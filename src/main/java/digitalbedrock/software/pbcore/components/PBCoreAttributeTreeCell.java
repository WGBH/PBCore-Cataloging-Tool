package digitalbedrock.software.pbcore.components;

import digitalbedrock.software.pbcore.controllers.DocumentAttributeItemController;
import digitalbedrock.software.pbcore.controllers.MainController;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreAttribute;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;

import java.io.IOException;

public class PBCoreAttributeTreeCell extends TreeCell<PBCoreAttribute> {
    private Node graphic;
    private DocumentAttributeItemController controller;
    private AttributeTreeCellListener attributeTreeCellListener;

    public PBCoreAttributeTreeCell(MainController mainController, AttributeTreeCellListener attributeTreeCellListener) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document_attribute_list_item.fxml"));
            graphic = loader.load();
            controller = loader.getController();
            controller.setMainController(mainController);
            this.attributeTreeCellListener = attributeTreeCellListener;
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    protected void updateItem(PBCoreAttribute attribute, boolean empty) {
        super.updateItem(attribute, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(graphic);
            controller.bind(attribute, attributeTreeCellListener);
        }
    }

    public interface AttributeTreeCellListener {
        void onRemoveAttribute(PBCoreAttribute pbCoreAttribute);
    }
}