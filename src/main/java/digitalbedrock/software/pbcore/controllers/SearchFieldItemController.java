package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.entity.IPBCore;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SearchFieldItemController {

    @FXML
    private Label lblElementType;
    @FXML
    private Label lblElementName;

    public void bind(IPBCore ipbCore) {
        if (ipbCore.isAttribute()) {
            lblElementType.getStyleClass().remove("elem");
            lblElementType.getStyleClass().add("attr");
            lblElementType.setText("A");
        } else {
            lblElementType.getStyleClass().remove("attr");
            lblElementType.getStyleClass().add("elem");
            lblElementType.setText("E");
        }
        lblElementName.setText(ipbCore.getScreenName());
    }
}
