package digitalbedrock.software.pbcore.components;

import digitalbedrock.software.pbcore.controllers.DocumentElementItemController;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;

import java.io.IOException;

public class PBCoreTreeCell extends TreeCell<PBCoreElement> {
    private Node graphic;
    private DocumentElementItemController controller;
    private DocumentElementItemController.DocumentElementInteractionListener listener;
    private PBCoreElement element;

    public PBCoreTreeCell(DocumentElementItemController.DocumentElementInteractionListener listener) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document_element_list_item.fxml"));
            graphic = loader.load();
            controller = loader.getController();
            this.listener = listener;
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    protected void updateItem(PBCoreElement element, boolean empty) {
        super.updateItem(element, empty);
        this.element = element;
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(graphic);
            controller.setDocumentElementInteractionListener(getIndex(), element, listener);
        }

    }

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i);
        if (controller != null && element != null) {
            controller.setDocumentElementInteractionListener(i, element, listener);
        }
    }
}