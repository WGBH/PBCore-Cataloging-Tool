package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.components.AutoFillTextBoxPBCoreElementSkin;
import digitalbedrock.software.pbcore.components.PBCoreAttributeTreeCell;
import digitalbedrock.software.pbcore.components.PBCoreTreeCell;
import digitalbedrock.software.pbcore.components.editor.AceEditor;
import digitalbedrock.software.pbcore.core.models.CVTerm;
import digitalbedrock.software.pbcore.core.models.ElementType;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreAttribute;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElementType;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import digitalbedrock.software.pbcore.listeners.AttributeSelectionListener;
import digitalbedrock.software.pbcore.listeners.ElementSelectionListener;
import digitalbedrock.software.pbcore.listeners.FileChangedListener;
import digitalbedrock.software.pbcore.listeners.SavableTabListener;
import digitalbedrock.software.pbcore.utils.Registry;
import digitalbedrock.software.pbcore.utils.StringUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import np.com.ngopal.control.AutoFillTextBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentController extends AbsController implements ElementSelectionListener, AttributeSelectionListener, PBCoreAttributeTreeCell.AttributeTreeCellListener, SavableTabListener {

    private static final int ROW_HEIGHT = 55;
    @FXML
    private TreeView<PBCoreElement> rootDocumentTreeView;
    @FXML
    private TreeView<PBCoreElement> requiredElementsListView;
    @FXML
    private TreeView<PBCoreElement> optionalElementsTreeView;
    @FXML
    private TreeView<PBCoreAttribute> attributesTreeView;
    @FXML
    private AutoFillTextBox<CVTerm> taElementValue;
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

    ObjectProperty<PBCoreElement> selectedPBCoreElementProperty = new SimpleObjectProperty<>();
    PBCoreElement rootElement;

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
    }

    public void onAdd(int index, PBCoreElement pbCoreElement) {
        selectedPBCoreElementProperty.setValue(pbCoreElement);

        menuOptionSelected(MenuOption.SELECT_ELEMENT, index, pbCoreElement, DocumentController.this);
    }

    public void onDuplicate(int index, PBCoreElement pbCoreElement, TreeView<PBCoreElement> treeView) {
        TreeItem<PBCoreElement> selectedItem = treeView.getTreeItem(index);
        PBCoreElement copy = pbCoreElement.copy(true);
        copy.valueProperty.addListener((observable, oldValue, newValue) -> updateXmlPreview());
        selectedItem.getParent().getValue().addSubElement(copy);
        TreeItem<PBCoreElement> itemToAdd;
        if (treeView.equals(requiredElementsListView)) {
            itemToAdd = getRequiredTreeItem(copy, true);
        } else {
            itemToAdd = getOptionalTreeItem(copy, true);
        }
        selectedItem.getParent().getChildren().add(index + 1, itemToAdd);

        selectedPBCoreElementProperty.setValue(copy);
        updateXmlPreview();
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
        attributes.stream().map((attribute) -> {
            pbCoreAttributeTreeItem.getChildren().add(new TreeItem<>(attribute));
            return attribute;
        }).forEachOrdered((attribute) -> {
            attribute.valueProperty.addListener((observable, oldValue, newValue) -> {
                buttonSave.setVisible(buttonSave.isVisible() || !Objects.equals(oldValue, newValue));
                updateXmlPreview();
            });
        });
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
        subElements.stream().filter((pbCoreElement) -> (pbCoreElement.isRequired() || !root)).forEachOrdered((pbCoreElement) -> {
            pbCoreElementTreeItem.getChildren().add(Math.min(pbCoreElement.getSequence(), pbCoreElementTreeItem.getChildren().isEmpty() ? 0 : pbCoreElementTreeItem.getChildren().size() - 1), getRequiredTreeItem(pbCoreElement, false));
        });
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
        subElements.stream().filter((pbCoreElement) -> (hasNonRootParent || !pbCoreElement.isRequired())).forEachOrdered((pbCoreElement) -> {
            pbCoreElementTreeItem.getChildren().add(Math.min(pbCoreElement.getSequence(), pbCoreElementTreeItem.getChildren().isEmpty() ? 0 : pbCoreElementTreeItem.getChildren().size() - 1), getOptionalTreeItem(pbCoreElement, true));
        });
        return pbCoreElementTreeItem;
    }

    private void initListSelectionListeners() {
        rootListener = (observable, oldValue, newValue) -> {
            optionalElementsTreeView.getSelectionModel().selectedItemProperty().removeListener(optionalListener);
            requiredElementsListView.getSelectionModel().selectedItemProperty().removeListener(requiredListener);
            selectedPBCoreElementProperty.setValue(newValue == null ? null : newValue.getValue());
            taElementValue.setDisable(selectedPBCoreElementProperty.getValue() != null && !selectedPBCoreElementProperty.getValue().getSubElements().isEmpty());
            setTaElementValueText(selectedPBCoreElementProperty.getValue());
            optionalElementsTreeView.getSelectionModel().clearSelection();
            optionalElementsTreeView.getSelectionModel().selectedItemProperty().addListener(optionalListener);
            requiredElementsListView.getSelectionModel().clearSelection();
            requiredElementsListView.getSelectionModel().selectedItemProperty().addListener(requiredListener);
        };
        requiredListener = (observable, oldValue, newValue) -> {
            optionalElementsTreeView.getSelectionModel().selectedItemProperty().removeListener(optionalListener);
            rootDocumentTreeView.getSelectionModel().selectedItemProperty().removeListener(rootListener);
            selectedPBCoreElementProperty.setValue(newValue == null ? null : newValue.getValue());
            taElementValue.setDisable(selectedPBCoreElementProperty.getValue() != null && !selectedPBCoreElementProperty.getValue().getSubElements().isEmpty());
            setTaElementValueText(selectedPBCoreElementProperty.getValue());
            optionalElementsTreeView.getSelectionModel().clearSelection();
            optionalElementsTreeView.getSelectionModel().selectedItemProperty().addListener(optionalListener);
            rootDocumentTreeView.getSelectionModel().clearSelection();
            rootDocumentTreeView.getSelectionModel().selectedItemProperty().addListener(rootListener);
        };
        optionalListener = (observable, oldValue, newValue) -> {
            requiredElementsListView.getSelectionModel().selectedItemProperty().removeListener(requiredListener);
            rootDocumentTreeView.getSelectionModel().selectedItemProperty().removeListener(rootListener);
            selectedPBCoreElementProperty.setValue(newValue == null ? null : newValue.getValue());
            taElementValue.setDisable(selectedPBCoreElementProperty.getValue() != null && !selectedPBCoreElementProperty.getValue().getSubElements().isEmpty());
            setTaElementValueText(selectedPBCoreElementProperty.getValue());
            requiredElementsListView.getSelectionModel().clearSelection();
            requiredElementsListView.getSelectionModel().selectedItemProperty().addListener(requiredListener);
            rootDocumentTreeView.getSelectionModel().clearSelection();
            rootDocumentTreeView.getSelectionModel().selectedItemProperty().addListener(rootListener);
        };
    }

    private void setTaElementValueText(PBCoreElement pbCoreElement) {
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
            List<CVTerm> suggestions = registry.getControlledVocabularies().get(pbCoreElement.getName()).getTerms();
            taElementValue.getData().addAll(suggestions);
        }
        updateInvalidIcon(!pbCoreElement.isValid() && pbCoreElement.getElementType() != PBCoreElementType.ROOT_ELEMENT, pbCoreElement.isFatalError());
    }

    private void updateInvalidIcon(boolean isInvalid, boolean fatalError) {
        invalidValueIcon.setVisible(isInvalid);
        if (invalidValueIcon.isVisible()) {
            invalidValueIcon.getStyleClass().remove("panicIcon");
            invalidValueIcon.getStyleClass().remove("warningIcon");
            if (fatalError) {
                invalidValueIcon.getStyleClass().add("panicIcon");
                invalidValueIcon.setIconCode(MaterialDesign.MDI_CHECK_CIRCLE);
            } else {
                invalidValueIcon.getStyleClass().add("warningIcon");
                invalidValueIcon.setIconCode(MaterialDesign.MDI_ALERT);
            }
            Tooltip tooltip = new Tooltip(getErrorMessage());
            invalidValueIcon.setOnMouseEntered(event -> {
                Point2D p = invalidValueIcon.localToScreen(invalidValueIcon.getLayoutBounds().getMaxX(), invalidValueIcon.getLayoutBounds().getMaxY()); //I position the tooltip at bottom right of the node (see below for explanation)
                tooltip.show(invalidValueIcon, p.getX(), p.getY() + 2);
            });
            invalidValueIcon.setOnMouseExited(event -> tooltip.hide());
        } else {
            invalidValueIcon.setOnMouseEntered(null);
            invalidValueIcon.setOnMouseEntered(null);
        }
    }

    public String getErrorMessage() {
        if (selectedPBCoreElementProperty.getValue().isHasChildElements() && !selectedPBCoreElementProperty.getValue().isValid()) {
            if (selectedPBCoreElementProperty.getValue().isFatalError()) {
                invalidDocumentIcon.getStyleClass().add("panicIcon");
                invalidDocumentIcon.setIconCode(MaterialDesign.MDI_CHECK_CIRCLE);
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
                if (selectedPBCoreElementProperty.getValue().getValue() == null || selectedPBCoreElementProperty.getValue().getValue().trim().isEmpty()) {
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
    public void onElementSelected(int index, PBCoreElement element) {
        if (element == null) {
            return;
        }
        ElementType elementType;
        TreeItem<PBCoreElement> selectedItem;
        TreeItem<PBCoreElement> pbCoreElementTreeItem;
        if (selectedPBCoreElementProperty.getValue() == null || selectedPBCoreElementProperty.getValue().getId() == rootElement.getId()) {
            if (element.isRequired()) {
                selectedItem = requiredElementsListView.getRoot();
                pbCoreElementTreeItem = getRequiredTreeItem(element, true);
                elementType = ElementType.REQUIRED;
            } else {
                selectedItem = optionalElementsTreeView.getRoot();
                pbCoreElementTreeItem = getOptionalTreeItem(element, true);
                elementType = ElementType.OPTIONAL;
            }
        } else if (selectedPBCoreElementProperty.getValue().isRequired()) {
            selectedItem = requiredElementsListView.getTreeItem(index);
            pbCoreElementTreeItem = getRequiredTreeItem(element, true);
            elementType = ElementType.REQUIRED;
        } else {
            selectedItem = optionalElementsTreeView.getTreeItem(index);
            if (selectedItem == null) {
                selectedItem = requiredElementsListView.getTreeItem(index);
                pbCoreElementTreeItem = getRequiredTreeItem(element, true);
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
        selectedPBCoreElementProperty.setValue(element);
        setTaElementValueText(element);
        updateXmlPreview();
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
        updateStatusBarLabel();
    }

    @Override
    public void onAttributeSelected(PBCoreAttribute pbCoreAttribute) {
        if (pbCoreAttribute == null) {
            return;
        }
        selectedPBCoreElementProperty.getValue().addAttribute(pbCoreAttribute.copy());
        attributesTreeView.setRoot(getAttributesTreeItem(selectedPBCoreElementProperty.getValue()));
        updateXmlPreview();
    }

    @Override
    public void onRemoveAttribute(PBCoreAttribute pbCoreAttribute) {
        selectedPBCoreElementProperty.getValue().removeAttribute(pbCoreAttribute);
        attributesTreeView.setRoot(getAttributesTreeItem(selectedPBCoreElementProperty.getValue()));
        updateXmlPreview();
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

    public void initializeDocument(String token, String currentId, File file, PBCoreElement pbCoreElement, FileChangedListener fileChangedListener) {
        this.fileChangedListener = fileChangedListener;
        this.rootElement = pbCoreElement;
        this.file = file;
        this.currentId = currentId;
        this.token = token;
        initListSelectionListeners();
        statusBarDocumentName.setText(file == null ? currentId : file.getName());
        statusBarDocumentType.setText(rootElement.getScreenName().toUpperCase());

        selectedPBCoreElementProperty.addListener((observable, oldValue, newValue) -> {
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
                        cbElementValue.setItems(FXCollections.observableArrayList(newValue.getEnumerationValues()));
                        cbElementValue.getSelectionModel().select(0);
                        break;
                    default:
                        taElementValue.setVisible(true);
                        cbElementValue.setVisible(false);
                        cbElementValue.getItems().clear();
                }
            }
        });
        addAttributeButton.setDisable(true);

        TreeItem<PBCoreElement> elementTreeItem = new TreeItem<>(rootElement);
        rootDocumentTreeView.setRoot(elementTreeItem);
        rootDocumentTreeView.setCellFactory(lv -> new PBCoreTreeCell(new DocumentElementItemController.DocumentElementInteractionListener() {
            @Override
            public void onRemove(int index, PBCoreElement pbCoreElement) {
            }

            @Override
            public void onAdd(int index, PBCoreElement pbCoreElement) {
                DocumentController.this.onAdd(index, pbCoreElement);
            }

            @Override
            public void onDuplicate(int index, PBCoreElement pbCoreElement) {

            }
        }));

        requiredElementsListView.setShowRoot(false);
        requiredElementsListView.setCellFactory(lv -> new PBCoreTreeCell(new DocumentElementItemController.DocumentElementInteractionListener() {
            @Override
            public void onRemove(int index, PBCoreElement pbCoreElement) {
                DocumentController.this.onRemove(index, pbCoreElement, requiredElementsListView);
            }

            @Override
            public void onAdd(int index, PBCoreElement pbCoreElement) {
                DocumentController.this.onAdd(index, pbCoreElement);
            }

            @Override
            public void onDuplicate(int index, PBCoreElement pbCoreElement) {
                DocumentController.this.onDuplicate(index, pbCoreElement, requiredElementsListView);
            }
        }));
        loadRequiredTreeData(rootElement);

        optionalElementsTreeView.setShowRoot(false);
        optionalElementsTreeView.setCellFactory(lv -> new PBCoreTreeCell(new DocumentElementItemController.DocumentElementInteractionListener() {
            @Override
            public void onRemove(int index, PBCoreElement pbCoreElement) {
                DocumentController.this.onRemove(index, pbCoreElement, optionalElementsTreeView);
            }

            @Override
            public void onAdd(int index, PBCoreElement pbCoreElement) {
                DocumentController.this.onAdd(index, pbCoreElement);
            }

            @Override
            public void onDuplicate(int index, PBCoreElement pbCoreElement) {
                DocumentController.this.onDuplicate(index, pbCoreElement, optionalElementsTreeView);
            }
        }));
        loadOptionalTreeData(rootElement);

        attributesTreeView.setShowRoot(false);
        attributesTreeView.setCellFactory(lv -> new PBCoreAttributeTreeCell(DocumentController.this));

        requiredElementsListView.getSelectionModel().selectedItemProperty().addListener(requiredListener);
        optionalElementsTreeView.getSelectionModel().selectedItemProperty().addListener(optionalListener);

        taElementValue.getTextbox().textProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedPBCoreElementProperty.getValue() == null) {
                return;
            }
            buttonSave.setVisible(buttonSave.isVisible() || !Objects.equals(selectedPBCoreElementProperty.getValue().getValue(), newValue));
            selectedPBCoreElementProperty.getValue().setValue(taElementValue.getText());
            Registry registry = MainApp.getInstance().getRegistry();
            if (registry.getControlledVocabularies().containsKey(selectedPBCoreElementProperty.getValue().getName())) {
                List<CVTerm> suggestions = registry.getControlledVocabularies().get(selectedPBCoreElementProperty.getValue().getName()).getTerms();
                selectedPBCoreElementProperty.getValue().setValid(suggestions.stream().filter(cvTerm -> cvTerm.getTerm().equalsIgnoreCase(taElementValue.getText())).count() > 0);
            } else {
                if (!selectedPBCoreElementProperty.getValue().isHasChildElements()) {
                    switch (selectedPBCoreElementProperty.getValue().getElementValueRestrictionType()) {
                        case PATTERN:
                            Pattern pattern = Pattern.compile(selectedPBCoreElementProperty.getValue().getPatternToFollow());
                            String s = taElementValue.getText() == null ? "" : taElementValue.getText();
                            Matcher matcher = pattern.matcher(s);
                            selectedPBCoreElementProperty.getValue().setValid(!s.trim().isEmpty() && matcher.matches());
                            break;
                        default:
                            selectedPBCoreElementProperty.getValue().setValid(selectedPBCoreElementProperty.getValue().getValue() != null && !selectedPBCoreElementProperty.getValue().getValue().trim().isEmpty());
                            break;
                    }
                }
            }
            updateInvalidIcon(!selectedPBCoreElementProperty.getValue().isValid(), selectedPBCoreElementProperty.getValue().isFatalError());
        });
        cbElementValue.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedPBCoreElementProperty.getValue() == null) {
                return;
            }
            selectedPBCoreElementProperty.getValue().setValue(newValue);
            selectedPBCoreElementProperty.getValue().setValid(true);
        });
        taElementValue.setDisable(true);
        cbElementValue.setVisible(false);
        setTaElementValueText(selectedPBCoreElementProperty.getValue());
        new AutoFillTextBoxPBCoreElementSkin(taElementValue);
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
        rootDocumentTreeView.getSelectionModel().select(0);
        updateStatusBarLabel();
        buttonSave.setVisible(file == null);
    }

    @FXML
    void saveFile(ActionEvent event) {
        saveDocument();
    }

    @Override
    public void saveDocument() {
        if (buttonSave.isVisible()) {
            saveDocument(false);
        }
    }

    public void saveDocument(boolean close) {
        if (!buttonSave.isVisible()) {
            if (fileChangedListener != null) {
                fileChangedListener.onFileChanged(currentId, file, close);
            }
        }
        if (file == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Unsaved changes");
            alert.setContentText("Your work is not currently saved. Do you want to save if before closing the tab?");

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
        }
        if (file != null) {
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
    public MenuBar createMenu() {
        final MenuBar menuBar = new MenuBar();
        return menuBar;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void onShown() {
        super.onShown();
        aceEditor.reload();
    }

    public void onShow() {
        aceEditor.reload();
    }
}
