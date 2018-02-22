package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.models.CV;
import digitalbedrock.software.pbcore.core.models.CVTerm;
import digitalbedrock.software.pbcore.utils.Registry;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsVocabulariesController extends AbsController {

    private static final String EDIT = "SAVE";
    private static final String ADD = "ADD";
    private static final String VOCABULARY_TERM_IS_MANDATORY = "Vocabulary term is mandatory";
    private static final String TERM_ALREADY_ADDED_FOR_SELECTED_VOCABULARY = "Term already added";
    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    @FXML
    private TreeView<String> treelist;
    @FXML
    private TableView<CVTerm> tvVocabularies;

    private final TreeItem<String> rootElements = new TreeItem<>();
    private final TreeItem<String> rootAttributes = new TreeItem<>();

    @FXML
    private Label lblInvalidTerm;
    @FXML
    private TextField tfTerm;
    @FXML
    private TextField tfSource;
    @FXML
    private TextField tfVersion;
    @FXML
    private TextField tfRef;

    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonCancelEdit;

    private String selectedCV;
    private CVTerm selectedCVTerm;

    @FXML
    private TableColumn<CVTerm, String> termColumn;
    @FXML
    private TableColumn<CVTerm, String> sourceColumn;
    @FXML
    private TableColumn<CVTerm, String> versionColumn;
    @FXML
    private TableColumn<CVTerm, String> refColumn;
    @FXML
    private TableColumn<CVTerm, String> editColumn;
    @FXML
    private TableColumn<CVTerm, String> deleteColumn;

    @FXML
    private ToggleGroup typeRadio;
    @FXML
    private RadioButton rbElements;
    @FXML
    private RadioButton rbAttributes;

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
        MainApp.getInstance().getRegistry().getControlledVocabularies().forEach((key, value) -> {
            TreeItem<String> t = new TreeItem<>(key);
            if (value.isAttribute()) {
                rootAttributes.getChildren().add(t);
            } else {
                rootElements.getChildren().add(t);
            }
        });
        treelist.setShowRoot(false);
        treelist.setRoot(rootElements);

        treelist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                tvVocabularies.getItems().clear();
                return;
            }
            selectedCV = newValue.getValue();
            selectedCVTerm = null;
            CV cv = MainApp.getInstance().getRegistry().getControlledVocabularies().get(newValue.getValue());
            tvVocabularies.setItems(FXCollections.observableArrayList(cv.getTerms()));
            buttonAdd.setDisable(false);
            tfTerm.setDisable(false);
            tfSource.setDisable(false);
            tfVersion.setDisable(false);
            tfRef.setDisable(false);
            tfTerm.setText(null);
            tfSource.setText(null);
            tfVersion.setText(null);
            tfRef.setText(null);
        });
        termColumn.setCellValueFactory(param -> param.getValue().termProperty());
        sourceColumn.setCellValueFactory(param -> param.getValue().sourceProperty());
        versionColumn.setCellValueFactory(param -> param.getValue().versionProperty());
        refColumn.setCellValueFactory(param -> param.getValue().refProperty());

        editColumn.setCellFactory((final TableColumn<CVTerm, String> param) -> new TableCell<CVTerm, String>() {
            final Button btn = new Button("", new FontIcon(MaterialDesign.MDI_LEAD_PENCIL));

            {
                btn.getStyleClass().add("listActionButton");
            }

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    CVTerm term = getTableView().getItems().get(getIndex());
                    btn.setOnAction(event -> {
                        buttonCancelEdit.setVisible(true);
                        tfTerm.setText(term.getTerm());
                        tfSource.setText(term.getSource());
                        tfVersion.setText(term.getVersion());
                        tfRef.setText(term.getRef());
                        buttonAdd.setText(EDIT);
                        buttonAdd.setDisable(!term.isCustom());
                        tfTerm.setDisable(!term.isCustom());
                        tfSource.setDisable(!term.isCustom());
                        tfVersion.setDisable(!term.isCustom());
                        tfRef.setDisable(!term.isCustom());
                        selectedCVTerm = term;
                        updateTFs();
                    });
                    setGraphic(!term.isCustom() ? null : btn);
                    setText(null);
                }

                setMaxWidth(25);
                setAlignment(Pos.CENTER);

                btn.getStyleClass().add("listActionButton");
                btn.getStyleClass().add("primaryIcon");
            }
        });
        deleteColumn.setCellFactory((final TableColumn<CVTerm, String> param) -> new TableCell<CVTerm, String>() {
            final Button btn = new Button("", new FontIcon(MaterialDesign.MDI_CLOSE));

            {
                btn.getStyleClass().add("listActionButton");
            }

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    CVTerm term = getTableView().getItems().get(getIndex());
                    btn.setOnAction(event -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Delete Controlled vocabulary");
                        alert.setContentText("By removing this controlled vocabulary it won't be available any more.\nThis operation is not reversible.\nDo you want to proceed?");
                        alert.setHeaderText(null);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            deleteTerm(term);
                        }
                    });
                    setGraphic(!term.isCustom() ? null : btn);
                    setText(null);
                }
                setMaxWidth(25);
                setAlignment(Pos.CENTER);

                btn.getStyleClass().add("listActionButton");
                btn.getStyleClass().add("dimmedIcon");
            }

            private void deleteTerm(CVTerm term) {
                MainApp.getInstance().getRegistry().deleteVocabulary(selectedCV, term);
                tvVocabularies.getItems().remove(term);
                if (Objects.equals(selectedCVTerm, term)) {
                    selectedCVTerm = null;
                    clearTFs();
                    updateTFs();
                    buttonCancelEdit.setVisible(false);
                }
            }
        });
        buttonAdd.setDisable(true);
        tfTerm.setDisable(true);
        tfSource.setDisable(true);
        tfVersion.setDisable(true);
        tfRef.setDisable(true);
        typeRadio.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (typeRadio.getSelectedToggle().equals(rbAttributes)) {
                treelist.setRoot(rootAttributes);
            } else if (typeRadio.getSelectedToggle().equals(rbElements)) {
                treelist.setRoot(rootElements);
            }
            selectedCVTerm = null;
            cancelEditTerm(null);
            buttonAdd.setDisable(true);
        });
    }

    private void updateTFs() {
        if (selectedCVTerm == null) {
            buttonAdd.setDisable(false);
            tfTerm.setDisable(false);
            tfSource.setDisable(false);
            tfVersion.setDisable(false);
            tfRef.setDisable(false);
            buttonAdd.setText(ADD);
        } else {
            buttonAdd.setDisable(!selectedCVTerm.isCustom());
            tfTerm.setDisable(!selectedCVTerm.isCustom());
            tfSource.setDisable(!selectedCVTerm.isCustom());
            tfVersion.setDisable(!selectedCVTerm.isCustom());
            tfRef.setDisable(!selectedCVTerm.isCustom());
            buttonAdd.setText(EDIT);
        }
    }

    @Override
    public MenuBar createMenu() {
        return new MenuBar();
    }

    public void saveTerm(ActionEvent actionEvent) {
        Registry registry = MainApp.getInstance().getRegistry();
        String term = tfTerm.getText();
        if (term == null || term.trim().isEmpty()) {
            lblInvalidTerm.setText(VOCABULARY_TERM_IS_MANDATORY);
            lblInvalidTerm.setVisible(true);
            return;
        } else {
            CVTerm cvTerm1 = registry.getControlledVocabularies().get(selectedCV).getTerms().stream().filter(cvTerm -> Objects.equals(cvTerm.getTerm(), term)).findFirst().orElse(null);
            if (cvTerm1 != null && (selectedCVTerm == null || !Objects.equals(selectedCVTerm, cvTerm1))) {
                lblInvalidTerm.setText(TERM_ALREADY_ADDED_FOR_SELECTED_VOCABULARY);
                lblInvalidTerm.setVisible(true);
                return;
            }
        }
        lblInvalidTerm.setVisible(false);
        String source = tfSource.getText();
        String version = tfVersion.getText();
        String ref = tfRef.getText();
        if (selectedCVTerm == null) {
            CVTerm cvTerm = registry.saveVocabulary(selectedCV, term, source, version, ref);
            tvVocabularies.getItems().add(cvTerm);
            tvVocabularies.scrollTo(tvVocabularies.getItems().size() - 1);
        } else {
            selectedCVTerm.update(term, source, version, ref);
            registry.updateVocabulary(selectedCV, selectedCVTerm);
            tvVocabularies.getItems().set(tvVocabularies.getItems().indexOf(selectedCVTerm), selectedCVTerm);
        }
        selectedCVTerm = null;
        cancelEditTerm(null);
    }

    public void cancelEditTerm(ActionEvent actionEvent) {
        buttonCancelEdit.setVisible(false);
        clearTFs();
        updateTFs();
    }

    private void clearTFs() {
        tfTerm.setText(null);
        tfSource.setText(null);
        tfVersion.setText(null);
        tfRef.setText(null);
    }
}
