package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.models.CVBase;
import digitalbedrock.software.pbcore.core.models.CVTerm;
import digitalbedrock.software.pbcore.listeners.CVSelectionListener;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CVSelectorController extends AbsController {

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnAdd;

    @FXML
    private TreeView<CVTerm> treeCVs;

    private boolean attr;
    private String key;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeCVs.setShowRoot(false);
        treeCVs.setCellFactory(lv -> new PBCoreTreeCell());
        ChangeListener<TreeItem<CVTerm>> listener = (observable, oldValue, newValue) -> {
            btnAdd.setDisable(newValue == null);
        };
        treeCVs.getSelectionModel().selectedItemProperty().addListener(listener);
    }

    public void setCVSelectionListener(CVSelectionListener cvSelectionListener) {
        btnCancel.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            cvSelectionListener.onCVSelected(key, null, attr);
        });
        btnAdd.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> onAdd(cvSelectionListener));
    }

    private void onAdd(CVSelectionListener cvSelectionListener) {
        TreeItem<CVTerm> selectedItem = treeCVs.getSelectionModel().getSelectedItem();
        cvSelectionListener.onCVSelected(key, selectedItem.getValue(), attr);
    }

    private TreeItem<CVTerm> getTreeItem(String key) {
        TreeItem<CVTerm> pbCoreElementTreeItem = new TreeItem<>();
        MainApp.getInstance().getRegistry().getControlledVocabularies().get(key).getTerms().forEach(cvTerm -> pbCoreElementTreeItem.getChildren().add(new TreeItem<>(cvTerm)));
        HashMap<String, CVBase> subs = MainApp.getInstance().getRegistry().getControlledVocabularies().get(key).getSubs();
        if (subs != null) {
            for (Map.Entry<String, CVBase> stringCVBaseEntry : subs.entrySet()) {
                stringCVBaseEntry.getValue().getTerms().forEach(cvTerm -> pbCoreElementTreeItem.getChildren().add(new TreeItem<>(cvTerm)));
            }
        }
        return pbCoreElementTreeItem;
    }

    public void setKey(boolean attr, String key) {
        this.attr = attr;
        this.key = key;
        treeCVs.setRoot(getTreeItem(key));
        treeCVs.getSelectionModel().select(0);
    }

    @Override
    public MenuBar createMenu() {
        return null;
    }

    private class PBCoreTreeCell extends TreeCell<CVTerm> {

        @Override
        protected void updateItem(CVTerm item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setText(item.getTerm());
                setTooltip(new Tooltip(item.getTerm()));
            } else {
                setText(null);
                setTooltip(null);
            }
        }
    }
}
