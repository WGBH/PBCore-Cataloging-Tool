package digitalbedrock.software.pbcore.core.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FolderModel implements Serializable {

    private String folderPath;
    private final LongProperty totalValidFiles = new SimpleLongProperty();
    private LocalDateTime dateLastIndexing;
    private final BooleanProperty indexing = new SimpleBooleanProperty();
    private final BooleanProperty scheduled = new SimpleBooleanProperty();

    public FolderModel() {
    }

    public FolderModel(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public LocalDateTime getDateLastIndexing() {
        return dateLastIndexing;
    }

    public void setDateLastIndexing(LocalDateTime dateLastIndexing) {
        this.dateLastIndexing = dateLastIndexing;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public long getTotalValidFiles() {
        return totalValidFiles.get();
    }

    public LongProperty totalValidFilesProperty() {
        return totalValidFiles;
    }

    public void setTotalValidFiles(long totalValidFiles) {
        this.totalValidFiles.set(totalValidFiles);
    }

    public boolean isIndexing() {
        return indexing.get();
    }

    public BooleanProperty indexingProperty() {
        return indexing;
    }

    public void setIndexing(boolean indexing) {
        this.indexing.set(indexing);
    }

    public boolean isScheduled() {
        return scheduled.get();
    }

    public BooleanProperty scheduledProperty() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled.set(scheduled);
    }
}
