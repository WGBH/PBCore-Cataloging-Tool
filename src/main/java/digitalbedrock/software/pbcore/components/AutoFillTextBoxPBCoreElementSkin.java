package digitalbedrock.software.pbcore.components;

import digitalbedrock.software.pbcore.controllers.CVTermItemController;
import digitalbedrock.software.pbcore.core.models.CVTerm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Window;
import np.com.ngopal.control.AutoFillTextBox;

import java.io.IOException;

public class AutoFillTextBoxPBCoreElementSkin extends SkinBase<AutoFillTextBox<CVTerm>> implements ChangeListener<String>, EventHandler {

    private final static int TITLE_HEIGHT = 28;

    private final static int WINDOW_BORDER = 8;

    private ListView<CVTerm> listview;

    private TextField textbox;

    private final AutoFillTextBox<CVTerm> autofillTextbox;

    private final ObservableList<CVTerm> data;

    private final Popup popup;

    public Window getWindow() {
        return autofillTextbox.getScene() == null ? null : autofillTextbox.getScene().getWindow();
    }

    public AutoFillTextBoxPBCoreElementSkin(AutoFillTextBox<CVTerm> text) {
        super(text);

        autofillTextbox = text;

        listview = text.getListview();
        if (text.getFilterMode()) {
            listview.setItems(text.getData());
        }
        listview.itemsProperty().addListener((ov, t, t1) -> {
            if (listview.getItems().size() > 0 && listview.getItems() != null) {
                showPopup();
            } else {
                hidePopup();
            }
        });
        listview.setOnMouseReleased(this);
        listview.setOnKeyReleased(this);
        listview.setCellFactory(lv -> new CVTermListCell());

        textbox = text.getTextbox();
        textbox.setOnKeyPressed(this);
        textbox.textProperty().addListener(this);
        textbox.focusedProperty().addListener((ov, t, t1) -> textbox.end());

        popup = new Popup();
        popup.setAutoHide(true);
        popup.getContent().add(listview);

        data = text.getData();
        FXCollections.sort(data);
        getChildren().clear();
        getChildren().addAll(textbox);

    }

    public void selectList() {
        CVTerm i = listview.getSelectionModel().getSelectedItem();
        if (i != null) {
            textbox.setText(i.getTerm());
            listview.getItems().clear();
            textbox.requestFocus();
            textbox.requestLayout();
            textbox.end();
            hidePopup();
        }
    }

    @Override
    public void handle(Event evt) {

        if (evt.getEventType() == KeyEvent.KEY_PRESSED) {
        } else if (evt.getEventType() == KeyEvent.KEY_RELEASED) {
            KeyEvent t = (KeyEvent) evt;
            if (t.getSource() == listview) {
                if (t.getCode() == KeyCode.ENTER) {
                    selectList();
                } else if (t.getCode() == KeyCode.UP) {
                    if (listview.getSelectionModel().getSelectedIndex() == 0) {
                        textbox.requestFocus();
                    }
                }
            }
        } else if (evt.getEventType() == MouseEvent.MOUSE_RELEASED) {
            if (evt.getSource() == listview) {
                selectList();
            }
        }
    }

    public void showPopup() {
        listview.setPrefWidth(textbox.getWidth());
        if (listview.getItems().size() > 6) {
            listview.setPrefHeight((6 * 24));
        } else {
            listview.setPrefHeight((listview.getItems().size() * 24));
        }
        if (getWindow() != null) {

            popup.show(getWindow(), getWindow().getX() + textbox.localToScene(0, 0).getX() + textbox.getScene().getX(), getWindow().getY() + textbox.localToScene(0, 0).getY() + textbox.getScene().getY() + TITLE_HEIGHT);

            listview.getSelectionModel().clearSelection();
            listview.getFocusModel().focus(-1);
        }
    }

    public void hidePopup() {

        popup.hide();

    }

    @Override
    public void changed(ObservableValue<? extends String> ov, String t, String t1) {
        if (ov.getValue() != null && ov.getValue().length() > 0) {
            String txtdata = (textbox.getText()).trim();
            int limit = 0;
            if (txtdata.length() > 0) {
                ObservableList<CVTerm> list = FXCollections.observableArrayList();
                String compare = txtdata.toLowerCase();
                for (CVTerm dat : data) {
                    String str = dat.getTerm().toLowerCase();
                    if (str.toLowerCase().contains(compare.toLowerCase())) {
                        list.add(dat);
                        limit++;
                    }
                    if (limit == autofillTextbox.getListLimit()) {
                        break;
                    }
                }
                if (list.isEmpty()) {
                    hidePopup();
                } else if (listview.getItems().containsAll(list) && listview.getItems().size() == list.size() && listview.getItems() != null) {
                    showPopup();
                } else {
                    listview.setItems(list);
                }
            } else {
                if (autofillTextbox.getFilterMode()) {
                    listview.setItems(data);
                } else {
                    hidePopup();
                }
            }
        }
        if (ov.getValue() == null || ov.getValue().trim().length() <= 0) {
            hidePopup();
        }
    }

    private class CVTermListCell extends ListCell<CVTerm> {

        private Node graphic;
        private CVTermItemController controller;

        public CVTermListCell() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cvterm_list_item.fxml"));
                graphic = loader.load();
                controller = loader.getController();
            }
            catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }

        @Override
        public void updateItem(CVTerm item, boolean empty) {
            super.updateItem(item, empty);
            if (isEmpty()) {
                setGraphic(null);
            } else {
                setGraphic(graphic);
                controller.bind(item);
            }
        }
    }
}
