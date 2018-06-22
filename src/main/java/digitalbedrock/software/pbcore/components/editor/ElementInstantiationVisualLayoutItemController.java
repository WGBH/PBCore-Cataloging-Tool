package digitalbedrock.software.pbcore.components.editor;

import digitalbedrock.software.pbcore.core.models.entity.IPBCoreLayoutType;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.Objects;

public class ElementInstantiationVisualLayoutItemController {

    @FXML
    private Text previewElementLabel;
    @FXML
    private Text previewElementMediaType;
    @FXML
    private Text previewElementFormat;

    public void bind(PBCoreElement element) {
        if (element == null || element.getTypeForLayout() != IPBCoreLayoutType.INSTANTIATION) {
            return;
        }
        String pbcoreInstantiationMediaType = element.getOrderedSubElements().stream().filter(pbCoreElement -> Objects.equals(pbCoreElement.getName(), "instantiationMediaType")).map(PBCoreElement::getValue).findFirst().orElse(null);
        String pbcoreInstantiationDigital = element.getOrderedSubElements().stream().filter(pbCoreElement -> Objects.equals(pbCoreElement.getName(), "instantiationDigital")).map(PBCoreElement::getValue).findFirst().orElse(null);

        String pbcoreInstantiationPhysical = element.getOrderedSubElements().stream().filter(pbCoreElement -> Objects.equals(pbCoreElement.getName(), "instantiationPhysical")).map(PBCoreElement::getValue).findFirst().orElse(null);
        previewElementLabel.setText(pbcoreInstantiationDigital != null ? "DIGITAL:" : (pbcoreInstantiationPhysical != null ? "PHYSICAL:" : ""));
        previewElementMediaType.setText(" " + (pbcoreInstantiationMediaType == null ? "" : pbcoreInstantiationMediaType));
        previewElementFormat.setText(" (" + (pbcoreInstantiationDigital != null ? pbcoreInstantiationDigital : pbcoreInstantiationPhysical != null ? pbcoreInstantiationPhysical : "") + ")");
    }
}
