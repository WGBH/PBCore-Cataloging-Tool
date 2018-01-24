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

    //Final Static variables for Window Insets
    private final static int TITLE_HEIGHT = 28;

    private final static int WINDOW_BORDER = 8;

    //This is listview for showing the matched words
    private ListView<CVTerm> listview;

    //This is Textbox where user types
    private TextField textbox;

    //This is the main Control of AutoFillTextBox
    private AutoFillTextBox<CVTerm> autofillTextbox;

    //This is the ObservableData where the matching words are saved
    private ObservableList<CVTerm> data;

    //This is the Popup where listview is embedded.
    private Popup popup;

    public Window getWindow() {
        return autofillTextbox.getScene().getWindow();
    }

    /**
     * ****************************
     * CONSTRUCTOR
     * <p>
     *
     * @param text AutoTextBox ****************************
     */
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

    /**
     * ********************************************************
     * Selects the current Selected Item from the list and the content of that
     * selected Item is set to textbox.
     * ********************************************************
     */
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

    /**
     * ****************************************************
     * This is the main event handler which handles all the event of the
     * listview and textbox
     * <p>
     *
     * @param evt ****************************************************
     */
    @Override
    public void handle(Event evt) {

        /**
         * ******************************
         * EVENT HANDLING FOR 'TextBox' ******************************
         */
        if (evt.getEventType() == KeyEvent.KEY_PRESSED) {
            /* --------------------------------
             * - KeyEvent Handling for Textbox -
             * -------------------------------- */
//            KeyEvent t = (KeyEvent) evt;
//            if (t.getSource() == textbox) {
//                //WHEN USER PRESS DOWN ARROW KEY FOCUS TRANSFER TO LISTVIEW
//                if (t.getCode() == KeyCode.DOWN) {
//                    if (popup.isShowing()) {
//                        listview.requestFocus();
//                    }
//                }
//
//            }
        } /**
         * ******************************
         * EVENT HANDLING FOR 'LISTVIEW' ******************************
         */
        else if (evt.getEventType() == KeyEvent.KEY_RELEASED) {
            /* ---------------------------------
             * - KeyEvent Handling for ListView -
             * ---------------------------------- */
            KeyEvent t = (KeyEvent) evt;
            if (t.getSource() == listview) {
                if (t.getCode() == KeyCode.ENTER) {
                    selectList();
                } else if (t.getCode() == KeyCode.UP) {
                    if (listview.getSelectionModel().getSelectedIndex() == 0) {
                        textbox.requestFocus();
                    }
                }/* else if(){
                 *
                 * } */

            }
        } else if (evt.getEventType() == MouseEvent.MOUSE_RELEASED) {
            /* -----------------------------------
             * - MouseEvent Handling for Listview -
             * ------------------------------------ */
            if (evt.getSource() == listview) {
                selectList();
            }
        }
    }

    /**
     * A Popup containing Listview is trigged from this function This function
     * automatically resize it's height and width according to the width of
     * textbox and item's cell height
     */
    public void showPopup() {
        listview.setPrefWidth(textbox.getWidth());
        if (listview.getItems().size() > 6) {
            listview.setPrefHeight((6 * 24));
        } else {
            listview.setPrefHeight((listview.getItems().size() * 24));
        }

        popup.show(getWindow(), getWindow().getX() + textbox.localToScene(0, 0).getX() + textbox.getScene().getX(), getWindow().getY() + textbox.localToScene(0, 0).getY() + textbox.getScene().getY() + TITLE_HEIGHT);

        listview.getSelectionModel().clearSelection();
        listview.getFocusModel().focus(-1);
    }

    /**
     * This function hides the popup containing listview
     */
    public void hidePopup() {

        popup.hide();

    }

    /**
     * *********************************************
     * When ever the the rawTextProperty is changed then this listener is
     * activated
     * <p>
     *
     * @param ov
     * @param t
     * @param t1 **********************************************
     */
    @Override
    public void changed(ObservableValue<? extends String> ov, String t, String t1) {
        if (ov.getValue() != null && ov.getValue().length() > 0) {
            String txtdata = (textbox.getText()).trim();
            //Limit of data cell to be shown in ListView
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
                if(list.isEmpty()){
                    hidePopup();
                }else if (listview.getItems().containsAll(list) && listview.getItems().size() == list.size() && listview.getItems() != null) {
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
            } catch (IOException exc) {
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
