package digitalbedrock.software.pbcore.core.models.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.models.CVTerm;
import digitalbedrock.software.pbcore.core.models.NewDocumentType;
import digitalbedrock.software.pbcore.core.models.document.PbcoreDescriptionDocumentType;
import digitalbedrock.software.pbcore.utils.Registry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class PBCoreStructure {

    private static PBCoreStructure instance;
    private List<PBCoreElement> elements = new ArrayList<>();

    public PBCoreStructure() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        ObjectMapper mapper = new ObjectMapper();
        try {
            elements = mapper.readValue(classloader.getResource("structure.json"), new TypeReference<List<PBCoreElement>>() {
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PBCoreStructure getInstance() {
        if (instance == null) {
            instance = new PBCoreStructure();
        }
        return instance;
    }

    public PBCoreElement getRootElement(NewDocumentType selectedDocumentType) {
        return getRootElement(selectedDocumentType, false);
    }

    public PBCoreElement getRootElement(NewDocumentType selectedDocumentType, boolean includeAttributes) {
        PBCoreElement pbcoreDescriptionDocument = null;
        switch (selectedDocumentType) {
            case DESCRIPTION_DOCUMENT:
                pbcoreDescriptionDocument = elements.stream().filter(pbCoreElement -> pbCoreElement.getName().equals("pbcoreDescriptionDocument")).findFirst().orElse(null);
                break;
            case INSTANTIATION_DOCUMENT:
                pbcoreDescriptionDocument = elements.stream().filter(pbCoreElement -> pbCoreElement.getName().equals("pbcoreInstantiationDocument")).findFirst().orElse(null);
                break;
            case COLLECTION:
                pbcoreDescriptionDocument = elements.stream().filter(pbCoreElement -> pbCoreElement.getName().equals("pbcoreCollection")).findFirst().orElse(null);
                break;
        }
        if (pbcoreDescriptionDocument == null) {
            return null;
        }
        PBCoreElement clone = pbcoreDescriptionDocument.copy(includeAttributes);
        verifyElementExtraAttributes(clone);
        return clone;
    }

    public PBCoreElement getElement(String fullpath) {
        List<String> split = new ArrayList<>(Arrays.asList(fullpath.split("/")));
        List<PBCoreElement> elementsToProcess = elements;
        PBCoreElement pbCoreElementToReturn = null;
        while (!split.isEmpty()) {
            String remove = split.remove(0);
            pbCoreElementToReturn = elementsToProcess.stream().filter(pbCoreElement -> pbCoreElement.getName().equals(remove)).findFirst().orElse(null);
            if (pbCoreElementToReturn == null) {
                return null;
            }
            elementsToProcess = pbCoreElementToReturn.getOrderedSubElements();
        }
        verifyElementExtraAttributes(pbCoreElementToReturn);
        return pbCoreElementToReturn;
    }

    private void verifyElementExtraAttributes(PBCoreElement pbCoreElementToReturn) {
        List<String> strings = Arrays.asList("pbcoreDescriptionDocument",
                "pbcoreInstantiationDocument",
                "pbcoreCollection");
        if (pbCoreElementToReturn != null && strings.contains(pbCoreElementToReturn.getName())) {

            List<String> attrsToAdd = Arrays.asList("xmlns",
                    "xsi:schemaLocation",
                    "xmlns:xsi");

            pbCoreElementToReturn.setElementType(PBCoreElementType.ROOT_ELEMENT);
            PBCoreAttribute pbCoreAttribute = new PBCoreAttribute(pbCoreElementToReturn.getFullPath() + "/xmlns", "xmlns", "xmlns", true, "", "http://www.pbcore.org/PBCore/PBCoreNamespace.html", true);
            PBCoreAttribute pbCoreAttribute1 = new PBCoreAttribute(pbCoreElementToReturn.getFullPath() + "/xsi:schemaLocation", "xsi:schemaLocation", "xsi:schemaLocation", true, "", "http://www.pbcore.org/PBCore/PBCoreNamespace.html https://raw.githubusercontent.com/WGBH/PBCore_2.1/master/pbcore-2.1.xsd", true);
            PBCoreAttribute pbCoreAttribute2 = new PBCoreAttribute(pbCoreElementToReturn.getFullPath() + "/xmlns:xsi", "xmlns:xsi", "xmlns:xsi", true, "", "http://www.w3.org/2001/XMLSchema-instance", true);

            if (pbCoreElementToReturn.getAttributes().stream().filter(pbCoreAttribute3 -> attrsToAdd.contains(pbCoreAttribute3.getName())).count() == 0) {
                pbCoreElementToReturn.addAttribute(pbCoreAttribute);
                pbCoreElementToReturn.addAttribute(pbCoreAttribute1);
                pbCoreElementToReturn.addAttribute(pbCoreAttribute2);
            }
        }
    }

    public PBCoreElement parseFile(File file) throws IllegalAccessException, JAXBException {
        return parseFile(file, MainApp.getInstance().getRegistry());
    }

    public PBCoreElement parseFile(File file, Registry registry) throws IllegalAccessException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PbcoreDescriptionDocumentType.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement customer = (JAXBElement) jaxbUnmarshaller.unmarshal(file);
        PBCoreElement pbCoreElement = PBCoreStructure.getInstance().getElement(customer.getName().getLocalPart());

        boolean valid = true;
        if (pbCoreElement.getElementValueRestrictionType() == ElementValueRestrictionType.SIMPLE && !pbCoreElement.isHasChildElements()) {
            valid = pbCoreElement.getValue() != null && !pbCoreElement.getValue().trim().isEmpty();
        }
        String pathRepresentation = pbCoreElement.getScreenName();

        PBCoreElement coreElement = new PBCoreElement(
                pbCoreElement.getFullPath(),
                pathRepresentation,
                pbCoreElement.getScreenName(),
                pbCoreElement.getName(),
                pbCoreElement.isRequired(),
                pbCoreElement.isRepeatable(),
                pbCoreElement.getDescription(),
                pbCoreElement.getValue(),
                pbCoreElement.getElementType(),
                pbCoreElement.isSupportsAttributes(),
                valid,
                pbCoreElement.isFatalError(),
                pbCoreElement.getSequence(),
                pbCoreElement.getElementValueRestrictionType(),
                pbCoreElement.getPatternToFollow(),
                pbCoreElement.getEnumerationValues(),
                pbCoreElement.isHasChildElements(),
                pbCoreElement.isChoice()
        );
        pbCoreElement.getAttributes().forEach((pbCoreAttribute) -> {
            coreElement.addAttribute(pbCoreAttribute.copy());
        });
        parseFileToPBCoreElements(customer.getName().getLocalPart(), pathRepresentation, coreElement, customer.getValue(), registry);
        return coreElement;
    }

    private boolean parseFileToPBCoreElements(String parentFullPath, String parentPathRepresentation, PBCoreElement pbCoreElement, Object object, Registry registry) throws IllegalAccessException {
        if (object == null) {
            return true;
        }
        parseElement(parentFullPath, parentPathRepresentation, pbCoreElement, object, registry);
        return pbCoreElement.getSubElements().isEmpty() && pbCoreElement.getValue() == null;
    }

    private void parseElement(String parentFullPath, String parentPathRepresentation, PBCoreElement pbCoreElement, Object o, Registry registry) throws IllegalAccessException {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(XmlElement.class)) {

                field.setAccessible(true);
                Object object = field.get(o);
                if (object instanceof Collection) {
                    Collection collection = (Collection) object;
                    for (Object collectionObject : collection) {
                        processElement(parentFullPath, parentPathRepresentation, pbCoreElement, field, collectionObject, registry);
                    }
                } else {
                    processElement(parentFullPath, parentPathRepresentation, pbCoreElement, field, object, registry);
                }

            } else if (field.isAnnotationPresent(XmlValue.class)) {
                field.setAccessible(true);
                pbCoreElement.setValue((String) field.get(o));
                pbCoreElement.setValid(pbCoreElement.getValue() != null && !pbCoreElement.getValue().trim().isEmpty());
            } else if (field.isAnnotationPresent(XmlAttribute.class)) {
                field.setAccessible(true);
                if (field.get(o) == null) {
                    continue;
                }
                if (pbCoreElement.getAttributes().isEmpty()) {
                    PBCoreElement element = PBCoreStructure.getInstance().getElement(pbCoreElement.getFullPath());
                    PBCoreAttribute pbCoreAttribute1 = element.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getName().equals(field.getName())).findFirst().orElse(null).copy();
                    try {
                        pbCoreAttribute1.setValue((String) field.get(o));
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    pbCoreElement.addAttribute(pbCoreAttribute1);
                } else {
                    pbCoreElement.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getName().equals(field.getName())).forEach(pbCoreAttribute -> {
                        try {
                            pbCoreAttribute.setValue((String) field.get(o));
                        }
                        catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
        Set<PBCoreAttribute> collect = pbCoreElement.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getValue() == null && !pbCoreAttribute.isRequired()).collect(Collectors.toSet());
        pbCoreElement.getAttributes().removeAll(collect);
        if (registry.getControlledVocabularies().containsKey(pbCoreElement.getName())) {
            List<CVTerm> suggestions = registry.getControlledVocabularies().get(pbCoreElement.getName()).getTerms();
            pbCoreElement.setValid(suggestions.stream().filter(cvTerm -> cvTerm.getTerm().equalsIgnoreCase(pbCoreElement.getValue())).count() > 0);
        }
    }

    private void processElement(String parentFullPath, String parentPathRepresentation, PBCoreElement pbCoreElement, Field field, Object object, Registry registry) throws IllegalAccessException {
        String fullPath = parentFullPath + "/" + field.getName();
        PBCoreElement element = PBCoreStructure.getInstance().getElement(fullPath);
        boolean valid = true;
        if (pbCoreElement.getElementValueRestrictionType() == ElementValueRestrictionType.SIMPLE && !pbCoreElement.isHasChildElements()) {
            valid = pbCoreElement.getValue() != null && !pbCoreElement.getValue().trim().isEmpty();
        }
        if (element == null) {
            return;
        }
        String pathRepresentation = parentPathRepresentation + " . " + element.getScreenName();
        PBCoreElement coreElement = new PBCoreElement(
                element.getFullPath(),
                pathRepresentation,
                element.getScreenName(),
                element.getName(),
                element.isRequired(),
                element.isRepeatable(),
                element.getDescription(),
                element.getValue(),
                element.getElementType(),
                element.isSupportsAttributes(),
                valid,
                pbCoreElement.isFatalError(),
                element.getSequence(),
                element.getElementValueRestrictionType(),
                element.getPatternToFollow(),
                element.getEnumerationValues(),
                element.isHasChildElements(),
                element.isChoice()
        );
        boolean ignore = parseFileToPBCoreElements(fullPath, pathRepresentation, coreElement, object, registry);
        if (!ignore) {
            pbCoreElement.addSubElement(coreElement);
        }
    }

    public void saveFile(PBCoreElement pbCoreElement, File file) throws ParserConfigurationException, TransformerException, IOException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element element = processElement(doc, pbCoreElement);

        doc.appendChild(element);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        FileWriter fileWriter = new FileWriter(file);
        StreamResult result = new StreamResult(fileWriter);
        transformer.transform(source, result);
    }

    private Element processElement(Document doc, PBCoreElement value) {
        Element element = doc.createElement(value.getName());
        element.appendChild(doc.createTextNode(value.getValue() == null ? "" : value.getValue()));
        value.getAttributes().forEach((pbCoreAttribute) -> {
            element.setAttribute(pbCoreAttribute.getName(), pbCoreAttribute.getValue());
        });
        value.getOrderedSubElements().forEach((pbCoreElement) -> {
            element.appendChild(processElement(doc, pbCoreElement));
        });
        return element;
    }

    public List<PBCoreElement> getAllElementsList() {
        List<PBCoreElement> pbCoreElements = new ArrayList<>();
        elements.forEach(pbCoreElement -> {
            if (pbCoreElement.getSubElements().isEmpty()) {
                pbCoreElements.add(pbCoreElement);
            } else {
                fillList(pbCoreElements, pbCoreElement);
            }
        });
        return pbCoreElements;
    }

    private void fillList(List<PBCoreElement> pbCoreElements, PBCoreElement pbCoreElement) {
        pbCoreElement.getSubElements().forEach(pbCoreElement1 -> {
            if (pbCoreElement1.getSubElements().isEmpty()) {
                pbCoreElements.add(pbCoreElement1);
            } else {
                fillList(pbCoreElements, pbCoreElement1);
            }
        });
    }
}
