package digitalbedrock.software.pbcore.controllers.settings;

import digitalbedrock.software.pbcore.core.models.FolderModel;
import javafx.application.Platform;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;

public class FolderLastIndexedDateCellFactory implements Callback<TableColumn<FolderModel, String>, TableCell<FolderModel, String>> {

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
                    model.indexingProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> setText(model.getDateLastIndexing() == null ? "-" : model.getDateLastIndexing().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))));
                    setText(model.getDateLastIndexing() == null ? "-" : model.getDateLastIndexing().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                }
            }
        };
    }
}
