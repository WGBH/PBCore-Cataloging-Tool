package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import digitalbedrock.software.pbcore.listeners.ElementSelectionListener;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ElementSelectorController extends AbsController {

    @FXML
    private Label lblDescription;
    @FXML
    private Text lblOptional;
    @FXML
    private Text lblRepeatable;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnAdd;

    @FXML
    private TreeView<PBCoreElement> treeElements;

    private PBCoreElement pbCoreElement;

    @Override
    public void setMainController(MainController mainController) {
        super.setMainController(mainController);

        if (pbCoreElement == null) {
            return;
        }

        treeElements.setRoot(getTreeItem(pbCoreElement));
        treeElements.setShowRoot(false);
        treeElements.setCellFactory(lv -> new PBCoreTreeCell());
        ChangeListener<TreeItem<PBCoreElement>> listener = (observable, oldValue, newValue) -> {
            PBCoreElement value = newValue.getValue();
            lblDescription.setText(value.getDescription());
            lblOptional.setText((value.isRequired() ? "required" : "optional"));
            lblRepeatable.setText((value.isRepeatable() ? "repeatable" : ""));
        };
        treeElements.getSelectionModel().selectedItemProperty().addListener(listener);
        treeElements.getSelectionModel().select(0);

    }

    public void setElementSelectionListener(ElementSelectionListener elementSelectionListener) {
        btnCancel.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> elementSelectionListener.onElementSelected(null));
        btnAdd.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            TreeItem<PBCoreElement> selectedItem = treeElements.getSelectionModel().getSelectedItem();
            if (selectedItem.getParent().getParent() != null) {//in this case we are not looking at a root element so we should return the full parent
                elementSelectionListener.onElementSelected(selectedItem.getParent().getValue().clone());
            } else {
                elementSelectionListener.onElementSelected(selectedItem.getValue().clone());
            }
        });
    }

    private TreeItem<PBCoreElement> getTreeItem(PBCoreElement rootElement) {
        TreeItem<PBCoreElement> pbCoreElementTreeItem = new TreeItem<>(rootElement);
        for (PBCoreElement pbCoreElement : rootElement.getSubElements()) {
            pbCoreElementTreeItem.getChildren().add(getTreeItem(pbCoreElement));
        }
        return pbCoreElementTreeItem;
    }

    public void setPbCoreElement(PBCoreElement pbCoreElement) {
        this.pbCoreElement = PBCoreStructure.getInstance().getElement(pbCoreElement.getFullPath());
    }

    private class PBCoreTreeCell extends TreeCell<PBCoreElement> {
        @Override
        protected void updateItem(PBCoreElement item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setText(item.getScreenName());
            }
        }
    }
}