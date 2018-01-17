package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.listeners.MenuActionListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainAppController extends AbsController implements MenuActionListener {
    @FXML
    private AnchorPane splash;
    @FXML
    private TabPane tabPane;

    @Override
    public void newDescriptionDocument() {
        splash.setVisible(false);
        Tab tab = new Tab("New description document");
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tab.setOnClosed(t -> {
            if (tabPane.getTabs().isEmpty()) {
                splash.setVisible(true);
            }
        });
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document.fxml"));
            Node node = loader.load();
            ((DocumentController) loader.getController()).setMainController(mainController);
            tab.setContent(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tabPane.getTabs().add(tab);
    }
}
