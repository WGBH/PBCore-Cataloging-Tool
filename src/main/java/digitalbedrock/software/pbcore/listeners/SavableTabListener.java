package digitalbedrock.software.pbcore.listeners;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;

public interface SavableTabListener {

    void saveDocument();

    void saveDocumentAs();

    void saveDocumentAsTemplate();

    void addBatchUpdate(PBCoreElement pbCoreElement);

    boolean isExportable();
}
