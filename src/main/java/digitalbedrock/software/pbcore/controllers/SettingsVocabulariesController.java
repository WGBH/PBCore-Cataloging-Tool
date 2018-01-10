package digitalbedrock.software.pbcore.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SettingsVocabulariesController extends AbsController {

    // elements
    @FXML
    private ToggleGroup typeRadio;

    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    // actions
    @FXML
    void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onOkButtonClick(ActionEvent event) {
        // TODO: save
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();

    }

}
