package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.controllers.settings.*;
import digitalbedrock.software.pbcore.core.models.FolderModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsCrawlingController extends AbsController {

    public static final String PROCESSING = "PROCESSING";
    public static final String SCHEDULED = "SCHEDULED";
    public static final String FINISHED = "-";

    @FXML
    private TableView<FolderModel> foldersTableView;

    @FXML
    private TableColumn<FolderModel, String> pathColumn;

    @FXML
    private TableColumn<FolderModel, Boolean> lastIndexedColumn;

    @FXML
    private TableColumn<FolderModel, Boolean> stateColumn;

    @FXML
    private TableColumn<FolderModel, Boolean> filesProcessedColumn;

    @FXML
    private TableColumn<FolderModel, Boolean> removeColumn;

    @FXML
    private TableColumn<FolderModel, Boolean> reindexColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    @FXML
    void onAddButtonClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File folder = directoryChooser.showDialog(addButton.getScene().getWindow());
        if (folder == null) {
            return;
        }
        MainApp.getInstance().getRegistry().getSettings().addFolder(folder);
        foldersTableView.getItems().add(new FolderModel(folder.getPath()));

    }

    @FXML
    void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    void onOkButtonClick(ActionEvent event) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pathColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getFolderPath()));

        ObservableList<FolderModel> obsList = FXCollections.observableArrayList(MainApp.getInstance().getRegistry().getSettings().getFolders());
        foldersTableView.setItems(obsList);
        foldersTableView.setSelectionModel(null);

        lastIndexedColumn.setCellFactory(new FolderLastIndexedDateCellFactory());
        stateColumn.setCellFactory(new FolderStateCellFactory());
        filesProcessedColumn.setCellFactory(new FolderProcessedFilesCellFactory());
        reindexColumn.setCellFactory(new ReindexFolderCellFactory());
        removeColumn.setCellFactory(new RemoveFolderCellFactory(obsList));
    }

    @Override
    public MenuBar createMenu() {
        final MenuBar menuBar = new MenuBar();
        return menuBar;
    }
}
