package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.lucene.HitDocument;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SearchResultItemController {

    @FXML
    private Label lblElementType;
    @FXML
    private Label lblFilename;
    @FXML
    private Label lblFilepath;

    public void bind(HitDocument hitDocument) {
        lblElementType.setText(hitDocument.getPbCoreElement().getScreenName());
        lblFilename.setText(hitDocument.getFilename());
        lblFilepath.setText(hitDocument.getFilepath());
    }
}
