package digitalbedrock.software.pbcore.listeners;

import java.io.File;

public interface FileChangedListener {

    void onFileChanged(String currentId, File file, boolean close);

    void discardChanges(String currentId, File file);
}
