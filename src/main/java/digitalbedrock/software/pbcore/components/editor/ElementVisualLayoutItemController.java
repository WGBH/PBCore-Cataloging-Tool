package digitalbedrock.software.pbcore.components.editor;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

public class ElementVisualLayoutItemController {

    @FXML
    private Text previewElementLabel;
    @FXML
    private Text previewElementValue;

    public void bind(PBCoreElement element) {
        if (element == null) {
            return;
        }
        previewElementLabel.setText(element.getScreenName().toUpperCase() + ": ");
        if (!element.isAnyElement()) {
            String value = element.getValue() == null ? "" : element.getValue();
            if (element.getName().equalsIgnoreCase("pbcoreDescription") && value.length() > 100) {
                value = value.substring(0, 100) + "...";
            }
            previewElementValue.setText(value);
        } else {
            StringBuilder text = new StringBuilder();
            text.append("\n");
            element.getAnyValues().stream().map((pbCoreElementAnyValue) -> pbCoreElementAnyValue == null || pbCoreElementAnyValue.getValue() == null || pbCoreElementAnyValue.getValue().trim().isEmpty() ? "" : pbCoreElementAnyValue.getValue()).map((valueStr) -> {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;
                try {
                    StringWriter stringWriter = new StringWriter();
                    builder = factory.newDocumentBuilder();
                    InputSource inputSource = new InputSource(new StringReader(valueStr));
                    inputSource.setEncoding("UTF-8");
                    Document document = builder.parse(inputSource);
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                    transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
                    text.append(stringWriter.toString());
                    stringWriter.close();
                } catch (Exception e) {
                    text.append(valueStr);
                }
                return valueStr;
            }).forEachOrdered((_item) -> {
                text.append("\n");
            });
            previewElementValue.setText(text.toString());
            previewElementValue.getStyleClass().add("previewElementXMLValue");
        }
    }
}
