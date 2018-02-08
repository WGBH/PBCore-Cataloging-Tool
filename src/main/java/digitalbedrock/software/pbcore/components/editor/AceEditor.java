package digitalbedrock.software.pbcore.components.editor;

import digitalbedrock.software.pbcore.core.PBcoreValidator;
import digitalbedrock.software.pbcore.core.models.entity.IPBCore;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.utils.Registry;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AceEditor extends AnchorPane {

    private final static String FILE_PROTOCOL = "file://";

    private PBcoreValidator validator;

    private EditorOpenedStateListener editorOpenedStateListener;
    @FXML
    private WebView webView;
    @FXML
    private ListView<IPBCore> treeViewPreview;
    @FXML
    private Button closeButton;
    @FXML
    private GridPane topBar;
    @FXML
    private RadioButton toggleXml;
    @FXML
    private RadioButton toggleVisual;
    @FXML
    private ToggleGroup typeRadio;

    private final SimpleStringProperty text;

    private AtomicBoolean opened = new AtomicBoolean(true);
    private BooleanProperty showCloseButton = new SimpleBooleanProperty(true);

    private PBCoreElement pbCoreElement;

    public AceEditor() {
        try {
            validator = new PBcoreValidator();
        }
        catch (SAXException e) {
            e.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editor.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        toggleXml.getStyleClass().remove("radio-button");
        toggleXml.getStyleClass().add("toggle-button");
        toggleVisual.getStyleClass().remove("radio-button");
        toggleVisual.getStyleClass().add("toggle-button");

        typeRadio.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            updateContent(newValue);
        });

        final WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        text = new SimpleStringProperty("");
        text.addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
                if (webEngine.getLoadWorker().stateProperty().getValue() == Worker.State.SUCCEEDED) {
                    webEngine.executeScript("test('" + StringEscapeUtils.escapeJavaScript(arg2) + "')");
                }
            }
        });
        initialize(webView);

        closeButton.setOnAction(event -> {
            if (opened.get()) {
                double heightToHide = getHeight() - topBar.getHeight();
                double v2 = getHeight() - Math.min(heightToHide, 50);
                setTranslateY(getTranslateY() + v2);
                if (editorOpenedStateListener != null) {
                    editorOpenedStateListener.onEditorClosed();
                    opened.set(false);
                }
            } else {
                setTranslateY(0);
                if (editorOpenedStateListener != null) {
                    editorOpenedStateListener.onEditorOpened();
                    opened.set(true);
                }
            }
            updateContent(typeRadio.getSelectedToggle());
        });
        showCloseButton.addListener((observable, oldValue, newValue) -> closeButton.setVisible(newValue != null && newValue));
    }

    private void updateContent(Toggle newValue) {
        if (newValue != null && opened.get()) {
            if (newValue.equals(toggleXml)) {
                updateXmlPreview();
                webView.setVisible(true);
                treeViewPreview.setVisible(false);
            } else if (newValue.equals(toggleVisual)) {
                webView.setVisible(false);
                treeViewPreview.setVisible(true);
                updateVisualLayoutPreview();
            }
        } else {
            webView.setVisible(false);
            treeViewPreview.setVisible(false);
        }
    }

    private void initialize(WebView webView) {
        loadContent(webView);
    }

    private void loadContent(WebView webView) {
        WebEngine engine = webView.getEngine();
        engine.load(FILE_PROTOCOL + Registry.verifyAndRetrieveAceEditorHtmlResourceFile());
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                try {
                    engine.executeScript("test('" + StringEscapeUtils.escapeJavaScript(text.getValue() == null ? "" : text.getValue()) + "')");
                }
                catch (Exception e) {
                }
            }
        });
    }

    public Node getNode() {
        return webView;
    }

    public StringProperty textProperty() {
        return text;
    }

    public String getText() {
        return text.get();
    }

    public void setEditorOpenedStateListener(EditorOpenedStateListener editorOpenedStateListener) {
        this.editorOpenedStateListener = editorOpenedStateListener;
    }

    public void updatePreview(PBCoreElement value) {
        this.pbCoreElement = value;
        updateContent(typeRadio.getSelectedToggle());
    }

    private void updateVisualLayoutPreview() {
        treeViewPreview.getItems().clear();
        treeViewPreview.setCellFactory(lv -> new IPBCorePreviewItemListCell());
        List<IPBCore> flatList = new ArrayList<>();
        getFlatTree(flatList, pbCoreElement);
        treeViewPreview.setItems(FXCollections.observableArrayList(flatList));
    }

    private void updateXmlPreview() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element element = processElement(doc, pbCoreElement);
            if (element == null) {
                return;
            }

            doc.appendChild(element);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult(stringWriter);
            transformer.transform(source, result);

            textProperty().setValue(stringWriter.toString());
            try {
                validator.validate(stringWriter.toString());
            }
            catch (SAXException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
        catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void getFlatTree(List<IPBCore> listToAdd, PBCoreElement rootElement) {
        if (rootElement == null) {
            return;
        }
        listToAdd.add(rootElement);
        listToAdd.addAll(rootElement.getAttributes());
        rootElement.getOrderedSubElements().forEach((coreElement) -> {
            getFlatTree(listToAdd, coreElement);
        });
    }

    private TreeItem<PBCoreElement> getTreeItem(PBCoreElement rootElement) {
        if (rootElement == null) {
            return new TreeItem<>();
        }
        TreeItem<PBCoreElement> pbCoreElementTreeItem = new TreeItem<>(rootElement);
        pbCoreElementTreeItem.setExpanded(true);
        rootElement.getOrderedSubElements().forEach((coreElement) -> {
            pbCoreElementTreeItem.getChildren().add(getTreeItem(coreElement));
        });
        return pbCoreElementTreeItem;
    }

    private Element processElement(Document doc, PBCoreElement value) {
        if (value == null) {
            return null;
        }
        Element element = doc.createElement(value.getName());
        element.appendChild(doc.createTextNode(value.getValue() == null ? "" : value.getValue()));
        value.getAttributes().forEach((pbCoreAttribute) -> {
            element.setAttribute(pbCoreAttribute.getName(), pbCoreAttribute.getValue());
        });
        value.getOrderedSubElements().forEach((pbElement) -> {
            element.appendChild(processElement(doc, pbElement));
        });
        return element;
    }

    public void reload() {
        if (webView.getEngine().getLoadWorker().stateProperty().getValue() != Worker.State.SUCCEEDED) {
            loadContent(webView);
        }
    }

    public interface EditorOpenedStateListener {

        void onEditorClosed();

        void onEditorOpened();
    }

    public void open() {
        opened.set(false);
        closeButton.fire();
    }

    public void close() {
        opened.set(true);
        closeButton.fire();
    }

    public boolean isShowCloseButton() {
        return showCloseButton.get();
    }

    public BooleanProperty showCloseButtonProperty() {
        return showCloseButton;
    }

    public void setShowCloseButton(boolean showCloseButton) {
        this.showCloseButton.set(showCloseButton);
    }
}
