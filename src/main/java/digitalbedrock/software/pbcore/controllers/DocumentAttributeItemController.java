package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.components.AutoFillTextBoxPBCoreElementSkin;
import digitalbedrock.software.pbcore.components.PBCoreAttributeTreeCell;
import digitalbedrock.software.pbcore.core.models.CVTerm;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreAttribute;
import digitalbedrock.software.pbcore.utils.Registry;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import np.com.ngopal.control.AutoFillTextBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class DocumentAttributeItemController extends AbsController {

    @FXML
    private AutoFillTextBox<CVTerm> autoCompleteTF;
    @FXML
    private Label attributeNameLbl;
    @FXML
    private Button removeButton;
    @FXML
    private FontIcon valueMissingIcon;
    private ChangeListener<String> tChangeListener;

    public DocumentAttributeItemController() {

    }

    public void bind(PBCoreAttribute pbCoreAttribute, PBCoreAttributeTreeCell.AttributeTreeCellListener attributeTreeCellListener) {
        if (tChangeListener != null) {
            autoCompleteTF.getTextbox().textProperty().removeListener(tChangeListener);
        }
        tChangeListener = (observable, oldValue, newValue) -> {
            pbCoreAttribute.setValue(newValue);
        };
        Registry registry = mainController.getRegistry();
        if (registry.getControlledVocabularies().containsKey(pbCoreAttribute.getName())) {
            registry.getControlledVocabularies().get(pbCoreAttribute.getName()).getTerms().forEach(autoCompleteTF::addData);
        }
        autoCompleteTF.getTextbox().setText(pbCoreAttribute.getValue());
        pbCoreAttribute.valueProperty.addListener((observable, oldValue, newValue) -> valueMissingIcon.setVisible(newValue == null || newValue.trim().isEmpty()));
        attributeNameLbl.setText(pbCoreAttribute.getScreenName());
        removeButton.setVisible(!pbCoreAttribute.isRequired());
        removeButton.setOnAction(event -> {
            if (attributeTreeCellListener != null) {
                attributeTreeCellListener.onRemoveAttribute(pbCoreAttribute);
            }
        });
        new AutoFillTextBoxPBCoreElementSkin(autoCompleteTF);
        autoCompleteTF.setDisable(pbCoreAttribute.isReadOnly());
        autoCompleteTF.getTextbox().textProperty().addListener(tChangeListener);

        valueMissingIcon.setVisible(pbCoreAttribute.getValue() == null || pbCoreAttribute.getValue().trim().isEmpty());

        if (valueMissingIcon.isVisible()) {
            Tooltip tooltip = new Tooltip("Value Missing");
            valueMissingIcon.setOnMouseEntered(event -> {
                Point2D p = valueMissingIcon.localToScreen(valueMissingIcon.getLayoutBounds().getMaxX(), valueMissingIcon.getLayoutBounds().getMaxY()); //I position the tooltip at bottom right of the node (see below for explanation)
                tooltip.show(valueMissingIcon, p.getX(), p.getY() + 2);
            });
            valueMissingIcon.setOnMouseExited(event -> tooltip.hide());
        } else {
            valueMissingIcon.setOnMouseEntered(null);
            valueMissingIcon.setOnMouseEntered(null);
        }
    }
}