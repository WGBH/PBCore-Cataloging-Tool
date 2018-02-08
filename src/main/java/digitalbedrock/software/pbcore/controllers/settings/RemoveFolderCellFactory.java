package digitalbedrock.software.pbcore.controllers.settings;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.models.FolderModel;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class RemoveFolderCellFactory implements Callback<TableColumn<FolderModel, Boolean>, TableCell<FolderModel, Boolean>> {

    private final ObservableList<FolderModel> obsList;

    public RemoveFolderCellFactory(ObservableList<FolderModel> obsList) {
        this.obsList = obsList;
    }

    @Override
    public TableCell<FolderModel, Boolean> call(final TableColumn<FolderModel, Boolean> param) {
        return new TableCell<FolderModel, Boolean>() {
            final Button btn = new Button("", new FontIcon(MaterialDesign.MDI_CLOSE));

            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    FolderModel model = getTableView().getItems().get(getIndex());
                    btn.setOnAction(event -> {
                        MainApp.getInstance().getRegistry().getSettings().removePath(model.getFolderPath());
                        obsList.remove(model);
                    });
                    setGraphic(btn);
                    setText(null);
                }

                setMaxWidth(25);
                setAlignment(Pos.CENTER);

                btn.getStyleClass().add("listActionButton");
                btn.getStyleClass().add("dimmedIcon");
            }
        };
    }
}
