package digitalbedrock.software.pbcore.components.editor;

import digitalbedrock.software.pbcore.core.models.entity.IPBCore;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreAttribute;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class IPBCorePreviewItemListCell extends ListCell<IPBCore> {

    @Override
    protected void updateItem(IPBCore item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
        } else if (!item.isAttribute()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/preview_element_item.fxml"));
                Node graphic = loader.load();
                ElementVisualLayoutItemController controller = loader.getController();
                controller.bind((PBCoreElement) item);
                setGraphic(graphic);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/preview_attribute_item.fxml"));
                Node graphic = loader.load();
                AttributeVisualLayoutItemController controller = loader.getController();
                controller.bind((PBCoreAttribute) item);
                setGraphic(graphic);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }
        setPrefWidth(0);
    }
}
