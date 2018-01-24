package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.NewDocumentType;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import digitalbedrock.software.pbcore.listeners.MenuActionListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class MainAppController extends AbsController implements MenuActionListener {
    @FXML
    private AnchorPane splash;
    @FXML
    private TabPane tabPane;

    @Override
    public void newDocument(NewDocumentType newDocumentType) {
        PBCoreElement rootElement = PBCoreStructure.getInstance().getRootElement(newDocumentType);
        rootElement.clearOptionalSubElements();
        showTab(null, rootElement);
    }

    @Override
    public void openDocument(File file) {
        try {
            showTab(file, PBCoreStructure.getInstance().parseFile(file));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }


    private void showTab(File file, PBCoreElement pbCoreElement) {
        splash.setVisible(false);

        String title = "untitled" + String.format("%04d", tabPane.getTabs().size() + 1);

        Tab tab = new Tab(title);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tab.setOnClosed(t -> {
            if (tabPane.getTabs().isEmpty()) {
                splash.setVisible(true);
            }
        });
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document.fxml"));
            Node node = loader.load();
            DocumentController controller = loader.getController();
            controller.setMainController(mainController);
            controller.initializeDocument(file, pbCoreElement);
            tab.setContent(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }
}
