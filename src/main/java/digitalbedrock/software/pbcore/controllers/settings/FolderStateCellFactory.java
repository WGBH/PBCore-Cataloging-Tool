package digitalbedrock.software.pbcore.controllers.settings;

import digitalbedrock.software.pbcore.core.models.FolderModel;
import digitalbedrock.software.pbcore.lucene.LuceneIndexer;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import static digitalbedrock.software.pbcore.controllers.SettingsCrawlingController.*;

public class FolderStateCellFactory implements Callback<TableColumn<FolderModel, String>, TableCell<FolderModel, String>> {

    @Override
    public TableCell<FolderModel, String> call(final TableColumn<FolderModel, String> param) {
        return new TableCell<FolderModel, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    FolderModel model = getTableView().getItems().get(getIndex());
                    model.scheduledProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                        boolean processingFolder = LuceneIndexer.getInstance().isProcessingFolder(model.getFolderPath());
                        setText(newValue ? SCHEDULED : processingFolder ? PROCESSING : FINISHED);
                    }));
                    model.indexingProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                        boolean scheduledFolder = LuceneIndexer.getInstance().isScheduledFolder(model.getFolderPath());
                        setText(newValue ? PROCESSING : scheduledFolder ? SCHEDULED : FINISHED);
                    }));
                    LuceneIndexer.getInstance().getFoldersToProcess().addListener((ListChangeListener<String>) c -> Platform.runLater(() -> Platform.runLater(() -> {
                        if (c.getList().contains(model.getFolderPath())) {
                            setText(SCHEDULED);
                        }
                    })));
                    if (model.isIndexing()) {
                        setText(PROCESSING);
                    } else if (model.isScheduled()) {
                        setText(SCHEDULED);
                    } else {
                        setText(FINISHED);
                    }
                }
            }
        };
    }
}
