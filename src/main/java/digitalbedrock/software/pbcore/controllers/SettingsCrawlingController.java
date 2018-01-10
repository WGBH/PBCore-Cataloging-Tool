package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.FolderModel;
import java.io.File;
import java.time.LocalDate;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SettingsCrawlingController extends AbsController {

    private File folder;

    @FXML
    private TableView<FolderModel> foldersTableView;

    @FXML
    private TableColumn<FolderModel, String> pathColumn;

    @FXML
    private TableColumn<FolderModel, LocalDate> lastIndexedColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    // actions
    @FXML
    void onAddButtonClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        folder = directoryChooser.showDialog(addButton.getScene().getWindow());
        if (folder == null) {
            return;
        }
        foldersTableView.getItems().add(new FolderModel(folder));
    }

    @FXML
    void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    void onOkButtonClick(ActionEvent event) {
        if (folder != null) {
            mainController.getRegistry().getSettings().addPath(folder);
        }
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @Override
    public void setMainController(MainController mainController) {
        super.setMainController(mainController);
        pathColumn.setCellValueFactory(celldata -> {
            return new SimpleStringProperty(((FolderModel) celldata.getValue()).getPath());
        });
        lastIndexedColumn.setCellValueFactory(celldata -> {
            return new SimpleObjectProperty(((FolderModel) celldata.getValue()).getDateLastIndexing());
        });

        ObservableList<FolderModel> obsList = FXCollections.observableArrayList();
        mainController.getRegistry().getSettings().getDirectories().forEach(file -> {
            obsList .add(new FolderModel(file));
        });
        foldersTableView.setItems(obsList);
    }

}
