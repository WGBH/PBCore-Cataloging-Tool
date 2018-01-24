package digitalbedrock.software.pbcore.core.models.entity;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import digitalbedrock.software.pbcore.core.models.NewDocumentType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PBCoreStructure {
    private static PBCoreStructure instance;
    private List<PBCoreElement> elements = new ArrayList<>();

    public PBCoreStructure() {
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
        PBCoreElement clone = pbcoreDescriptionDocument.copy(false);
        PBCoreAttribute pbCoreAttribute = new PBCoreAttribute("xmlns", "xmlns", true, "", "http://www.pbcore.org/PBCore/PBCoreNamespace.html");
        pbCoreAttribute.setReadOnly(true);
        PBCoreAttribute pbCoreAttribute1 = new PBCoreAttribute("xsi:schemaLocation", "xsi:schemaLocation", true, "", "http://www.pbcore.org/PBCore/PBCoreNamespace.html https://raw.githubusercontent.com/WGBH/PBCore_2.1/master/pbcore-2.1.xsd");
        pbCoreAttribute1.setReadOnly(true);
        PBCoreAttribute pbCoreAttribute2 = new PBCoreAttribute("xmlns:xsi", "xmlns:xsi", true, "", "http://www.w3.org/2001/XMLSchema-instance");
        pbCoreAttribute2.setReadOnly(true);
        clone.addAttribute(pbCoreAttribute);
        clone.addAttribute(pbCoreAttribute1);
        clone.addAttribute(pbCoreAttribute2);
        clone.setElementType(PBCoreElementType.ROOT_ELEMENT);
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
        return pbCoreElementToReturn;
    }

    public PBCoreElement parseFile(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        Element documentElement = doc.getDocumentElement();
        NodeList childNodes = documentElement.getChildNodes();
        return getElementFromDom(null, documentElement, childNodes);
    }

    private PBCoreElement getElementFromDom(String parentFullPath, Node documentElement, NodeList childNodes) {
        String fullPath = parentFullPath != null ? (parentFullPath + "/" + documentElement.getNodeName()) : documentElement.getNodeName();
        System.out.println(fullPath);
        PBCoreElement element = getElement(fullPath);
        if (element == null) {
            return null;
        }
        PBCoreElement pbCoreElement = new PBCoreElement(
                element.getFullPath(),
                element.getScreenName(),
                element.getName(),
                element.isRequired(),
                element.isRepeatable(),
                element.getDescription(),
                element.getElementType() == PBCoreElementType.ROOT_ELEMENT ? null : documentElement.getTextContent(),
                element.getElementType(),
                element.isSupportsAttributes(),
                element.isValid(),
                element.getSequence(),
                element.getElementValueRestrictionType(),
                element.getPatternToFollow(),
                element.getEnumerationValues(),
                element.isValidAttributes(),
                element.isHasChildElements()

        );
        pbCoreElement.setValue(documentElement.getTextContent());
        if (documentElement.getAttributes() != null) {
            for (int j = 0; j < documentElement.getAttributes().getLength(); j++) {
                Node attr = documentElement.getAttributes().item(j);
                PBCoreAttribute pbCoreAttribute = new PBCoreAttribute(
                        attr.getNodeName(),
                        attr.getNodeName(),
                        false,
                        "",
                        attr.getTextContent()
                );
                pbCoreElement.addAttribute(pbCoreAttribute);
            }
        }
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            PBCoreElement elementFromDom = getElementFromDom(fullPath, item, item.getChildNodes());
            if (elementFromDom != null) {
                pbCoreElement.addSubElement(elementFromDom);
            }
        }
        return pbCoreElement;
    }

}
