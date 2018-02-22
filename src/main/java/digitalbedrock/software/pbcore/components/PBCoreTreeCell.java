package digitalbedrock.software.pbcore.components;

import digitalbedrock.software.pbcore.controllers.DocumentElementItemController;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;

import java.io.IOException;

public class PBCoreTreeCell extends TreeCell<PBCoreElement> {

    private DocumentElementItemController controller;
    private final DocumentElementItemController.DocumentElementInteractionListener listener;
    private final boolean allowRemovalOfAllElements;
    private final boolean showErrors;

    public PBCoreTreeCell(boolean showErrors, boolean allowRemovalOfAllElements, DocumentElementItemController.DocumentElementInteractionListener listener) {
        this.listener = listener;
        this.allowRemovalOfAllElements = allowRemovalOfAllElements;
        this.showErrors = showErrors;
    }

    @Override
    protected void updateItem(PBCoreElement element, boolean empty) {
        super.updateItem(element, empty);
        if (empty) {
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document_element_list_item.fxml"));
                Node graphic = loader.load();
                controller = loader.getController();
                controller.setDocumentElementInteractionListener(showErrors, allowRemovalOfAllElements, getIndex(), element, listener);
                setGraphic(graphic);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }

    }

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i);
        if (controller != null) {
            controller.updateIndex(i);
        }
    }
}
