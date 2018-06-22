package digitalbedrock.software.pbcore.components.editor;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreAttribute;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class AttributeVisualLayoutItemController {

    @FXML
    private Text previewAttributeLabel;
    @FXML
    private Text previewAttributeValue;

    public void bind(PBCoreAttribute attribute) {
        if (attribute == null) {
            return;
        }
        previewAttributeLabel.setText(attribute.getScreenName() + ": ");
        previewAttributeValue.setText(attribute.getValue() == null ? "" : attribute.getValue());
    }

}
