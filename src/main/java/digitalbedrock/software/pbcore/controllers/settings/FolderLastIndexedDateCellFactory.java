package digitalbedrock.software.pbcore.controllers.settings;

import digitalbedrock.software.pbcore.core.models.FolderModel;
import javafx.application.Platform;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;

public class FolderLastIndexedDateCellFactory implements Callback<TableColumn<FolderModel, Boolean>, TableCell<FolderModel, Boolean>> {

    @Override
    public TableCell<FolderModel, Boolean> call(final TableColumn<FolderModel, Boolean> param) {
        return new TableCell<FolderModel, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    FolderModel model = getTableView().getItems().get(getIndex());
                    model.indexingProperty().addListener((observable, oldValue, newValue) -> {
                        Platform.runLater(() -> setText(model.getDateLastIndexing() == null ? "-" : model.getDateLastIndexing().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
                    });
                    setText(model.getDateLastIndexing() == null ? "-" : model.getDateLastIndexing().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                }
            }
        };
    }
}
