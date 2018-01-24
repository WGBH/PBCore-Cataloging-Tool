package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.CVTerm;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CVTermItemController extends AbsController {

    @FXML
    private Label termLabel;
    @FXML
    private Label sourceLabel;

    public void bind(CVTerm cvTerm) {
        termLabel.setText(cvTerm.getTerm());
        sourceLabel.setText(cvTerm.getSource());
    }
}