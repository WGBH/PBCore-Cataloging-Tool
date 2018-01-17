package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElementType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.kordamp.ikonli.javafx.FontIcon;

public class DocumentElementController {

    @FXML
    private Button removeButton;
    @FXML
    private Label titleLabel;
    @FXML
    private Button addButton;
    @FXML
    private Button copyButton;
    @FXML
    private FontIcon valueMissingIcon;

    public void setDocumentElementInteractionListener(int index, PBCoreElement pbCoreElement, DocumentElementInteractionListener documentElementInteractionListener) {
        removeButton.setOnAction(event -> {
            System.out.println(index);
            documentElementInteractionListener.onRemove(index, pbCoreElement);
        });
        addButton.setOnAction(event -> documentElementInteractionListener.onAdd(pbCoreElement));
        copyButton.setOnAction(event -> documentElementInteractionListener.onDuplicate(pbCoreElement));
        valueMissingIcon.setVisible(pbCoreElement.getSubElements().isEmpty() && (pbCoreElement.getValue() == null || pbCoreElement.getValue().isEmpty()));
        removeButton.setVisible(!pbCoreElement.isRequired() || pbCoreElement.getHasMultiple());
        addButton.setVisible(pbCoreElement.getElementType() == PBCoreElementType.ROOT_ELEMENT || pbCoreElement.isHasChildElements());
        copyButton.setVisible(pbCoreElement.getElementType() != PBCoreElementType.ROOT_ELEMENT && !pbCoreElement.isRepeatable());

        pbCoreElement.hasMultipleProperty.addListener((observable, oldValue, newValue) -> removeButton.setVisible(!pbCoreElement.isRequired() || pbCoreElement.getHasMultiple()));
        pbCoreElement.valueProperty.addListener((observable, oldValue, newValue) -> valueMissingIcon.setVisible(pbCoreElement.getSubElements().isEmpty() && (pbCoreElement.getValue() == null || pbCoreElement.getValue().isEmpty())));
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getCopyButton() {
        return copyButton;
    }

    interface DocumentElementInteractionListener {

        void onRemove(int index, PBCoreElement pbCoreElement);

        void onAdd(PBCoreElement pbCoreElement);

        void onDuplicate(PBCoreElement pbCoreElement);
    }
}