package digitalbedrock.software.pbcore.components.editor;

import digitalbedrock.software.pbcore.core.models.entity.IPBCore;
import digitalbedrock.software.pbcore.core.models.entity.IPBCoreLayoutType;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreAttribute;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class IPBCorePreviewItemListCell extends ListCell<IPBCore> {

    private final ItemSelectedListener itemSelectedListener;

    public IPBCorePreviewItemListCell(ItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    @Override
    protected void updateItem(IPBCore item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
            return;
        } else if (item.getTypeForLayout() == IPBCoreLayoutType.DUMMY) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/preview_dummy_items_element_item.fxml"));
                Node graphic = loader.load();
                setGraphic(graphic);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        } else if (item.getTypeForLayout() == IPBCoreLayoutType.INSTANTIATION) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/preview_instantiation_element_item.fxml"));
                Node graphic = loader.load();
                ElementInstantiationVisualLayoutItemController controller = loader.getController();
                controller.bind((PBCoreElement) item);
                setGraphic(graphic);
                getStyleClass().add("itemCell");
                graphic.setOnMouseClicked(event -> {
                    if (itemSelectedListener != null) {
                        itemSelectedListener.onItemSelected(item);
                    }
                });
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        } else if (!item.isAttribute()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(item.isAnyElement() ? "/fxml/preview_element_any_values_item.fxml" : "/fxml/preview_element_item.fxml"));
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
        double v = getGraphic().getLayoutX() + ((item.getIndex() - 1) * 10);
        getGraphic().translateXProperty().setValue(v);
    }

    public interface ItemSelectedListener {

        void onItemSelected(IPBCore element);
    }
}
