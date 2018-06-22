package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.components.AutoFillTextAreaBox;
import digitalbedrock.software.pbcore.components.PBCoreAnyValueListCell;
import digitalbedrock.software.pbcore.components.PBCoreAttributeTreeCell;
import digitalbedrock.software.pbcore.components.PBCoreTreeCell;
import digitalbedrock.software.pbcore.components.editor.AceEditor;
import digitalbedrock.software.pbcore.core.models.CV;
import digitalbedrock.software.pbcore.core.models.CVTerm;
import digitalbedrock.software.pbcore.core.models.ElementType;
import digitalbedrock.software.pbcore.core.models.entity.*;
import digitalbedrock.software.pbcore.listeners.*;
import digitalbedrock.software.pbcore.utils.PBCoreUtils;
import digitalbedrock.software.pbcore.utils.Registry;
import digitalbedrock.software.pbcore.utils.StringUtils;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentController extends AbsController implements ElementSelectionListener, AttributeSelectionListener, PBCoreAttributeTreeCell.AttributeTreeCellListener, SavableTabListener, CVSelectionListener {

    @FXML
    private ListView<PBCoreElementAnyValue> lvAnyValues;
    @FXML
    private TreeView<PBCoreElement> rootDocumentTreeView;
    @FXML
    private TreeView<PBCoreElement> requiredElementsListView;
    @FXML
    private TreeView<PBCoreElement> optionalElementsTreeView;
    @FXML
    private TreeView<PBCoreAttribute> attributesTreeView;
    @FXML
    private AutoFillTextAreaBox<CVTerm> taElementValue;
    @FXML
    private ComboBox<String> cbElementValue;
    @FXML
    private Button addAttributeButton;
    @FXML
    private AceEditor aceEditor;
    @FXML
    private VBox attributesVB;
    @FXML
    private FontIcon invalidValueIcon;
    @FXML
    private ColumnConstraints mainGridColumnLeft;
    @FXML
    private ColumnConstraints mainGridColumnCenter;
    @FXML
    private ColumnConstraints mainGridColumnRight;
    @FXML
    private Label documentValidationLbl;
    @FXML
    private FontIcon invalidDocumentIcon;
    @FXML
    private Button buttonSave;
    @FXML
    private Label statusBarDocumentType;
    @FXML
    private Label statusBarDocumentName;
    @FXML
    private Button addAnyValueButton;
    @FXML
    private Button btnShowInExplorer;
    @FXML
    private Button btnSelectCV;

    private final ObjectProperty<PBCoreElement> selectedPBCoreElementProperty = new SimpleObjectProperty<>();
    private PBCoreElement rootElement;

    private ChangeListener<TreeItem<PBCoreElement>> rootListener = null;
    private ChangeListener<TreeItem<PBCoreElement>> requiredListener = null;
    private ChangeListener<TreeItem<PBCoreElement>> optionalListener = null;

    private String token;
    private String currentId;
    private File file;
    private FileChangedListener fileChangedListener;

    private void onRemove(int index, PBCoreElement pbCoreElement, TreeView<PBCoreElement> treeView) {
        TreeItem<PBCoreElement> selectedItem;
        selectedItem = treeView.getTreeItem(index);
        selectedItem.getParent().getValue().removeSubElement(pbCoreElement);
        TreeItem<PBCoreElement> pbCoreElementToRemove = null;
        for (TreeItem<PBCoreElement> pbCoreElementTreeItem : selectedItem.getParent().getChildren()) {
            if (pbCoreElementTreeItem.getValue().getId() == pbCoreElement.getId() && StringUtils.compare(pbCoreElementTreeItem.getValue().getValue(), pbCoreElement.getValue())) {
                pbCoreElementToRemove = pbCoreElementTreeItem;
                break;
            }
        }
        if (pbCoreElementToRemove != null) {
            selectedItem.getParent().getChildren().remove(pbCoreElementToRemove);
        }
        selectedPBCoreElementProperty.setValue(null);
        updateXmlPreview();
        buttonSave.setVisible(true);
        lvAnyValues.setVisible(false);
        taElementValue.setDisable(true);
        taElementValue.getTextbox().setPromptText("no input required");
    }

    private void onAdd(String treeViewId, int index, PBCoreElement pbCoreElement) {
        menuOptionSelected(MenuOption.SELECT_ELEMENT, treeViewId, index, pbCoreElement, DocumentController.this);
    }

    private void onDuplicate(int index, PBCoreElement pbCoreElement, TreeView<PBCoreElement> treeView) {
        TreeItem<PBCoreElement> selectedItem = treeView.getTreeItem(index);
        PBCoreElement copy = pbCoreElement.copy(true, false);
        copy.valueProperty.addListener((observable, oldValue, newValue) -> updateXmlPreview());
        selectedItem.getParent().getValue().addSubElement(copy);
        TreeItem<PBCoreElement> itemToAdd;
        if (treeView.equals(requiredElementsListView)) {
            itemToAdd = getRequiredTreeItem(copy, pbCoreElement.getElementType() == PBCoreElementType.ROOT_ELEMENT);
        } else {
            itemToAdd = getOptionalTreeItem(copy, true);
        }
        int i = selectedItem.getParent().getChildren().indexOf(selectedItem);
        selectedItem.getParent().getChildren().add(i + 1, itemToAdd);

        selectedPBCoreElementProperty.setValue(copy);
        updateXmlPreview();
        buttonSave.setVisible(true);
    }

    private void loadRequiredTreeData(PBCoreElement rootElement) {
        TreeItem<PBCoreElement> requiredTreeItem = getRequiredTreeItem(rootElement, true);
        requiredElementsListView.setRoot(requiredTreeItem);
    }

    private void loadOptionalTreeData(PBCoreElement rootElement) {
        TreeItem<PBCoreElement> optionalTreeItem = getOptionalTreeItem(rootElement, false);
        optionalElementsTreeView.setRoot(optionalTreeItem);
    }

    private TreeItem<PBCoreAttribute> getAttributesTreeItem(PBCoreElement rootElement) {
        TreeItem<PBCoreAttribute> pbCoreAttributeTreeItem = new TreeItem<>();
        if (rootElement == null) {
            return pbCoreAttributeTreeItem;
        }
        List<PBCoreAttribute> attributes = rootElement.getAttributes();
        attributes.stream().peek((attribute) -> pbCoreAttributeTreeItem.getChildren().add(new TreeItem<>(attribute))).forEachOrdered((attribute) -> attribute.valueProperty.addListener((observable, oldValue, newValue) -> {
            buttonSave.setVisible(buttonSave.isVisible() || !Objects.equals(oldValue, newValue));
            updateXmlPreview();
        }));
        return pbCoreAttributeTreeItem;
    }

    private TreeItem<PBCoreElement> getRequiredTreeItem(PBCoreElement rootElement, boolean root) {
        TreeItem<PBCoreElement> pbCoreElementTreeItem = new TreeItem<>(rootElement);
        pbCoreElementTreeItem.setExpanded(true);
        rootElement.updateStatus();
        rootElement.valueProperty.addListener((observable, oldValue, newValue) -> updateXmlPreview());
        rootElement.validAttributesProperty.addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                updateStatusBarLabel(false, false);
            } else {
                updateStatusBarLabel();
            }
        });
        rootElement.validProperty.addListener((observable, oldValue, newValue) -> updateStatusBarLabel());
        rootElement.fatalErrorProperty.addListener((observable, oldValue, newValue) -> updateStatusBarLabel());
        List<PBCoreElement> subElements = rootElement.getOrderedSubElements();
        subElements.stream()
                .filter(pbCoreElement -> pbCoreElement.isRequired() || !root)
                .forEach(pbCoreElement
                        -> pbCoreElementTreeItem.getChildren()
                        .add(Math.min(pbCoreElement.getSequence(), pbCoreElementTreeItem.getChildren().size()),
                                getRequiredTreeItem(pbCoreElement, false)));
        return pbCoreElementTreeItem;
    }

    private TreeItem<PBCoreElement> getOptionalTreeItem(PBCoreElement rootElement, boolean hasNonRootParent) {
        TreeItem<PBCoreElement> pbCoreElementTreeItem = new TreeItem<>(rootElement);
        pbCoreElementTreeItem.setExpanded(true);
        rootElement.updateStatus();
        rootElement.valueProperty.addListener((observable, oldValue, newValue) -> updateXmlPreview());
        rootElement.validProperty.addListener((observable, oldValue, newValue) -> updateStatusBarLabel());
        rootElement.fatalErrorProperty.addListener((observable, oldValue, newValue) -> updateStatusBarLabel());
        List<PBCoreElement> subElements = rootElement.getOrderedSubElements();
        subElements.stream()
                .filter(pbCoreElement -> hasNonRootParent || !pbCoreElement.isRequired())
                .forEach(pbCoreElement
                        -> pbCoreElementTreeItem.getChildren()
                        .add(Math.min(pbCoreElement.getSequence(), pbCoreElementTreeItem.getChildren().size()),
                                getOptionalTreeItem(pbCoreElement, true)));
        return pbCoreElementTreeItem;
    }

    private void initListSelectionListeners() {
        rootListener = (observable, oldValue, newValue) -> {
            optionalElementsTreeView.getSelectionModel().selectedItemProperty().removeListener(optionalListener);
            requiredElementsListView.getSelectionModel().selectedItemProperty().removeListener(requiredListener);
            selectedPBCoreElementProperty.setValue(newValue == null ? null : newValue.getValue());
            taElementValue.setDisable(selectedPBCoreElementProperty.getValue() == null || selectedPBCoreElementProperty.getValue().isSupportsChildElements());
            if (selectedPBCoreElementProperty.getValue() == null || selectedPBCoreElementProperty.getValue().isSupportsChildElements()) {
                taElementValue.getTextbox().setPromptText("no input required");
            } else {
                taElementValue.getTextbox().setPromptText("enter here...");
            }
            setElementValueText(selectedPBCoreElementProperty.getValue());
            optionalElementsTreeView.getSelectionModel().clearSelection();
            optionalElementsTreeView.getSelectionModel().selectedItemProperty().addListener(optionalListener);
            requiredElementsListView.getSelectionModel().clearSelection();
            requiredElementsListView.getSelectionModel().selectedItemProperty().addListener(requiredListener);
        };
        requiredListener = (observable, oldValue, newValue) -> {
            optionalElementsTreeView.getSelectionModel().selectedItemProperty().removeListener(optionalListener);
            rootDocumentTreeView.getSelectionModel().selectedItemProperty().removeListener(rootListener);
            selectedPBCoreElementProperty.setValue(newValue == null ? null : newValue.getValue());
            taElementValue.setDisable(selectedPBCoreElementProperty.getValue() == null || selectedPBCoreElementProperty.getValue().isSupportsChildElements());
            if (selectedPBCoreElementProperty.getValue() == null || selectedPBCoreElementProperty.getValue().isSupportsChildElements()) {
                taElementValue.getTextbox().setPromptText("no input required");
            } else {
                taElementValue.getTextbox().setPromptText("enter here...");
            }
            setElementValueText(selectedPBCoreElementProperty.getValue());
            optionalElementsTreeView.getSelectionModel().clearSelection();
            optionalElementsTreeView.getSelectionModel().selectedItemProperty().addListener(optionalListener);
            rootDocumentTreeView.getSelectionModel().clearSelection();
            rootDocumentTreeView.getSelectionModel().selectedItemProperty().addListener(rootListener);
        };
        optionalListener = (observable, oldValue, newValue) -> {
            requiredElementsListView.getSelectionModel().selectedItemProperty().removeListener(requiredListener);
            rootDocumentTreeView.getSelectionModel().selectedItemProperty().removeListener(rootListener);
            selectedPBCoreElementProperty.setValue(newValue == null ? null : newValue.getValue());
            taElementValue.setDisable(selectedPBCoreElementProperty.getValue() == null || selectedPBCoreElementProperty.getValue().isSupportsChildElements());
            if (selectedPBCoreElementProperty.getValue() == null || selectedPBCoreElementProperty.getValue().isSupportsChildElements()) {
                taElementValue.getTextbox().setPromptText("no input required");
            } else {
                taElementValue.getTextbox().setPromptText("enter here...");
            }
            setElementValueText(selectedPBCoreElementProperty.getValue());
            requiredElementsListView.getSelectionModel().clearSelection();
            requiredElementsListView.getSelectionModel().selectedItemProperty().addListener(requiredListener);
            rootDocumentTreeView.getSelectionModel().clearSelection();
            rootDocumentTreeView.getSelectionModel().selectedItemProperty().addListener(rootListener);
        };
    }

    private void setElementValueText(PBCoreElement pbCoreElement) {
        if (pbCoreElement != null && pbCoreElement.isAnyElement()) {
            lvAnyValues.setItems(FXCollections.observableArrayList(pbCoreElement.getAnyValues()));
            updateInvalidIcon(false, false);
            addAnyValueButton.setVisible(true);
        } else {
            taElementValue.getData().clear();
            taElementValue.getTextbox().setText(pbCoreElement == null || pbCoreElement.getValue() == null ? null : pbCoreElement.getValue());
            if (pbCoreElement == null) {
                taElementValue.setFilterMode(false);
                invalidValueIcon.setVisible(false);
                return;
            }
            Registry registry = MainApp.getInstance().getRegistry();
            boolean b = registry.getControlledVocabularies().containsKey(pbCoreElement.getName());
            taElementValue.setFilterMode(b);
            if (b) {
                List<CVTerm> suggestions = new ArrayList<>();
                CV cv = registry.getControlledVocabularies().get(pbCoreElement.getName());
                if (cv.isHasSubs()) {
                    cv.getSubs().entrySet().forEach((stringCVBaseEntry) -> {
                        suggestions.addAll(stringCVBaseEntry.getValue().getTerms());
                    });
                } else {
                    suggestions.addAll(cv.getTerms());
                }
                taElementValue.getData().addAll(suggestions);
            }
            updateInvalidIcon(!pbCoreElement.isValid() && pbCoreElement.getElementType() != PBCoreElementType.ROOT_ELEMENT, pbCoreElement.isFatalError());
            addAnyValueButton.setVisible(false);
        }
    }

    private void updateInvalidIcon(boolean isInvalid, boolean fatalError) {
        invalidValueIcon.setVisible(isInvalid);
        if (invalidValueIcon.isVisible()) {
            invalidValueIcon.getStyleClass().remove("panicIcon");
            invalidValueIcon.getStyleClass().remove("warningIcon");
            if (fatalError) {
                invalidValueIcon.getStyleClass().add("panicIcon");
                invalidValueIcon.setIconCode(MaterialDesign.MDI_ALERT_CIRCLE);
            } else {
                invalidValueIcon.getStyleClass().add("warningIcon");
                invalidValueIcon.setIconCode(MaterialDesign.MDI_ALERT);
            }
            Tooltip tooltip = new Tooltip(getErrorMessage());
            invalidValueIcon.setOnMouseEntered(event -> {
                Point2D p = invalidValueIcon.localToScreen(invalidValueIcon.getLayoutBounds().getMaxX(), invalidValueIcon.getLayoutBounds().getMaxY());
                tooltip.show(invalidValueIcon, p.getX(), p.getY() + 2);
            });
            invalidValueIcon.setOnMouseExited(event -> tooltip.hide());
        } else {
            invalidValueIcon.getStyleClass().add("warningIcon");
            invalidValueIcon.setIconCode(MaterialDesign.MDI_ALERT);
            invalidValueIcon.setOnMouseEntered(null);
            invalidValueIcon.setOnMouseEntered(null);
        }
    }

    private String getErrorMessage() {
        if (selectedPBCoreElementProperty.getValue().isHasChildElements() && !selectedPBCoreElementProperty.getValue().isValid()) {
            if (selectedPBCoreElementProperty.getValue().isFatalError()) {
                invalidDocumentIcon.getStyleClass().add("panicIcon");
                invalidDocumentIcon.setIconCode(MaterialDesign.MDI_ALERT_CIRCLE);
                if (selectedPBCoreElementProperty.getValue().getSubElements().size() > 1) {
                    return "At least one child node has an invalid value";
                } else {
                    return "Child node has an invalid value";
                }
            } else {
                invalidDocumentIcon.getStyleClass().add("warningIcon");
                invalidDocumentIcon.setIconCode(MaterialDesign.MDI_ALERT);
                if (selectedPBCoreElementProperty.getValue().getSubElements().size() > 1) {
                    return "At least one child node is missing is respective value";
                } else {
                    return "Child node is missing is respective value";
                }
            }
        }
        switch (selectedPBCoreElementProperty.getValue().getElementValueRestrictionType()) {
            case ENUMERATION:
                for (String enumerationValue : selectedPBCoreElementProperty.getValue().getEnumerationValues()) {
                    if (selectedPBCoreElementProperty.getValue().getValue().equals(enumerationValue)) {
                        return "";
                    }
                }
                invalidDocumentIcon.getStyleClass().add("panicIcon");
                invalidDocumentIcon.setIconCode(MaterialDesign.MDI_ALERT_CIRCLE);
                return "Invalid value. Only one of " + selectedPBCoreElementProperty.getValue().getEnumerationValues() + " is allowed";
            case PATTERN:
                Pattern pattern = Pattern.compile(selectedPBCoreElementProperty.getValue().getPatternToFollow());
                String value = selectedPBCoreElementProperty.getValue().getValue();
                Matcher matcher = pattern.matcher(value == null ? "" : value);
                if (matcher.matches()) {
                    return "";
                }
                invalidDocumentIcon.getStyleClass().add("panicIcon");
                invalidDocumentIcon.setIconCode(MaterialDesign.MDI_ALERT_CIRCLE);
                return "Value doesn't match required pattern";
            case SIMPLE:
                if (selectedPBCoreElementProperty.getValue().isAnyElement() && selectedPBCoreElementProperty.getValue().getAnyValues().isEmpty()) {
                    return "This element should have at least one value associated";
                } else if (selectedPBCoreElementProperty.getValue().getValue() == null || selectedPBCoreElementProperty.getValue().getValue().trim().isEmpty()) {
                    return "Value missing";
                } else {
                    invalidDocumentIcon.getStyleClass().add("warningIcon");
                    invalidDocumentIcon.setIconCode(MaterialDesign.MDI_ALERT);
                    return "Value not matching any of the controlled vocabularies defined for this element";
                }
        }
        return "";
    }

    @FXML
    public void onAddAttribute(ActionEvent event) {
        MainApp.getInstance().showSelectAttribute(selectedPBCoreElementProperty.getValue(), DocumentController.this);
    }

    @Override
    public void onElementSelected(String treeViewId, int index, PBCoreElement element, boolean close) {

        if (element == null) {
            return;
        }
        ElementType elementType;
        TreeItem<PBCoreElement> selectedItem;
        TreeItem<PBCoreElement> pbCoreElementTreeItem;
        if (selectedPBCoreElementProperty.getValue() == null || selectedPBCoreElementProperty.getValue().getId() == rootElement.getId()) {
            if (element.isRequired()) {
                selectedItem = requiredElementsListView.getRoot();
                pbCoreElementTreeItem = getRequiredTreeItem(element, element.getElementType() == PBCoreElementType.ROOT_ELEMENT);
                elementType = ElementType.REQUIRED;
            } else {
                selectedItem = optionalElementsTreeView.getRoot();
                pbCoreElementTreeItem = getOptionalTreeItem(element, true);
                elementType = ElementType.OPTIONAL;
            }
        } else if (treeViewId.equals(requiredElementsListView.getId())) {
            selectedItem = requiredElementsListView.getTreeItem(index);
            pbCoreElementTreeItem = getRequiredTreeItem(element, element.getElementType() == PBCoreElementType.ROOT_ELEMENT);
            elementType = ElementType.REQUIRED;
        } else {
            selectedItem = optionalElementsTreeView.getTreeItem(index);
            if (selectedItem == null) {
                selectedItem = requiredElementsListView.getTreeItem(index);
                pbCoreElementTreeItem = getRequiredTreeItem(element, element.getElementType() == PBCoreElementType.ROOT_ELEMENT);
                elementType = ElementType.REQUIRED;
            } else {
                pbCoreElementTreeItem = getOptionalTreeItem(element, true);
                elementType = ElementType.OPTIONAL;
            }
        }
        selectedItem.getValue().addSubElement(element);

        pbCoreElementTreeItem.setExpanded(true);
        selectedItem.setExpanded(true);
        selectedItem.getChildren().add(Math.min(element.getSequence(), selectedItem.getChildren().isEmpty() ? 0 : selectedItem.getChildren().size() - 1), pbCoreElementTreeItem);
        if (close) {
            selectedPBCoreElementProperty.setValue(element);
            setElementValueText(element);
            switch (elementType) {
                case REQUIRED:
                    requiredElementsListView.getSelectionModel().select(0);
                    rootDocumentTreeView.getSelectionModel().clearSelection();
                    optionalElementsTreeView.getSelectionModel().clearSelection();
                    break;
                case OPTIONAL:
                    optionalElementsTreeView.getSelectionModel().select(0);
                    requiredElementsListView.getSelectionModel().clearSelection();
                    rootDocumentTreeView.getSelectionModel().clearSelection();
                    break;
            }
        }
        updateXmlPreview();
        updateStatusBarLabel();
        buttonSave.setVisible(true);
    }

    @Override
    public void onAttributeSelected(PBCoreAttribute pbCoreAttribute, boolean close) {
        if (pbCoreAttribute == null) {
            return;
        }
        selectedPBCoreElementProperty.getValue().addAttribute(pbCoreAttribute.copy());
        attributesTreeView.setRoot(getAttributesTreeItem(selectedPBCoreElementProperty.getValue()));
        updateXmlPreview();
        buttonSave.setVisible(true);
    }

    @Override
    public void onRemoveAttribute(PBCoreAttribute pbCoreAttribute) {
        selectedPBCoreElementProperty.getValue().removeAttribute(pbCoreAttribute);
        attributesTreeView.setRoot(getAttributesTreeItem(selectedPBCoreElementProperty.getValue()));
        updateXmlPreview();
        buttonSave.setVisible(true);
    }

    private void updateXmlPreview() {
        PBCoreElement value = requiredElementsListView.getRoot().getValue();
        aceEditor.updatePreview(value);
        MainApp.getInstance().getRegistry().savePBCoreElement(token, currentId, file, value);
    }

    private void updateStatusBarLabel() {
        boolean valid
                = rootDocumentTreeView.getRoot().getValue().isValid()
                && rootDocumentTreeView.getRoot().getValue().isValidAttributes()
                && requiredElementsListView.getRoot().getValue().isValid()
                && requiredElementsListView.getRoot().getValue().isValidAttributes()
                && optionalElementsTreeView.getRoot().getValue().isValid()
                && optionalElementsTreeView.getRoot().getValue().isValidAttributes();
        boolean fatalError = rootDocumentTreeView.getRoot().getValue().isFatalError() && requiredElementsListView.getRoot().getValue().isFatalError() && optionalElementsTreeView.getRoot().getValue().isFatalError();
        updateStatusBarLabel(valid, fatalError);
    }

    private void updateStatusBarLabel(boolean valid, boolean fatalError) {
        invalidDocumentIcon.getStyleClass().remove("panicIcon");
        invalidDocumentIcon.getStyleClass().remove("niceIcon");
        invalidDocumentIcon.getStyleClass().remove("warningIcon");
        documentValidationLbl.getStyleClass().remove("panicText");
        documentValidationLbl.getStyleClass().remove("niceText");
        documentValidationLbl.getStyleClass().remove("warningText");

        if (valid) {
            documentValidationLbl.setText("");
            invalidDocumentIcon.getStyleClass().add("niceIcon");
            invalidDocumentIcon.setIconCode(MaterialDesign.MDI_CHECK_CIRCLE);
            documentValidationLbl.getStyleClass().add("niceText");
        } else {
            if (fatalError) {
                documentValidationLbl.setText("Invalid file: missing mandatory values");
                invalidDocumentIcon.getStyleClass().add("panicIcon");
                invalidDocumentIcon.setIconCode(MaterialDesign.MDI_ALERT_CIRCLE);
                documentValidationLbl.getStyleClass().add("panicText");
            } else {
                documentValidationLbl.setText("Some elements are missing their respective values");
                invalidDocumentIcon.getStyleClass().add("warningIcon");
                invalidDocumentIcon.setIconCode(MaterialDesign.MDI_ALERT);
                documentValidationLbl.getStyleClass().add("warningText");
            }
        }
    }

    public void initializeDocument(String token, String currentId, File file, PBCoreElement pbCoreElement, FileChangedListener fileChangedListener, boolean changesDetected) {
        this.fileChangedListener = fileChangedListener;
        this.rootElement = pbCoreElement;
        this.file = file;
        this.currentId = currentId;
        this.token = token;
        btnShowInExplorer.setDisable(file == null);
        initListSelectionListeners();
        statusBarDocumentName.setText(file == null ? currentId : file.getName());
        statusBarDocumentType.setText(rootElement.getScreenName().toUpperCase());

        selectedPBCoreElementProperty.addListener((observable, oldValue, newValue) -> {
            btnSelectCV.setVisible(MainApp.getInstance().getRegistry().getControlledVocabularies().containsKey(selectedPBCoreElementProperty.getValue().getName()));
            attributesTreeView.setRoot(getAttributesTreeItem(newValue));
            addAttributeButton.setDisable(newValue == null || !newValue.isSupportsAttributes());
            if (newValue == null) {
                taElementValue.setVisible(true);
                cbElementValue.setVisible(false);
                invalidValueIcon.setVisible(false);
            } else {
                switch (newValue.getElementValueRestrictionType()) {
                    case ENUMERATION:
                        taElementValue.setVisible(false);
                        cbElementValue.setVisible(true);
                        lvAnyValues.setVisible(false);
                        cbElementValue.setItems(FXCollections.observableArrayList(newValue.getEnumerationValues()));
                        int i = newValue.getEnumerationValues().indexOf(newValue.getValue());
                        cbElementValue.getSelectionModel().select(i < 0 ? 0 : i);
                        break;
                    default:
                        if (newValue.isAnyElement()) {
                            lvAnyValues.setVisible(true);
                            taElementValue.setVisible(false);
                            cbElementValue.setVisible(false);
                        } else {
                            lvAnyValues.setVisible(false);
                            taElementValue.setVisible(true);
                            cbElementValue.setVisible(false);
                        }
                        cbElementValue.getItems().clear();
                }
            }
        });
        addAttributeButton.setDisable(true);

        TreeItem<PBCoreElement> elementTreeItem = new TreeItem<>(rootElement);
        rootDocumentTreeView.setRoot(elementTreeItem);
        rootDocumentTreeView.setCellFactory(lv -> new PBCoreTreeCell(false, false, new DocumentElementItemController.DocumentElementInteractionListener() {
            @Override
            public void onRemove(int index, PBCoreElement pbCoreElement) {
            }

            @Override
            public void onAdd(int index, PBCoreElement pbCoreElement) {
                rootDocumentTreeView.getSelectionModel().selectFirst();
                DocumentController.this.onAdd(rootDocumentTreeView.getId(), index, pbCoreElement);
            }

            @Override
            public void onDuplicate(int index, PBCoreElement pbCoreElement) {

            }
        }));

        requiredElementsListView.setShowRoot(false);
        requiredElementsListView.setCellFactory(lv -> new PBCoreTreeCell(true, false, new DocumentElementItemController.DocumentElementInteractionListener() {
            @Override
            public void onRemove(int index, PBCoreElement pbCoreElement) {
                DocumentController.this.onRemove(index, pbCoreElement, requiredElementsListView);
                requiredElementsListView.getSelectionModel().clearSelection();
            }

            @Override
            public void onAdd(int index, PBCoreElement pbCoreElement) {
                requiredElementsListView.getSelectionModel().select(index);
                DocumentController.this.onAdd(requiredElementsListView.getId(), index, pbCoreElement);
            }

            @Override
            public void onDuplicate(int index, PBCoreElement pbCoreElement) {
                DocumentController.this.onDuplicate(index, pbCoreElement, requiredElementsListView);
                requiredElementsListView.getSelectionModel().select(index);
            }
        }));
        loadRequiredTreeData(rootElement);

        optionalElementsTreeView.setShowRoot(false);
        optionalElementsTreeView.setCellFactory(lv -> new PBCoreTreeCell(true, false, new DocumentElementItemController.DocumentElementInteractionListener() {
            @Override
            public void onRemove(int index, PBCoreElement pbCoreElement) {
                DocumentController.this.onRemove(index, pbCoreElement, optionalElementsTreeView);
                optionalElementsTreeView.getSelectionModel().clearSelection();
            }

            @Override
            public void onAdd(int index, PBCoreElement pbCoreElement) {
                optionalElementsTreeView.getSelectionModel().select(index);
                DocumentController.this.onAdd(optionalElementsTreeView.getId(), index, pbCoreElement);
            }

            @Override
            public void onDuplicate(int index, PBCoreElement pbCoreElement) {
                DocumentController.this.onDuplicate(index, pbCoreElement, optionalElementsTreeView);
                optionalElementsTreeView.getSelectionModel().select(index + 1);
            }
        }));
        loadOptionalTreeData(rootElement);

        attributesTreeView.setShowRoot(false);
        attributesTreeView.setCellFactory(lv -> new PBCoreAttributeTreeCell(DocumentController.this, (pbCoreAttribute, listener) -> menuOptionSelected(MenuOption.SELECT_CV_ATTRIBUTE, pbCoreAttribute, listener)));

        requiredElementsListView.getSelectionModel().selectedItemProperty().addListener(requiredListener);
        optionalElementsTreeView.getSelectionModel().selectedItemProperty().addListener(optionalListener);

        taElementValue.getTextbox().textProperty().addListener((observable, oldValue, newValue) -> {
            PBCoreElement value = selectedPBCoreElementProperty.getValue();
            if (value == null) {
                return;
            }
            buttonSave.setVisible(buttonSave.isVisible() || !Objects.equals(value, newValue));
            value.setValue(taElementValue.getText());
            Registry registry = MainApp.getInstance().getRegistry();
            if (registry.getControlledVocabularies().containsKey(value.getName())) {
                List<CVTerm> suggestions = new ArrayList<>();
                CV cv = registry.getControlledVocabularies().get(value.getName());
                if (cv.isHasSubs()) {
                    cv.getSubs().entrySet().forEach((stringCVBaseEntry) -> {
                        suggestions.addAll(stringCVBaseEntry.getValue().getTerms());
                    });
                } else {
                    suggestions.addAll(cv.getTerms());
                }
                value.setValid(suggestions.stream().anyMatch(cvTerm -> cvTerm.getTerm().equalsIgnoreCase(taElementValue.getText())));
                PBCoreStructure.getInstance().updateSourceAttributeOnElement(value, taElementValue.getItem());
                attributesTreeView.setRoot(getAttributesTreeItem(selectedPBCoreElementProperty.getValue()));
                //updateXmlPreview();
            } else {
                if (!value.isHasChildElements()) {
                    switch (value.getElementValueRestrictionType()) {
                        case PATTERN:
                            Pattern pattern = Pattern.compile(value.getPatternToFollow());
                            String s = taElementValue.getText() == null ? "" : taElementValue.getText();
                            Matcher matcher = pattern.matcher(s);
                            value.setValid(!s.trim().isEmpty() && matcher.matches());
                            break;
                        default:
                            value.setValid(value.getValue() != null && !value.getValue().trim().isEmpty());
                            break;
                    }
                }
            }
            updateInvalidIcon(!value.isValid(), value.isFatalError());
        });
        cbElementValue.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedPBCoreElementProperty.getValue() == null || newValue == null) {
                return;
            }
            selectedPBCoreElementProperty.getValue().setValue(newValue);
            selectedPBCoreElementProperty.getValue().setValid(true);
        });
        taElementValue.setDisable(true);
        taElementValue.getTextbox().setPromptText("no input required");
        cbElementValue.setVisible(false);
        setElementValueText(selectedPBCoreElementProperty.getValue());
        updateXmlPreview();

        aceEditor.setEditorOpenedStateListener(new AceEditor.EditorOpenedStateListener() {
            @Override
            public void onEditorClosed() {
                GridPane.setRowIndex(attributesVB, 0);
                GridPane.setColumnIndex(attributesVB, 2);

                mainGridColumnLeft.setPercentWidth(34);
                mainGridColumnCenter.setPercentWidth(33);
                mainGridColumnRight.setPercentWidth(33);
            }

            @Override
            public void onEditorOpened() {
                GridPane.setRowIndex(attributesVB, 1);
                GridPane.setColumnIndex(attributesVB, 1);

                mainGridColumnLeft.setPercentWidth(25);
                mainGridColumnCenter.setPercentWidth(25);
                mainGridColumnRight.setPercentWidth(50);
            }
        });
        aceEditor.open();
        invalidValueIcon.setVisible(false);
        selectedPBCoreElementProperty.setValue(rootElement);
        requiredElementsListView.getSelectionModel().select(0);
        Platform.runLater(() -> taElementValue.requestFocus());
        updateStatusBarLabel();
        buttonSave.setVisible(file == null || changesDetected);

        lvAnyValues.setCellFactory(lv -> new PBCoreAnyValueListCell(pbCoreElementAnyValue -> {
            if (pbCoreElementAnyValue == null) {
                return;
            }
            selectedPBCoreElementProperty.getValue().removeAnyValue(pbCoreElementAnyValue);
            lvAnyValues.getItems().remove(pbCoreElementAnyValue);
            updateXmlPreview();
            updateInvalidIcon(false, false);
            buttonSave.setVisible(true);
        }));
        addAnyValueButton.setOnAction(event -> {
            menuOptionSelected(MenuOption.ADD_ELEMENT_ANY_VALUE, selectedPBCoreElementProperty.getValue(), (AddElementAnyValueListener) pbCoreElementAnyValue -> {
                if (pbCoreElementAnyValue == null) {
                    return;
                }
                selectedPBCoreElementProperty.getValue().addAnyElement(pbCoreElementAnyValue);
                lvAnyValues.getItems().add(pbCoreElementAnyValue);
                updateXmlPreview();
                updateInvalidIcon(false, false);
                buttonSave.setVisible(true);
            });
        });
    }

    @FXML
    void saveFile(ActionEvent event) {
        saveDocument();
        btnShowInExplorer.setDisable(false);
    }

    @Override
    public void saveDocument() {
        if (buttonSave.isVisible()) {
            saveDocument(false);
            btnShowInExplorer.setDisable(false);
        }
    }

    public void saveDocument(boolean close) {
        if (!buttonSave.isVisible()) {
            if (fileChangedListener != null) {
                fileChangedListener.onFileChanged(currentId, file, close);
                return;
            }
        }
        if (file == null) {
            if (close) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Unsaved changes");
                alert.setContentText("Your work is not currently saved. Do you want to save if before closing the tab?");
                alert.setHeaderText(null);
                ButtonType buttonTypeSave = new ButtonType("Save Changes");
                ButtonType buttonTypeDiscard = new ButtonType("Discard Changes");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeDiscard, buttonTypeCancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == buttonTypeSave) {
                        FileChooser fileChooser = new FileChooser();

                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Xml files (*.xml)", "*.xml");
                        fileChooser.getExtensionFilters().add(extFilter);

                        file = fileChooser.showSaveDialog(rootDocumentTreeView.getScene().getWindow());
                    } else if (result.get() == buttonTypeDiscard) {
                        fileChangedListener.discardChanges(currentId, file);
                        return;
                    }
                }
            } else {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialFileName("pbcore.xml");
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Xml files (*.xml)", "*.xml");
                fileChooser.getExtensionFilters().add(extFilter);

                file = fileChooser.showSaveDialog(rootDocumentTreeView.getScene().getWindow());
            }
        }
        if (file != null) {
            if (close) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Unsaved changes");
                alert.setContentText("Your work is not currently saved. Do you want to save if before closing the tab?");
                alert.setHeaderText(null);
                ButtonType buttonTypeSave = new ButtonType("Save Changes");
                ButtonType buttonTypeDiscard = new ButtonType("Discard Changes");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeDiscard, buttonTypeCancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == buttonTypeSave) {

                    } else if (result.get() == buttonTypeDiscard) {
                        fileChangedListener.discardChanges(currentId, file);
                        return;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            statusBarDocumentName.setText(file == null ? currentId : file.getName());
            PBCoreElement value = requiredElementsListView.getRoot().getValue();
            try {
                PBCoreStructure.getInstance().saveFile(value, file);
                if (fileChangedListener != null) {
                    fileChangedListener.onFileChanged(currentId, file, close);
                }
                buttonSave.setVisible(false);
                this.currentId = file.getAbsolutePath();
            } catch (ParserConfigurationException | IOException | TransformerException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void saveDocumentAs() {
        FileChooser fileChooser = new FileChooser();

        String split = file == null ? "pbcore.xml" : file.getName().split(".xml")[0];
        fileChooser.setInitialFileName(split);

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Xml files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File fileChooserResultFile = fileChooser.showSaveDialog(rootDocumentTreeView.getScene().getWindow());
        if (fileChooserResultFile != null) {
            PBCoreElement value = requiredElementsListView.getRoot().getValue();
            try {
                PBCoreStructure.getInstance().saveFile(value, fileChooserResultFile);
                if (fileChangedListener != null) {
                    fileChangedListener.onFileChanged(currentId, fileChooserResultFile, false);
                }
                buttonSave.setVisible(false);
                this.file = fileChooserResultFile;
                this.currentId = fileChooserResultFile.getAbsolutePath();
                statusBarDocumentName.setText(fileChooserResultFile.getName());
            } catch (ParserConfigurationException | IOException | TransformerException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void saveDocumentAsTemplate() {
        FileChooser fileChooser = new FileChooser();
        String split = file == null ? "pbcore_template.xml" : file.getName().split(".xml")[0] + "_template.xml";
        fileChooser.setInitialFileName(split);

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Xml files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File fileChooserResultFile = fileChooser.showSaveDialog(rootDocumentTreeView.getScene().getWindow());
        if (fileChooserResultFile != null) {
            PBCoreElement value = requiredElementsListView.getRoot().getValue();
            try {
                PBCoreStructure.getInstance().saveFileAsTemplate(value, fileChooserResultFile);
            } catch (ParserConfigurationException | IOException | TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addBatchUpdate(PBCoreElement pbCoreElement) {
        List<PBCoreElement> pbCoreElements = new ArrayList<>();
        if (rootElement.getName().equalsIgnoreCase("pbcorecollection")) {
            pbCoreElements.addAll(rootElement.getSubElements());
        } else {
            pbCoreElements.add(rootElement);
        }
        Iterator<PBCoreElement> iterator = pbCoreElements.iterator();
        while (iterator.hasNext()) {
            PBCoreElement next = iterator.next();
            pbCoreElement.getSubElements().forEach((elementToAdd) -> {
                if (!elementToAdd.getFullPath().contains(next.getFullPath())) {
                    return;
                }
                String fullP = elementToAdd.getFullPath();
                fullP = fullP.replace(elementToAdd.getName(), "");
                int i = fullP.lastIndexOf("/");
                if (i != -1) {
                    fullP = fullP.substring(0, i);
                }
                if (!(!next.getFullPath().contains(fullP)
                        || (!elementToAdd.isRepeatable()
                        && next.getSubElements().stream().anyMatch(pbc -> Objects.equals(pbc.getFullPath(), elementToAdd.getFullPath()))))) {
                    next.addSubElement(elementToAdd.copy(true));
                }
            });
        }
        loadOptionalTreeData(rootElement);
        loadRequiredTreeData(rootElement);
        updateXmlPreview();
        updateStatusBarLabel();
    }

    @Override
    public MenuBar createMenu() {
        return new MenuBar();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public boolean isExportable() {
        return rootElement.getName().equalsIgnoreCase("pbcoreDescriptionDocument");
    }

    @Override
    public void onShown() {
        super.onShown();
        aceEditor.reload();
    }

    public void onShow() {
        aceEditor.reload();
        Platform.runLater(() -> taElementValue.requestFocus());
    }

    @FXML
    public void showInExplorer(ActionEvent actionEvent) {
        if (PBCoreUtils.isWindows()) {
            try {
                Runtime.getRuntime().exec("explorer.exe /select," + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (PBCoreUtils.isMac()) {
            try {
                Runtime.getRuntime().exec("open --reveal " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.open(file.getParentFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void selectCV(ActionEvent actionEvent) {
        if (MainApp.getInstance().getRegistry().getControlledVocabularies().containsKey(selectedPBCoreElementProperty.getValue().getName())) {
            menuOptionSelected(MenuOption.SELECT_CV_ELEMENT, selectedPBCoreElementProperty.getValue(), DocumentController.this);
        }
    }

    @Override
    public void onCVSelected(String key, CVTerm cvTerm, boolean attr) {
        PBCoreElement value = selectedPBCoreElementProperty.getValue();
        if (cvTerm != null && !attr && value != null && value.getName().equals(key)) {
            value.setValue(cvTerm.getTerm());
            PBCoreStructure.getInstance().updateSourceAttributeOnElement(value, cvTerm);
            attributesTreeView.setRoot(getAttributesTreeItem(value));
            setElementValueText(value);
        }
    }
}
