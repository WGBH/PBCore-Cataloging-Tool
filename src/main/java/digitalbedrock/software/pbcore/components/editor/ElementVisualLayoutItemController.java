package digitalbedrock.software.pbcore.components.editor;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ElementVisualLayoutItemController {

    @FXML
    private Text previewElementLabel;
    @FXML
    private Text previewElementValue;

    public void bind(PBCoreElement element) {
        if (element == null) {
            return;
        }
        previewElementLabel.setText(element.getScreenName().toUpperCase() + ": ");
        previewElementValue.setText(element.getValue());
    }
}
