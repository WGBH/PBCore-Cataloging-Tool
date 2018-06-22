package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreElementAnyValue;
import digitalbedrock.software.pbcore.listeners.AddElementAnyValueListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URL;
import java.util.ResourceBundle;

public class AddElementAnyValueController extends AbsController {

    @FXML
    private TextArea taValue;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnAdd;
    @FXML
    private Label lblAttributeAlreadyAdded;

    private PBCoreElementAnyValue pbCoreElementAnyValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pbCoreElementAnyValue = new PBCoreElementAnyValue();
        lblAttributeAlreadyAdded.setVisible(false);
    }

    public void setAttributeSelectionListener(AddElementAnyValueListener addAnyValueListener) {
        btnCancel.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> addAnyValueListener.onValueAdded(null));
        btnAdd.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            String text = taValue.getText();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                builder.parse(new InputSource(new StringReader(text)));
                pbCoreElementAnyValue.setValue(text);
                lblAttributeAlreadyAdded.setVisible(false);
                addAnyValueListener.onValueAdded(pbCoreElementAnyValue);
            } catch (Exception e) {
                lblAttributeAlreadyAdded.setVisible(true);
            }
        });
        Platform.runLater(() -> btnAdd.requestFocus());
    }

    @Override
    public MenuBar createMenu() {
        return new MenuBar();
    }

}
