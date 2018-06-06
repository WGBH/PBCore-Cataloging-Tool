package digitalbedrock.software.pbcore.utils;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.lucene.HitDocument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PBCoreUtils {

    public static Map<String, PBCoreElement> convertToDescriptionDocsMap(List<HitDocument> items) {
        Map<String, PBCoreElement> mapPBCoreElements = new HashMap<>();
        items.forEach((hitDocument) -> {
            if (hitDocument.getPbCoreElement().getName().equalsIgnoreCase("pbcoreDescriptionDocument")) {
                mapPBCoreElements.put(hitDocument.getFilename(), hitDocument.getPbCoreElement());
            } else if (hitDocument.getPbCoreElement().getName().equalsIgnoreCase("pbcoreCollection")) {
                hitDocument.getPbCoreElement().getSubElements().stream().filter((pbCoreElement) -> (pbCoreElement.getName().equalsIgnoreCase("pbcoreDescriptionDocument"))).forEachOrdered((pbCoreElement) -> {
                    mapPBCoreElements.put(hitDocument.getFilename(), pbCoreElement);
                });
            }
        });
        return mapPBCoreElements;
    }


    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.contains("win"));
    }

    public static boolean isMac() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.contains("mac"));
    }
}
