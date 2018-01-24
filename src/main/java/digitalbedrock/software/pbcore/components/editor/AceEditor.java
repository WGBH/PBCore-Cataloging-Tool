package digitalbedrock.software.pbcore.components.editor;

import digitalbedrock.software.pbcore.utils.Registry;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AceEditor extends AnchorPane {

    private final static String FILE_PROTOCOL = "file://";
    private EditorOpenedStateListener editorOpenedStateListener;
    @FXML
    private WebView webView;
    @FXML
    private Button closeButton;
    @FXML
    private GridPane topBar;

    private SimpleStringProperty text;
    private String initialText;

    private AtomicBoolean opened = new AtomicBoolean(true);

    public AceEditor() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/editor.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        final WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        text = new SimpleStringProperty("");
        text.addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
                if (webEngine.getLoadWorker().stateProperty().getValue() == Worker.State.SUCCEEDED) {
                    webEngine.executeScript("test('" + StringEscapeUtils.escapeJavaScript(arg2
                            /*
                            .replace("<", "&lt")
                            .replace(">", "&gt")*/) + "')");
                }
            }
        });
        initialize(webView);

        closeButton.setOnAction(event -> {
            if (!opened.get()) {
                webView.setVisible(false);
                double heightToHide = getHeight() - topBar.getHeight();
                double v2 = getHeight() - Math.min(heightToHide, 50);
                setTranslateY(getTranslateY() + v2);
                if (editorOpenedStateListener != null) {
                    editorOpenedStateListener.onEditorClosed();
                }
            } else {
                webView.setVisible(true);
                setTranslateY(0);
                if (editorOpenedStateListener != null) {
                    editorOpenedStateListener.onEditorOpened();
                }
            }
            opened.set(!opened.get());
        });
    }

    private void initialize(WebView webView) {
        loadContent(webView);
    }

    private void loadContent(WebView webView) {
        WebEngine engine = webView.getEngine();
        engine.load(FILE_PROTOCOL + Registry.verifyAndRetrieveAceEditorHtmlResourceFile());
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                engine.executeScript("test('" + StringEscapeUtils.escapeJavaScript(text.getValue() == null ? "" : text.getValue()
                        /*.replaceAll("<", "&lt")
                        .replaceAll(">", "&gt")*/) + "')");
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

    public String getInitialText() {
        return initialText;
    }

    public void setInitialText(String initialText) {
        this.initialText = initialText;
        text.setValue(initialText);
    }

    public void setEditorOpenedStateListener(EditorOpenedStateListener editorOpenedStateListener) {
        this.editorOpenedStateListener = editorOpenedStateListener;
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
}
