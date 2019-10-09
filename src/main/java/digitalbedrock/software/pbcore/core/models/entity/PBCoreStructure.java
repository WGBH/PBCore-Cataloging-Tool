package digitalbedrock.software.pbcore.core.models.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.models.CVTerm;
import digitalbedrock.software.pbcore.core.models.NewDocumentType;
import digitalbedrock.software.pbcore.core.models.document.PbcoreDescriptionDocumentType;
import digitalbedrock.software.pbcore.lucene.LuceneFileIndexer;
import digitalbedrock.software.pbcore.utils.Registry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PBCoreStructure {

    private static PBCoreStructure instance;
    private List<PBCoreElement> elements = new ArrayList<>();

    private PBCoreStructure() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        ObjectMapper mapper = new ObjectMapper();
        try {
            elements = mapper.readValue(classloader.getResource("structure.json"), new TypeReference<List<PBCoreElement>>() {
            });
        } catch (IOException e) {
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
        if (selectedDocumentType == NewDocumentType.COLLECTION) {
            clone.getSubElements().forEach(cl -> {
                cl.getAttributes().clear();
            });
        }
        return clone;
    }

    public void removeElement(PBCoreElement pbc, String fullpathToRemove) {
        List<String> split = new ArrayList<>(Arrays.asList(fullpathToRemove.split("/")));
        List<PBCoreElement> elementsToProcess = pbc.getSubElements();
        PBCoreElement pbCoreElementToReturn = null;
        while (!split.isEmpty()) {
            String remove = split.remove(0);
            pbCoreElementToReturn = elementsToProcess.stream().filter(pbCoreElement -> pbCoreElement.getName().equals(remove)).findFirst().orElse(null);
            if (pbCoreElementToReturn == null) {
                return;
            }
            elementsToProcess = pbCoreElementToReturn.getOrderedSubElements();
        }
        if (pbCoreElementToReturn != null) {
            pbc.removeSubElement(pbCoreElementToReturn);
        }
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

            pbCoreElementToReturn.markAsRootElement();
            PBCoreAttribute pbCoreAttribute = new PBCoreAttribute(pbCoreElementToReturn.getFullPath() + "/xmlns", "xmlns", "xmlns", "xmlns", false, "", "http://www.pbcore.org/PBCore/PBCoreNamespace.html", true);
            PBCoreAttribute pbCoreAttribute1 = new PBCoreAttribute(pbCoreElementToReturn.getFullPath() + "/xsi:schemaLocation", "xsi:schemaLocation", "xsi:schemaLocation", "xsi:schemaLocation", false, "", "http://www.pbcore.org/PBCore/PBCoreNamespace.html https://raw.githubusercontent.com/WGBH/PBCore_2.1/master/pbcore-2.1.xsd", true);
            PBCoreAttribute pbCoreAttribute2 = new PBCoreAttribute(pbCoreElementToReturn.getFullPath() + "/xmlns:xsi", "xmlns:xsi", "xmlns:xsi", "xmlns:xsi", false, "", "http://www.w3.org/2001/XMLSchema-instance", true);

            if (pbCoreElementToReturn.getAttributes().stream().noneMatch(pbCoreAttribute3 -> attrsToAdd.contains(pbCoreAttribute3.getName()))) {
                pbCoreElementToReturn.addAttribute(pbCoreAttribute);
                pbCoreElementToReturn.addAttribute(pbCoreAttribute1);
                pbCoreElementToReturn.addAttribute(pbCoreAttribute2);
            }
            if (pbCoreElementToReturn.getName().equalsIgnoreCase("pbcoreCollection")) {
                pbCoreElementToReturn.getSubElements().forEach(element -> element.setElementType(null));
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
                pbCoreElement.getTooltip(),
                pbCoreElement.isRequired(),
                pbCoreElement.isRepeatable(),
                pbCoreElement.isSupportsChildElements(),
                pbCoreElement.isAnyElement(),
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
        pbCoreElement.getAttributes().forEach((pbCoreAttribute) -> coreElement.addAttribute(pbCoreAttribute.copy()));
        parseFileToPBCoreElements(customer.getName().getLocalPart(), pathRepresentation, coreElement, customer.getValue(), registry);
        coreElement.setElementType(PBCoreElementType.ROOT_ELEMENT);
        return coreElement;
    }

    private boolean parseFileToPBCoreElements(String parentFullPath, String parentPathRepresentation, PBCoreElement pbCoreElement, Object object, Registry registry) throws IllegalAccessException {
        if (object == null) {
            return true;
        }
        parseElement(parentFullPath, parentPathRepresentation, pbCoreElement, object, registry);
        return false;
    }

    private void parseElement(String parentFullPath, String parentPathRepresentation, PBCoreElement pbCoreElement, Object o, Registry registry) throws IllegalAccessException {
        pbCoreElement.setElementType(null);
        if (o instanceof String) {
            pbCoreElement.setValue((String) o);
            pbCoreElement.setValid(pbCoreElement.getValue() != null && !pbCoreElement.getValue().trim().isEmpty());
        } else {
            List<Field> fields = new ArrayList<>(Arrays.asList(o.getClass().getDeclaredFields()));
            Class parent = o.getClass().getSuperclass();
            while (parent != null) {
                fields.addAll(Arrays.asList(parent.getDeclaredFields()));
                parent = parent.getSuperclass();
            }
            for (Field field : fields) {
                if (field.isAnnotationPresent(XmlAnyElement.class)) {
                    field.setAccessible(true);
                    Object object = field.get(o);
                    if (object instanceof Collection) {
                        for (Object o1 : (Collection) object) {
                            StringWriter stringWriter = new StringWriter();
                            if (o1 instanceof ElementNSImpl) {
                                try {
                                    ElementNSImpl elementNSImpl = (ElementNSImpl) o1;
                                    Document document = elementNSImpl.getOwnerDocument();
                                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                    Transformer transformer = transformerFactory.newTransformer();
                                    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                                    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                                    transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
                                    pbCoreElement.addAnyElement(new PBCoreElementAnyValue(stringWriter.toString()));
                                } catch (TransformerException e) {
                                    e.printStackTrace();
                                }
                            } else if (o1 instanceof JAXBElement) {
                                try {
                                    processMarshalledObject(o1, stringWriter, pbCoreElement);
                                } catch (TransformerException | ParserConfigurationException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        pbCoreElement.setValue((String) field.get(o));
                    }
                } else if (field.isAnnotationPresent(XmlElement.class)) {
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
                    if (pbCoreElement.getAttributes().isEmpty() || pbCoreElement.getAttributes().stream().noneMatch(pbCoreAttribute -> pbCoreAttribute.getName().equals(field.getName()))) {
                        PBCoreElement element = PBCoreStructure.getInstance().getElement(pbCoreElement.getFullPath());
                        PBCoreAttribute pbCoreAttribute1 = element.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getName().equals(field.getName())).findFirst().orElse(null);
                        if (pbCoreAttribute1 != null) {
                            pbCoreAttribute1 = pbCoreAttribute1.copy();
                            try {
                                pbCoreAttribute1.setValue((String) field.get(o));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            pbCoreElement.addAttribute(pbCoreAttribute1);
                        }
                    } else {
                        pbCoreElement.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getName().equals(field.getName())).forEach(pbCoreAttribute -> {
                            try {
                                pbCoreAttribute.setValue((String) field.get(o));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                    }
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

    private void processMarshalledObject(Object o1, StringWriter stringWriter, PBCoreElement pbc) throws ParserConfigurationException, TransformerException {
        JAXBElement jaxbElement = (JAXBElement) o1;
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        JAXB.marshal(jaxbElement, new DOMResult(document));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));

        pbc.addAnyElement(new PBCoreElementAnyValue(stringWriter.toString()));

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
                element.getTooltip(),
                element.isRequired(),
                element.isRepeatable(),
                element.isSupportsChildElements(),
                element.isAnyElement(),
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
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        FileWriter fileWriter = new FileWriter(file);
        StreamResult result = new StreamResult(fileWriter);
        transformer.transform(source, result);

        //only process final files, ignore our temp files
        if (file.getAbsolutePath().endsWith(".xml")) {
            LuceneFileIndexer.getInstance().startFileIndexing(file.getAbsolutePath());
        }
    }

    public void saveFileAsTemplate(PBCoreElement pbCoreElement, File file) throws ParserConfigurationException, TransformerException, IOException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element element = processElement(doc, pbCoreElement, false);

        doc.appendChild(element);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        FileWriter fileWriter = new FileWriter(file);
        StreamResult result = new StreamResult(fileWriter);
        transformer.transform(source, result);

        //only process final files, ignore our temp files
        if (file.getAbsolutePath().endsWith(".xml")) {
            LuceneFileIndexer.getInstance().startFileIndexing(file.getAbsolutePath());
        }
    }

    public void saveFilesToZip(Map<String, PBCoreElement> pbCoreElement, File file) throws ParserConfigurationException, TransformerException, IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file));
        for (Map.Entry<String, PBCoreElement> stringPBCoreElementEntry : pbCoreElement.entrySet()) {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element element = processElement(doc, stringPBCoreElementEntry.getValue());

            doc.appendChild(element);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(outputStream);
            transformer.transform(source, result);

            byte[] bytes = outputStream.toByteArray();
            ZipEntry entry = new ZipEntry(stringPBCoreElementEntry.getKey());
            zos.putNextEntry(entry);
            zos.write(bytes);
        }
        zos.closeEntry();
        zos.close();
    }

    private Element processElement(Document doc, PBCoreElement value) {
        return processElement(doc, value, true);
    }

    private Element processElement(Document doc, PBCoreElement value, boolean includeValues) {
        Element element = doc.createElement(value.getName());
        if (!value.isAnyElement() && includeValues) {
            element.appendChild(doc.createTextNode(value.getValue() == null ? "" : value.getValue()));
        } else {
            value.getAnyValues().stream().map((s) -> s == null || s.getValue() == null || s.getValue().trim().isEmpty() ? "" : s.getValue()).forEachOrdered((valueStr) -> {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;
                try {
                    builder = factory.newDocumentBuilder();
                    InputSource inputSource = new InputSource(new StringReader(valueStr));
                    inputSource.setEncoding("UTF-8");
                    Document document = builder.parse(inputSource);
                    element.appendChild(doc.importNode(document.getDocumentElement(), true));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        List<String> attrsToAdd = Arrays.asList("xmlns",
                "xsi:schemaLocation",
                "xmlns:xsi");
        value.getAttributes().forEach(pbCoreAttribute -> element.setAttribute(pbCoreAttribute.getName(), !includeValues && !attrsToAdd.contains(pbCoreAttribute.getName()) ? null : pbCoreAttribute.getValue()));
        value.getOrderedSubElements().forEach(pbCoreElement -> element.appendChild(processElement(doc, pbCoreElement, includeValues)));
        return element;
    }

    public void updateSourceAttributeOnElement(PBCoreElement value, CVTerm item) {
        PBCoreAttribute pbCoreAttribute1 = value.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getName().equals("source")).findFirst().orElse(null);
        if (item != null) {
            if (pbCoreAttribute1 != null) {
                pbCoreAttribute1.setValue(item.getSource());
            } else {
                PBCoreElement element = PBCoreStructure.getInstance().getElement(value.getFullPath());
                PBCoreAttribute sourceAttr = element.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getName().equals("source")).findFirst().orElse(null);
                if (sourceAttr != null) {
                    sourceAttr = sourceAttr.copy();
                    sourceAttr.setValue(item.getSource());
                }
                value.addAttribute(sourceAttr);
            }
        }/* else {
            if (pbCoreAttribute1 != null) {
                pbCoreAttribute1.setValue(null);
            }
        }*/
    }
}
