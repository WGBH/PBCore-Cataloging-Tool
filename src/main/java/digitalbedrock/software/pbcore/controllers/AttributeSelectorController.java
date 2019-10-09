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

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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
    private Button btnAddAndClose;
    @FXML
    private Label lblAttributeAlreadyAdded;

    @FXML
    private TreeView<PBCoreAttribute> treeElements;

    private PBCoreElement currentPbCoreElement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblRepeatable.setVisible(false);
        treeElements.setShowRoot(false);
        treeElements.setCellFactory(lv -> new PBCoreTreeCell());
        ChangeListener<TreeItem<PBCoreAttribute>> listener = (observable, oldValue, newValue) -> {
            updateAttributeUI(newValue.getValue());
        };
        treeElements.getSelectionModel().selectedItemProperty().addListener(listener);
    }

    private void updateAttributeUI(PBCoreAttribute value) {
        lblDescription.setText(value.getDescription());
        lblOptional.setText(value.isRequired() ? "required" : "optional");

        btnAdd.setDisable(false);
        btnAddAndClose.setDisable(false);
        lblAttributeAlreadyAdded.setVisible(false);
        for (PBCoreAttribute pbCoreAttribute : currentPbCoreElement.getAttributes()) {
            if (Objects.equals(pbCoreAttribute.getName(), value.getName())) {
                btnAdd.setDisable(true);
                btnAddAndClose.setDisable(true);
                lblAttributeAlreadyAdded.setVisible(true);
                return;
            }
        }
    }

    public void setAttributeSelectionListener(AttributeSelectionListener attributeSelectionListener) {
        btnCancel.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> attributeSelectionListener.onAttributeSelected(null, true));
        btnAdd.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            onAdd(attributeSelectionListener, false);
        });
        btnAddAndClose.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            onAdd(attributeSelectionListener, true);
        });
    }

    private void onAdd(AttributeSelectionListener attributeSelectionListener, boolean close) {
        TreeItem<PBCoreAttribute> selectedItem = treeElements.getSelectionModel().getSelectedItem();
        PBCoreAttribute copy;
        if (selectedItem.getParent().getParent() != null) {
            copy = selectedItem.getParent().getValue().copy();
            attributeSelectionListener.onAttributeSelected(copy, close);
        } else {
            copy = selectedItem.getValue().copy();
            attributeSelectionListener.onAttributeSelected(copy, close);
        }
        if (!close) {
            //currentPbCoreElement.addAttribute(copy);
            updateAttributeUI(copy);
        }
    }

    private TreeItem<PBCoreAttribute> getTreeItem(PBCoreElement rootElement) {
        TreeItem<PBCoreAttribute> pbCoreElementTreeItem = new TreeItem<>();
        rootElement.getAttributes().forEach((pbCoreAttribute) -> pbCoreElementTreeItem.getChildren().add(new TreeItem<>(pbCoreAttribute)));
        return pbCoreElementTreeItem;
    }

    public void setPbCoreElement(PBCoreElement pbCoreElement) {
        this.currentPbCoreElement = pbCoreElement;
        treeElements.setRoot(getTreeItem(PBCoreStructure.getInstance().getElement(pbCoreElement.getFullPath())));
        treeElements.getSelectionModel().select(0);
    }

    @Override
    public MenuBar createMenu() {
        return new MenuBar();
    }

    private class PBCoreTreeCell extends TreeCell<PBCoreAttribute> {

        @Override
        protected void updateItem(PBCoreAttribute item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setText(item.getScreenName());
                setTooltip(new Tooltip(item.getTooltip()));
            } else {
                setText(null);
                setTooltip(null);
            }
        }
    }
}
