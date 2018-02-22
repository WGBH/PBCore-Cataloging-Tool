package digitalbedrock.software.pbcore.controllers.settings;

import digitalbedrock.software.pbcore.core.models.FolderModel;
import javafx.application.Platform;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class FolderProcessedFilesCellFactory implements Callback<TableColumn<FolderModel, Long>, TableCell<FolderModel, Long>> {

    @Override
    public TableCell<FolderModel, Long> call(final TableColumn<FolderModel, Long> param) {
        return new TableCell<FolderModel, Long>() {
            @Override
            public void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    FolderModel model = getTableView().getItems().get(getIndex());
                    model.totalValidFilesProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> setText(Long.toString(model.getTotalValidFiles()))));
                    setText(Long.toString(model.getTotalValidFiles()));
                }
            }
        };
    }
}
