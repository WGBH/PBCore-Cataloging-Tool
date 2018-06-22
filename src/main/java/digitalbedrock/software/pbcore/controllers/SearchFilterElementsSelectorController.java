package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.NewDocumentType;
import digitalbedrock.software.pbcore.core.models.entity.IPBCore;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import digitalbedrock.software.pbcore.listeners.SearchFilterElementsSelectionListener;
import digitalbedrock.software.pbcore.lucene.LuceneEngineSearchFilter;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.controlsfx.control.CheckTreeView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class SearchFilterElementsSelectorController extends AbsController {

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
    private CheckBox cbAll;

    @FXML
    private CheckTreeView<IPBCore> treeElements;

    private LuceneEngineSearchFilter searchFilter;

    private int allElementsCount = 0;
    private int allAttributesCount = 0;
    private int allCount = 0;
    private final AtomicBoolean ignore = new AtomicBoolean();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeElements.setShowRoot(false);
        treeElements.setCellFactory(lv -> new PBCoreTreeCell());
        ChangeListener<TreeItem<IPBCore>> listener = (observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            IPBCore value = newValue.getValue();
            lblDescription.setText(value.getDescription());
            lblOptional.setText(value.isRequired() ? "required" : "optional");
            lblRepeatable.setText(value.isRepeatable() ? ", repeatable" : "");
            btnAdd.setDisable(false);
        };
        treeElements.getSelectionModel().selectedItemProperty().addListener(listener);
        cbAll.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (ignore.get()) {
                return;
            }
            if (newValue) {
                treeElements.getCheckModel().checkAll();
            } else {
                treeElements.getCheckModel().clearChecks();
            }
        });
        treeElements.checkModelProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getCheckedItems().size() == allElementsCount) {
                ignore.set(true);
                cbAll.setSelected(true);
                ignore.set(false);
            }
        });
    }

    public void setElementSelectionListener(int index, SearchFilterElementsSelectionListener elementSelectionListener) {
        btnCancel.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> elementSelectionListener.onFiltersDefined(index, null));
        btnAdd.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            List<TreeItem<IPBCore>> selectedItem = treeElements.getCheckModel().getCheckedItems();
            searchFilter.updateFieldsToSearch(selectedItem.stream().filter(pbCoreElementTreeItem -> !pbCoreElementTreeItem.getValue().isHasChildElements()).map(TreeItem::getValue).collect(Collectors.toList()), allCount);
            elementSelectionListener.onFiltersDefined(index, searchFilter);
        });
    }

    private CheckBoxTreeItem<IPBCore> getTreeItem(PBCoreElement rootElement, List<TreeItem<IPBCore>> itemsToCheck, List<TreeItem<IPBCore>> itemsToUnCheck) {
        CheckBoxTreeItem<IPBCore> pbCoreElementTreeItem = new CheckBoxTreeItem<>(rootElement);
        pbCoreElementTreeItem.setIndependent(true);
        if (!rootElement.isHasChildElements()) {
            allElementsCount++;
            allCount++;
            if (searchFilter.getFieldsToSearch().stream().filter(ipbCore -> Objects.equals(ipbCore.getFullPath(), rootElement.getFullPath())).count() > 0) {
                itemsToCheck.add(pbCoreElementTreeItem);
            } else {
                itemsToUnCheck.add(pbCoreElementTreeItem);
            }
        }
        rootElement.getOrderedSubElements().stream().map((coreElement) -> getTreeItem(coreElement.copy(), itemsToCheck, itemsToUnCheck)).map((treeItem) -> {
            treeItem.setExpanded(true);
            return treeItem;
        }).forEachOrdered((treeItem) -> pbCoreElementTreeItem.getChildren().add(treeItem));
        rootElement.getAttributes().forEach((coreElement) -> {
            CheckBoxTreeItem<IPBCore> treeItem = new CheckBoxTreeItem<>(coreElement.copy());
            treeItem.setExpanded(true);
            pbCoreElementTreeItem.getChildren().add(treeItem);
            allCount++;
            allAttributesCount++;
            if (searchFilter.getFieldsToSearch().stream().filter(ipbCore -> Objects.equals(ipbCore.getFullPath(), coreElement.getFullPath())).count() > 0) {
                itemsToCheck.add(treeItem);
            } else {
                itemsToUnCheck.add(pbCoreElementTreeItem);
            }
        });
        return pbCoreElementTreeItem;
    }

    public void setSearchFilter(LuceneEngineSearchFilter searchF) {
        this.searchFilter = searchF;
        List<TreeItem<IPBCore>> itemsToCheck = new ArrayList<>();
        List<TreeItem<IPBCore>> itemsToUncheck = new ArrayList<>();
        treeElements.setRoot(getTreeItem(PBCoreStructure.getInstance().getRootElement(NewDocumentType.DESCRIPTION_DOCUMENT, true), itemsToCheck, itemsToUncheck));
        treeElements.getSelectionModel().select(0);
        cbAll.setSelected(searchF.isAllElements());
        if (!searchF.isAllElements()) {
            ignore.set(true);
            itemsToUncheck.forEach((ipbCoreTreeItem) -> treeElements.getCheckModel().clearCheck(ipbCoreTreeItem));
            itemsToCheck.forEach((ipbCoreTreeItem) -> treeElements.getCheckModel().check(ipbCoreTreeItem));
            ignore.set(false);
        }
    }

    @Override
    public MenuBar createMenu() {
        return null;
    }

    private class PBCoreTreeCell extends CheckBoxTreeCell<IPBCore> {

        @Override
        public void updateItem(IPBCore item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            if (empty) {
                setGraphic(null);
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search_field_item.fxml"));
                    Node graphic = loader.load();
                    SearchFieldItemController controller = loader.getController();
                    controller.bind(item);
                    HBox hBox = new HBox();
                    hBox.getChildren().add(getGraphic());
                    hBox.getChildren().add(graphic);
                    setGraphic(hBox);
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        }
    }
}
