package digitalbedrock.software.pbcore.components;

import digitalbedrock.software.pbcore.controllers.DocumentAttributeItemController;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreAttribute;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;

import java.io.IOException;

public class PBCoreAttributeTreeCell extends TreeCell<PBCoreAttribute> {

    private final AttributeTreeCellListener attributeTreeCellListener;

    public PBCoreAttributeTreeCell(AttributeTreeCellListener attributeTreeCellListener) {
        this.attributeTreeCellListener = attributeTreeCellListener;
    }

    @Override
    protected void updateItem(PBCoreAttribute attribute, boolean empty) {
        super.updateItem(attribute, empty);
        if (empty) {
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document_attribute_list_item.fxml"));
                Node graphic = loader.load();
                DocumentAttributeItemController controller = loader.getController();
                controller.bind(attribute, attributeTreeCellListener);
                setGraphic(graphic);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }
    }

    public interface AttributeTreeCellListener {

        void onRemoveAttribute(PBCoreAttribute pbCoreAttribute);
    }
}
