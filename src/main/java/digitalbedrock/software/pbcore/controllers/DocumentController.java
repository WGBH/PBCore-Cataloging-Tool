package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.ElementType;
import digitalbedrock.software.pbcore.core.models.NewDocumentType;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import digitalbedrock.software.pbcore.listeners.ElementSelectionListener;
import digitalbedrock.software.pbcore.utils.StringUtils;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;


public class DocumentController extends AbsController implements ElementSelectionListener, DocumentElementController.DocumentElementInteractionListener {
    private static final int ROW_HEIGHT = 55;
    @FXML
    private TreeView<PBCoreElement> rootDocumentTreeView;
    @FXML
    private TreeView<PBCoreElement> requiredElementsListView;
    @FXML
    private TreeView<PBCoreElement> optionalElementsTreeView;
    @FXML
    private TextArea taElementValue;

    private NewDocumentType selectedDocumentType = NewDocumentType.DESCRIPTION_DOCUMENT;

    PBCoreElement selectedPBCoreElement;
    PBCoreElement rootElement;

    private ChangeListener<TreeItem<PBCoreElement>> rootListener = null;
    private ChangeListener<TreeItem<PBCoreElement>> requiredListener = null;
    private ChangeListener<TreeItem<PBCoreElement>> optionalListener = null;

    public DocumentController() {
    }

