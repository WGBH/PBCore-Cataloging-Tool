package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.components.PBCoreAnyValueListCell;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElementAnyValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class DocumentElementAnyValueItemController {

    @FXML
    private Label taValue;
    @FXML
    private Button removeButton;

    public DocumentElementAnyValueItemController() {

    }

    public void bind(PBCoreElementAnyValue pbCoreElementAnyValue, PBCoreAnyValueListCell.AnyValueListCellListener anyValueListCellListener) {
        removeButton.setOnAction(event -> {
            if (anyValueListCellListener != null) {
                anyValueListCellListener.onRemove(pbCoreElementAnyValue);
            }
        });

        String valueStr = pbCoreElementAnyValue.getValue() == null || pbCoreElementAnyValue.getValue().trim().isEmpty() ? "" : pbCoreElementAnyValue.getValue();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(valueStr));
            inputSource.setEncoding("UTF-8");
            Document document = builder.parse(inputSource);
            taValue.setText(document.getDocumentElement().getTagName());
        } catch (Exception e) {
            taValue.setText(valueStr.length() < 20 ? valueStr : valueStr.substring(0, 20));
        }
    }
}
