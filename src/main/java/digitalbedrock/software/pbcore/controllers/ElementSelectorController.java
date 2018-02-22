package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElementType;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import digitalbedrock.software.pbcore.listeners.ElementSelectionListener;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

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
    private Label lblChoice;

    @FXML
    private TreeView<PBCoreElement> treeElements;

    private PBCoreElement selectedElement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeElements.setShowRoot(false);
        treeElements.setCellFactory(lv -> new PBCoreTreeCell());
        ChangeListener<TreeItem<PBCoreElement>> listener = (observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            PBCoreElement value = newValue.getValue();
            lblDescription.setText(value.getDescription());
            lblOptional.setText(value.isRequired() ? "required" : "optional");
            String repeatable = value.isRepeatable() ? ", repeatable" : "";
            String choice = value.isChoice() ? ", choice" : "";
            lblRepeatable.setText(repeatable + choice);
            lblElementAlreadyAdded.setText("Element already added");
            lblElementAlreadyAdded.setVisible(false);
            btnAdd.setDisable(false);
            if (!newValue.getValue().isRepeatable() && selectedElement.getName().equals(newValue.getParent().getValue().getName()) && containsSubElement(selectedElement, value)) {
                btnAdd.setDisable(true);
                lblElementAlreadyAdded.setVisible(true);
            }
            lblChoice.setVisible(false);
            if (value.isChoice()) {
                btnAdd.setDisable(true);
                lblChoice.setVisible(true);
            }
            if (selectedElement.isChoice()) {
                if (!containsSubElement(selectedElement, value)) {
                    lblElementAlreadyAdded.setText("Since an element of another type is already added, you cannot use an element of this type.");
                    lblElementAlreadyAdded.setVisible(true);
                    btnAdd.setDisable(true);
                }
            }
        };
        treeElements.getSelectionModel().selectedItemProperty().addListener(listener);
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

    public void setElementSelectionListener(String treeViewId, int index, ElementSelectionListener elementSelectionListener) {
        btnCancel.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            elementSelectionListener.onElementSelected(treeViewId, index, null);
        });
        btnAdd.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            TreeItem<PBCoreElement> selectedItem = treeElements.getSelectionModel().getSelectedItem();
            PBCoreElement selectedPBCoreElement = selectedItem.getValue().copy();
            TreeItem<PBCoreElement> item = selectedItem;
            while (item.getParent() != null) {
                if (item.getValue().isChoice()) {
                    selectedPBCoreElement = processChoiceElement(selectedPBCoreElement, item);
                } else {
                    selectedPBCoreElement = item.getValue().copy();
                }
                item = item.getParent();
            }
            selectedPBCoreElement.clearOptionalSubElements(selectedItem.getValue());
            if (!selectedPBCoreElement.isRequired()) {
                item.getValue().addSubElement(selectedPBCoreElement);
            }
            elementSelectionListener.onElementSelected(treeViewId, index, item.getParent() == null ? selectedPBCoreElement.copy(false) : item.getValue().copy(false));
        });
    }

    private PBCoreElement processChoiceElement(PBCoreElement selectedPBCoreElement, TreeItem<PBCoreElement> item) {
        PBCoreElement copy = item.getValue().copy();
        PBCoreElement finalSelectedPBCoreElement = selectedPBCoreElement;
        PBCoreElement pbCoreElement1 = copy.getSubElements().stream().filter(pbCoreElement -> pbCoreElement.getFullPath().contains(finalSelectedPBCoreElement.getFullPath())).findFirst().orElse(null);
        copy.getSubElements().clear();
        copy.addSubElement(pbCoreElement1);
        selectedPBCoreElement = copy;
        return selectedPBCoreElement;
    }

    private TreeItem<PBCoreElement> getTreeItem(PBCoreElement rootElement) {
        TreeItem<PBCoreElement> pbCoreElementTreeItem = new TreeItem<>(rootElement);
        rootElement.getOrderedSubElements().forEach((coreElement) -> pbCoreElementTreeItem.getChildren().add(getTreeItem(coreElement)));
        return pbCoreElementTreeItem;
    }

    public void setPbCoreElement(PBCoreElement pbCoreElement) {
        this.selectedElement = pbCoreElement;
        PBCoreElement copy = PBCoreStructure.getInstance().getElement(pbCoreElement.getFullPath()).copy();
        if (copy.getElementType() == PBCoreElementType.ROOT_ELEMENT) {
            copy.getSubElements().forEach(PBCoreElement::unmarkAsRootElement);
        }
        treeElements.setRoot(getTreeItem(copy));
        treeElements.getSelectionModel().select(0);
    }

    @Override
    public MenuBar createMenu() {
        return null;
    }

    private class PBCoreTreeCell extends TreeCell<PBCoreElement> {

        @Override
        protected void updateItem(PBCoreElement item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setText(item.getScreenName());
            } else {
                setText(null);
            }
        }
    }
}