    @Override
    public void setMainController(MainController mainController) {
        super.setMainController(mainController);

        initListSelectionListeners();

        rootElement = PBCoreStructure.getInstance().getRootElement(selectedDocumentType);
        rootElement.clearOptionalSubElements();//TODO:

        TreeItem<PBCoreElement> elementTreeItem = new TreeItem<>(rootElement);
        rootDocumentTreeView.setRoot(elementTreeItem);
        rootDocumentTreeView.setCellFactory(lv -> new PBCoreTreeCell(DocumentController.this));

        requiredElementsListView.setShowRoot(false);
        requiredElementsListView.setCellFactory(lv -> new PBCoreTreeCell(DocumentController.this));
        loadRequiredTreeData(rootElement);


        optionalElementsTreeView.setShowRoot(false);
        optionalElementsTreeView.setCellFactory(lv -> new PBCoreTreeCell(DocumentController.this));
        loadOptionalTreeData(rootElement);


        //rootDocumentTreeView.getSelectionModel().selectedItemProperty().addListener(rootListener);
        requiredElementsListView.getSelectionModel().selectedItemProperty().addListener(requiredListener);
        optionalElementsTreeView.getSelectionModel().selectedItemProperty().addListener(optionalListener);

        taElementValue.textProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedPBCoreElement == null) {
                return;
            }
            selectedPBCoreElement.setValue(taElementValue.getText());
        });
    }

    private void loadRequiredTreeData(PBCoreElement rootElement) {
        TreeItem<PBCoreElement> requiredTreeItem = getRequiredTreeItem(rootElement, true);
        requiredElementsListView.setRoot(requiredTreeItem);
    }

    private void loadOptionalTreeData(PBCoreElement rootElement) {
        TreeItem<PBCoreElement> optionalTreeItem = getOptionalTreeItem(rootElement, false);
        optionalElementsTreeView.setRoot(optionalTreeItem);
    }

    private TreeItem<PBCoreElement> getRequiredTreeItem(PBCoreElement rootElement, boolean root) {
        TreeItem<PBCoreElement> pbCoreElementTreeItem = new TreeItem<>(rootElement);
        rootElement.updateStatus();
        List<PBCoreElement> subElements = rootElement.getSubElements();
        subElements.sort(Comparator.comparing(PBCoreElement::getName));
        for (PBCoreElement pbCoreElement : subElements) {
            if (pbCoreElement.isRequired() || !root) {
                pbCoreElementTreeItem.getChildren().add(getRequiredTreeItem(pbCoreElement, false));
            }
        }
        return pbCoreElementTreeItem;
    }

    private TreeItem<PBCoreElement> getOptionalTreeItem(PBCoreElement rootElement, boolean hasNonRootParent) {
        TreeItem<PBCoreElement> pbCoreElementTreeItem = new TreeItem<>(rootElement);
        rootElement.updateStatus();
        List<PBCoreElement> subElements = rootElement.getSubElements();
        subElements.sort(Comparator.comparing(PBCoreElement::getName));
        for (PBCoreElement pbCoreElement : subElements) {
            if (hasNonRootParent || !pbCoreElement.isRequired()) {
                pbCoreElementTreeItem.getChildren().add(getOptionalTreeItem(pbCoreElement, true));
            }
        }
        return pbCoreElementTreeItem;
    }

    private void initListSelectionListeners() {
        rootListener = getPbCoreElementChangeListener(ElementType.ROOT);
        requiredListener = getPbCoreElementChangeListener(ElementType.REQUIRED);
        optionalListener = getPbCoreElementChangeListener(ElementType.OPTIONAL);
    }

    private ChangeListener<TreeItem<PBCoreElement>> getPbCoreElementChangeListener(ElementType elementType) {
        return (observable, oldValue, newValue) -> {
            TreeView<PBCoreElement> treeView1;
            ChangeListener<TreeItem<PBCoreElement>> pbCoreElementChangeListener1 = null;
            TreeView<PBCoreElement> treeView2;
            ChangeListener<TreeItem<PBCoreElement>> pbCoreElementChangeListener2;
            switch (elementType) {
                case ROOT:
                    treeView1 = optionalElementsTreeView;
                    pbCoreElementChangeListener1 = optionalListener;
                    treeView2 = requiredElementsListView;
                    pbCoreElementChangeListener2 = requiredListener;
                    break;
                case REQUIRED:
                    treeView1 = optionalElementsTreeView;
                    pbCoreElementChangeListener1 = optionalListener;
                    treeView2 = rootDocumentTreeView;
                    pbCoreElementChangeListener2 = rootListener;
                    break;
                case OPTIONAL:
                    treeView1 = requiredElementsListView;
                    pbCoreElementChangeListener1 = requiredListener;
                    treeView2 = rootDocumentTreeView;
                    pbCoreElementChangeListener2 = rootListener;
                    break;
                default:
                    return;
            }
            treeView1.getSelectionModel().selectedItemProperty().removeListener(pbCoreElementChangeListener1);
            treeView2.getSelectionModel().selectedItemProperty().removeListener(pbCoreElementChangeListener2);
            selectedPBCoreElement = newValue == null ? null : newValue.getValue();
            Object value = newValue == null ? null : selectedPBCoreElement.getValue();
            setTaElementValueText(value);
            taElementValue.setDisable(selectedPBCoreElement != null && !selectedPBCoreElement.getSubElements().isEmpty());
            treeView1.getSelectionModel().clearSelection();
            treeView1.getSelectionModel().selectedItemProperty().addListener(pbCoreElementChangeListener1);
            treeView2.getSelectionModel().clearSelection();
            treeView2.getSelectionModel().selectedItemProperty().addListener(pbCoreElementChangeListener2);
        };
    }

    private void setTaElementValueText(Object value) {
        taElementValue.setText(value == null ? null : value.toString());
    }

    public void setSelectedDocumentType(NewDocumentType selectedDocumentType) {
        this.selectedDocumentType = selectedDocumentType;
    }

    @Override
    public void onElementSelected(PBCoreElement element) {
        System.out.println("Add");
        System.out.println("->Before:" + rootElement.getSubElements().size());
        if (element == null) {
            return;
        }
        System.out.println(element);
        rootElement.addSubElement(element);
        if (element.isRequired()) {
            loadRequiredTreeData(rootElement);
        } else {
            loadOptionalTreeData(rootElement);
        }
        selectedPBCoreElement = element;
        setTaElementValueText(element.getValue());
        System.out.println("->After:" + rootElement.getSubElements().size());
    }

    @Override
    public void onRemove(int index, PBCoreElement pbCoreElement) {
        System.out.println("Remove -> " + index);
        TreeItem<PBCoreElement> selectedItem;
        if (pbCoreElement.isRequired()) {
            selectedItem = requiredElementsListView.getTreeItem(index);
        } else {
            selectedItem = optionalElementsTreeView.getTreeItem(index);
        }
        selectedItem.getParent().getValue().removeSubElement(pbCoreElement);
        TreeItem<PBCoreElement> pbCoreElementToRemove = null;
        for (TreeItem<PBCoreElement> pbCoreElementTreeItem : selectedItem.getParent().getChildren()) {
            if (pbCoreElementTreeItem.getValue().getId() == pbCoreElement.getId() && StringUtils.compare(pbCoreElementTreeItem.getValue().getValue(), pbCoreElement.getValue())) {
                pbCoreElementToRemove = pbCoreElementTreeItem;
                break;
            }
        }
        if (pbCoreElementToRemove != null) {
            if (selectedItem.getParent().getValue().getId() == rootElement.getId()) {
                //rootElement.removeSubElement(pbCoreElement);
            }
            selectedItem.getParent().getChildren().remove(pbCoreElementToRemove);
        }
    }

    @Override
    public void onAdd(PBCoreElement pbCoreElement) {
        mainController.showSelectElement(pbCoreElement, DocumentController.this);
    }

    @Override
    public void onDuplicate(PBCoreElement pbCoreElement) {
        System.out.println("Duplicate");
        onElementSelected(pbCoreElement.clone());
    }

    private class PBCoreTreeCell extends TreeCell<PBCoreElement> {
        private Node graphic;
        private DocumentElementController controller;
        private DocumentElementController.DocumentElementInteractionListener listener;

        public PBCoreTreeCell(DocumentElementController.DocumentElementInteractionListener listener) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document_element_list_item.fxml"));
                graphic = loader.load();
                controller = loader.getController();
                this.listener = listener;
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }

        @Override
        protected void updateItem(PBCoreElement element, boolean empty) {
            super.updateItem(element, empty);
            if (empty) {
                setGraphic(null);
            } else {
                controller.getTitleLabel().setText(element.getName());
                setGraphic(graphic);
                controller.setDocumentElementInteractionListener(getIndex(), element, listener);
            }

        }

    }

}
