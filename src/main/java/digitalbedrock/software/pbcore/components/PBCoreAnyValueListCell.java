package digitalbedrock.software.pbcore.components;

import digitalbedrock.software.pbcore.controllers.DocumentElementAnyValueItemController;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElementAnyValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class PBCoreAnyValueListCell extends ListCell<PBCoreElementAnyValue> {

    private DocumentElementAnyValueItemController controller;
    private final AnyValueListCellListener listener;

    public PBCoreAnyValueListCell(AnyValueListCellListener listener) {
        this.listener = listener;
    }

    @Override
    protected void updateItem(PBCoreElementAnyValue element, boolean empty) {
        super.updateItem(element, empty);
        if (empty) {
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document_element_any_value_list_item.fxml"));
                Node graphic = loader.load();
                controller = loader.getController();
                controller.bind(element, listener);
                setGraphic(graphic);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }

    }

    public interface AnyValueListCellListener {

        void onRemove(PBCoreElementAnyValue pbCoreElementAnyValue);
    }
}
