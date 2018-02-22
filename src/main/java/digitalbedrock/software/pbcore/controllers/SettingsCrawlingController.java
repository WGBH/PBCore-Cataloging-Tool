package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.controllers.settings.*;
import digitalbedrock.software.pbcore.core.models.FolderModel;
import digitalbedrock.software.pbcore.lucene.LuceneIndexer;
import javafx.beans.property.SimpleLongProperty;
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
import java.time.format.DateTimeFormatter;
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
    private TableColumn<FolderModel, String> lastIndexedColumn;

    @FXML
    private TableColumn<FolderModel, String> stateColumn;

    @FXML
    private TableColumn<FolderModel, Long> filesProcessedColumn;

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
        FolderModel folderModel = new FolderModel(folder.getPath());
        LuceneIndexer instance = LuceneIndexer.getInstance();
        if (instance.startFolderIndexing(folderModel.getFolderPath())) {
            folderModel.setScheduled(true);
            MainApp.getInstance().getRegistry().getSettings().updateFolder(folderModel);
        }
        foldersTableView.getItems().add(folderModel);

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
        stateColumn.setCellValueFactory(celldata -> {
            if (celldata.getValue().isIndexing()) {
                return new SimpleStringProperty(PROCESSING);
            } else if (celldata.getValue().isScheduled()) {
                return new SimpleStringProperty(SCHEDULED);
            } else {
                return new SimpleStringProperty(FINISHED);
            }
        });
        lastIndexedColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getDateLastIndexing() == null ? "" : celldata.getValue().getDateLastIndexing().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        filesProcessedColumn.setCellValueFactory(celldata -> new SimpleLongProperty(celldata.getValue().getTotalValidFiles()).asObject());

        ObservableList<FolderModel> obsList = FXCollections.observableArrayList(MainApp.getInstance().getRegistry().getSettings().getFolders());
        foldersTableView.setItems(obsList);

        lastIndexedColumn.setCellFactory(new FolderLastIndexedDateCellFactory());
        stateColumn.setCellFactory(new FolderStateCellFactory());
        filesProcessedColumn.setCellFactory(new FolderProcessedFilesCellFactory());
        reindexColumn.setCellFactory(new ReindexFolderCellFactory());
        removeColumn.setCellFactory(new RemoveFolderCellFactory(obsList));
    }

    @Override
    public MenuBar createMenu() {
        return new MenuBar();
    }
}
