package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.models.NewDocumentType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CVAttributeSelectorController extends AbsController {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblRepeatable.setVisible(false);
        treeElements.setShowRoot(false);
        treeElements.setCellFactory(lv -> new PBCoreTreeCell());
        ChangeListener<TreeItem<PBCoreAttribute>> listener = (observable, oldValue, newValue) -> {
            PBCoreAttribute value = newValue.getValue();
            lblDescription.setText(value.getDescription());
            lblOptional.setText(value.isRequired() ? "required" : "optional");

            btnAdd.setDisable(false);
            lblAttributeAlreadyAdded.setVisible(false);

            if (MainApp.getInstance().getRegistry().getControlledVocabularies().containsKey(value.getName())) {
                btnAdd.setDisable(true);
                lblAttributeAlreadyAdded.setVisible(true);
            }
        };
        treeElements.getSelectionModel().selectedItemProperty().addListener(listener);
        loadTree();
    }

    public void setAttributeSelectionListener(AttributeSelectionListener attributeSelectionListener) {
        btnCancel.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> attributeSelectionListener.onAttributeSelected(null, true));
        btnAdd.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            TreeItem<PBCoreAttribute> selectedItem = treeElements.getSelectionModel().getSelectedItem();
            MainApp.getInstance().getRegistry().createNewVocabulariesAggregator(selectedItem.getValue().getName(), true);
            attributeSelectionListener.onAttributeSelected(null, true);
        });
    }

    private TreeItem<PBCoreAttribute> getTreeItem() {
        TreeItem<PBCoreAttribute> pbCoreElementTreeItem = new TreeItem<>();
        List<PBCoreElement> elements = new ArrayList<>();
        elements.addAll(PBCoreStructure.getInstance().getRootElement(NewDocumentType.DESCRIPTION_DOCUMENT, true).getSubElements());
        elements.addAll(PBCoreStructure.getInstance().getRootElement(NewDocumentType.INSTANTIATION_DOCUMENT, true).getSubElements());
        while (!elements.isEmpty()) {
            PBCoreElement remove = elements.remove(0);
            remove.getAttributes().forEach((pbCoreAttribute) -> {
                //pbCoreAttribute.setElementScreenName(remove.getScreenName());
                if (pbCoreElementTreeItem.getChildren().stream().filter(pbCoreAttributeTreeItem -> pbCoreAttributeTreeItem.getValue().getName().equals(pbCoreAttribute.getName())).findFirst().orElse(null) == null) {
                    pbCoreElementTreeItem.getChildren().add(new TreeItem<>(pbCoreAttribute));
                }
            });
            elements.addAll(remove.getSubElements());
        }
        return pbCoreElementTreeItem;
    }


    public void loadTree() {
        treeElements.setRoot(getTreeItem());
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
