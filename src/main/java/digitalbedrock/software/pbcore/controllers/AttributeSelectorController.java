package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreAttribute;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import digitalbedrock.software.pbcore.listeners.AttributeSelectionListener;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.Objects;

public class AttributeSelectorController extends AbsController {

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
    private Label lblAttributeAlreadyAdded;

    @FXML
    private TreeView<PBCoreAttribute> treeElements;

    private PBCoreElement currentPbCoreElement;
    private PBCoreElement pbCoreElement;

    @Override
    public void setMainController(MainController mainController) {
        super.setMainController(mainController);

        if (pbCoreElement == null) {
            return;
        }
        lblRepeatable.setVisible(false);

        treeElements.setRoot(getTreeItem(pbCoreElement));
        treeElements.setShowRoot(false);
        treeElements.setCellFactory(lv -> new PBCoreTreeCell());
        ChangeListener<TreeItem<PBCoreAttribute>> listener = (observable, oldValue, newValue) -> {
            PBCoreAttribute value = newValue.getValue();
            lblDescription.setText(value.getDescription());
            lblOptional.setText(value.isRequired() ? "required" : "optional");

            btnAdd.setDisable(false);
            lblAttributeAlreadyAdded.setVisible(false);
            for (PBCoreAttribute pbCoreAttribute : currentPbCoreElement.getAttributes()) {
                if (Objects.equals(pbCoreAttribute.getName(), value.getName())) {
                    btnAdd.setDisable(true);
                    lblAttributeAlreadyAdded.setVisible(true);
                    return;
                }
            }
        };
        treeElements.getSelectionModel().selectedItemProperty().addListener(listener);
        treeElements.getSelectionModel().select(0);

    }

    public void setAttributeSelectionListener(AttributeSelectionListener attributeSelectionListener) {
        btnCancel.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> attributeSelectionListener.onAttributeSelected(null));
        btnAdd.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            TreeItem<PBCoreAttribute> selectedItem = treeElements.getSelectionModel().getSelectedItem();
            if (selectedItem.getParent().getParent() != null) {//in this case we are not looking at a root element so we should return the full parent
                attributeSelectionListener.onAttributeSelected(selectedItem.getParent().getValue().copy());
            } else {
                attributeSelectionListener.onAttributeSelected(selectedItem.getValue().copy());
            }
        });
    }

    private TreeItem<PBCoreAttribute> getTreeItem(PBCoreElement rootElement) {
        TreeItem<PBCoreAttribute> pbCoreElementTreeItem = new TreeItem<>();
        for (PBCoreAttribute pbCoreAttribute : rootElement.getAttributes()) {
            pbCoreElementTreeItem.getChildren().add(new TreeItem<>(pbCoreAttribute));
        }
        return pbCoreElementTreeItem;
    }

    public void setPbCoreElement(PBCoreElement pbCoreElement) {
        this.currentPbCoreElement = pbCoreElement;
        this.pbCoreElement = PBCoreStructure.getInstance().getElement(pbCoreElement.getFullPath());
    }

    private class PBCoreTreeCell extends TreeCell<PBCoreAttribute> {
        @Override
        protected void updateItem(PBCoreAttribute item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setText(item.getScreenName());
            }
        }
    }
}