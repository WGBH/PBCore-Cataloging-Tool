package digitalbedrock.software.pbcore.listeners;

import digitalbedrock.software.pbcore.core.models.NewDocumentType;

import java.io.File;

public interface MenuActionListener {
    void newDocument(NewDocumentType newDocumentType);
    void openDocument(File file);
}