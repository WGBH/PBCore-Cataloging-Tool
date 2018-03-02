package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElementType;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class DocumentElementItemController {

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

    private int index;

    private ChangeListener<String> tChangeListener;
    private ChangeListener<Boolean> tChangeListenerB;
    private ChangeListener<Boolean> tChangeListenerV;
    private ChangeListener<Boolean> tChangeListenerHasMultiple;

    public void setDocumentElementInteractionListener(boolean showErrors, boolean allowRemovalOfAllElements, int i, PBCoreElement pbCoreElement, DocumentElementInteractionListener documentElementInteractionListener) {
        this.index = i;
        if (tChangeListener != null) {
            pbCoreElement.valueProperty.removeListener(tChangeListener);
        }
        if (tChangeListenerB != null) {
            pbCoreElement.validAttributesProperty.removeListener(tChangeListenerB);
        }
        if (tChangeListenerV != null) {
            pbCoreElement.validProperty.removeListener(tChangeListenerV);
        }
        if (tChangeListenerHasMultiple != null) {
            pbCoreElement.hasMultipleProperty.removeListener(tChangeListenerHasMultiple);
        }
        removeButton.setOnAction(event -> documentElementInteractionListener.onRemove(index, pbCoreElement));
        addButton.setOnAction(event -> documentElementInteractionListener.onAdd(index, pbCoreElement));
        copyButton.setOnAction(event -> documentElementInteractionListener.onDuplicate(index, pbCoreElement));
        removeButton.setVisible(allowRemovalOfAllElements || !pbCoreElement.isRequired() || pbCoreElement.getHasMultiple());
        addButton.setVisible(pbCoreElement.getElementType() == PBCoreElementType.ROOT_ELEMENT || pbCoreElement.isSupportsChildElements());
        copyButton.setVisible(pbCoreElement.getElementType() != PBCoreElementType.ROOT_ELEMENT && pbCoreElement.isRepeatable());

        tChangeListenerHasMultiple = (observable, oldValue, newValue) -> removeButton.setVisible(allowRemovalOfAllElements || !pbCoreElement.isRequired() || pbCoreElement.getHasMultiple());
        tChangeListener = (observable, oldValue, newValue) -> {
            valueMissingIcon.setVisible(showErrors && (!pbCoreElement.isValid() || !pbCoreElement.isValidAttributes()));
            updateIconColor(pbCoreElement);
        };
        tChangeListenerB = (observable, oldValue, newValue) -> {
            valueMissingIcon.setVisible(showErrors && (!pbCoreElement.isValid() || !newValue));
            updateIconColor(pbCoreElement);
        };
        tChangeListenerV = (observable, oldValue, newValue) -> {
            valueMissingIcon.setVisible(showErrors && (!newValue || !pbCoreElement.isValidAttributes()));
            updateIconColor(pbCoreElement);
        };

        pbCoreElement.hasMultipleProperty.addListener(tChangeListenerHasMultiple);
        pbCoreElement.validAttributesProperty.addListener(tChangeListenerB);
        pbCoreElement.valueProperty.addListener(tChangeListener);
        pbCoreElement.validProperty.addListener(tChangeListenerV);
        valueMissingIcon.setVisible(showErrors
                && (pbCoreElement.isFatalError() || !pbCoreElement.isValid() || !pbCoreElement.isValidAttributes())
                && pbCoreElement.getElementType() != PBCoreElementType.ROOT_ELEMENT);
        updateIconColor(pbCoreElement);
        titleLabel.setText(pbCoreElement.getScreenName());

        titleLabel.setTooltip(new Tooltip(pbCoreElement.getScreenName()));
    }

    public void updateIndex(int i) {
        this.index = i;
    }

    private void updateIconColor(PBCoreElement pbCoreElement) {
        valueMissingIcon.getStyleClass().remove("panicIcon");
        valueMissingIcon.getStyleClass().remove("warningIcon");
        if (pbCoreElement.isFatalError()) {
            valueMissingIcon.getStyleClass().add("panicIcon");
            valueMissingIcon.setIconCode(MaterialDesign.MDI_ALERT_CIRCLE);
        } else {
            valueMissingIcon.getStyleClass().add("warningIcon");
            valueMissingIcon.setIconCode(MaterialDesign.MDI_ALERT);
        }
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

    public interface DocumentElementInteractionListener {

        void onRemove(int index, PBCoreElement pbCoreElement);

        void onAdd(int index, PBCoreElement pbCoreElement);

        void onDuplicate(int index, PBCoreElement pbCoreElement);
    }
}
