package digitalbedrock.software.pbcore.core.models.entity;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import digitalbedrock.software.pbcore.core.models.NewDocumentType;

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
        PBCoreElement clone = pbcoreDescriptionDocument.clone();
        clone.setElementType(PBCoreElementType.ROOT_ELEMENT);
        return clone;
    }

    public PBCoreElement getElement(String fullpath) {
        List<String> split = new ArrayList<>(Arrays.asList(fullpath.split("/")));
        List<PBCoreElement> elementsToProcess = elements;
        PBCoreElement pbCoreElementToReturn = null;
        while (!split.isEmpty()) {
            String remove = split.remove(0);
            pbCoreElementToReturn = elementsToProcess.stream().filter(pbCoreElement -> pbCoreElement.getFullPath().equals(remove)).findFirst().orElse(null);
            if (pbCoreElementToReturn == null) {
                return null;
            }
            elementsToProcess = pbCoreElementToReturn.getSubElements();
        }
        return pbCoreElementToReturn;
    }

    public PBCoreElement getParentElement(String fullpath) {
        List<String> split = new ArrayList<>(Arrays.asList(fullpath.split("/")));
        List<PBCoreElement> elementsToProcess = elements;
        PBCoreElement pbCoreElementToReturn = null;
        while (!split.isEmpty()) {
            String remove = split.remove(0);
            PBCoreElement pbCoreElement1 = elementsToProcess.stream().filter(pbCoreElement -> pbCoreElement.getFullPath().equals(remove)).findFirst().orElse(null);
            if (!split.isEmpty()) {
                pbCoreElementToReturn = pbCoreElement1;
                if (pbCoreElementToReturn == null) {
                    return null;
                }
            } else {
                return pbCoreElementToReturn;
            }
            elementsToProcess = pbCoreElementToReturn.getSubElements();
        }
        return null;
    }

    public PBCoreElement getParentElement(PBCoreElement rootElement, String fullpath) {
        List<String> split = new ArrayList<>(Arrays.asList(fullpath.split("/")));
        List<PBCoreElement> elementsToProcess = rootElement.getSubElements();
        PBCoreElement pbCoreElementToReturn = rootElement;
        split.remove(0);
        while (!split.isEmpty()) {
            String remove = split.remove(0);
            PBCoreElement pbCoreElement1 = elementsToProcess.stream().filter(pbCoreElement -> pbCoreElement.getFullPath().equals(remove)).findFirst().orElse(null);
            if (!split.isEmpty()) {
                pbCoreElementToReturn = pbCoreElement1;
                if (pbCoreElementToReturn == null) {
                    return null;
                }
            } else {
                return pbCoreElementToReturn;
            }
            elementsToProcess = pbCoreElementToReturn.getSubElements();
        }
        return null;
    }
}
