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
    private Label lblElementAlreadyAdded;

    @FXML
    private TreeView<PBCoreElement> treeElements;

    private PBCoreElement pbCoreElement;
    private PBCoreElement selectedElement;

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
            lblRepeatable.setText((value.isRepeatable() ? ", repeatable" : ""));
            lblElementAlreadyAdded.setVisible(false);
            btnAdd.setDisable(false);
            if (!newValue.getValue().isRepeatable() && containsSubElement(selectedElement, value)) {
                btnAdd.setDisable(true);
                lblElementAlreadyAdded.setVisible(true);
            }
        };
        treeElements.getSelectionModel().selectedItemProperty().addListener(listener);
        treeElements.getSelectionModel().select(0);
    }

    private boolean containsSubElement(PBCoreElement pbCoreElement, PBCoreElement value) {
        for (PBCoreElement coreElement : pbCoreElement.getSubElements()) {
            if (coreElement.getName().equals(value.getName())) {
                return true;
            } else {
                if (containsSubElement(coreElement, value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setElementSelectionListener(int index, ElementSelectionListener elementSelectionListener) {
        btnCancel.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> elementSelectionListener.onElementSelected(index, null));
        btnAdd.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            TreeItem<PBCoreElement> selectedItem = treeElements.getSelectionModel().getSelectedItem();
            PBCoreElement selectedElement = selectedItem.getValue().copy();
            TreeItem<PBCoreElement> item = selectedItem.getParent();
            while (item.getParent() != null) {
                item.getValue().clearOptionalSubElements();
                if (!selectedElement.isRequired()) {
                    item.getValue().addSubElement(selectedElement);
                }
                selectedElement = item.getValue().copy();
                item = item.getParent();
            }
            item.getValue().clearOptionalSubElements();
            if (!selectedElement.isRequired()) {
                item.getValue().addSubElement(selectedElement);
            }
            elementSelectionListener.onElementSelected(index, item.getParent() == null ? selectedElement.copy(false) : item.getValue().copy(false));
            /*}*/
        });
    }

    private TreeItem<PBCoreElement> getTreeItem(PBCoreElement rootElement) {
        TreeItem<PBCoreElement> pbCoreElementTreeItem = new TreeItem<>(rootElement);
        for (PBCoreElement coreElement : rootElement.getOrderedSubElements()) {
            pbCoreElementTreeItem.getChildren().add(getTreeItem(coreElement));
        }
        return pbCoreElementTreeItem;
    }

    public void setPbCoreElement(PBCoreElement pbCoreElement) {
        this.pbCoreElement = PBCoreStructure.getInstance().getElement(pbCoreElement.getFullPath()).copy();
        this.selectedElement = pbCoreElement;
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