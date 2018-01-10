package digitalbedrock.software.pbcore.core.models;

import java.io.File;
import java.time.LocalDate;

public class FolderModel {

    private boolean toAdd;
    private boolean toRemove;

    private File folder;
    private long totalValidFiles;
    private LocalDate dateLastIndexing;
    private boolean isIndexing;

    public FolderModel(File file) {
        folder = file;
        dateLastIndexing = LocalDate.now();
    }

    public boolean isToAdd() {
        return toAdd;
    }

    public void setToAdd(boolean toAdd) {
        this.toAdd = toAdd;
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }

    public File getFolder() {
        return folder;
    }

    public String getPath() {
        return folder.getAbsolutePath();
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }

    public long getTotalValidFiles() {
        return totalValidFiles;
    }

    public void setTotalValidFiles(long totalValidFiles) {
        this.totalValidFiles = totalValidFiles;
    }

    public LocalDate getDateLastIndexing() {
        return dateLastIndexing;
    }

    public void setDateLastIndexing(LocalDate dateLastIndexing) {
        this.dateLastIndexing = dateLastIndexing;
    }

    public boolean isIsIndexing() {
        return isIndexing;
    }

    public void setIsIndexing(boolean isIndexing) {
        this.isIndexing = isIndexing;
    }

}
