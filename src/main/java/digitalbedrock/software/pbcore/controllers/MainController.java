package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.NewDocumentType;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.listeners.ElementSelectionListener;
import digitalbedrock.software.pbcore.listeners.MenuActionListener;
import digitalbedrock.software.pbcore.utils.Registry;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {


    private final Registry registry = new Registry();
    private final AtomicBoolean isModalUp = new AtomicBoolean(false);
    private final Stage stage;
    private final MenuActionListener menuActionListener;

    public MainController(MenuActionListener menuActionListener, Stage stage) {
        this.stage = stage;
        this.menuActionListener = menuActionListener;
    }


    public void initialize(WindowEvent e) {
        registry.loadSavedSettings();
   
        if (registry.getSettings().getDirectories().isEmpty()) {
            showSettings(1);
        }
    }

    public MenuBar createMenu() {
        final MenuBar menuBar = new MenuBar();

        // FILE
        final Menu file = new Menu("File");
        final MenuItem open = new MenuItem("Open...");
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.META_DOWN));

        final MenuItem newd = new MenuItem("New Descripion Document");
        newd.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.META_DOWN));
        newd.setOnAction(e -> showNewDescriptionDocument(NewDocumentType.DESCRIPTION_DOCUMENT));

        final MenuItem newi = new MenuItem("New Instantiation Document");
        newi.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.META_DOWN));
        newi.setOnAction(e -> showNewDescriptionDocument(NewDocumentType.INSTANTIATION_DOCUMENT));

        final MenuItem newc = new MenuItem("New Collection");
        newc.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHIFT_DOWN, KeyCombination.META_DOWN));
        newc.setOnAction(e -> showNewDescriptionDocument(NewDocumentType.COLLECTION));

        final MenuItem batch = new MenuItem("Batch edit open documents");

        final MenuItem export = new MenuItem("Export open files to ZIP");
        final MenuItem save = new MenuItem("Save");
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.META_DOWN));
        final MenuItem saveas = new MenuItem("Save as...");
        saveas.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN, KeyCombination.META_DOWN));

        final MenuItem quit = new MenuItem("Quit");
        quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.META_DOWN));
        quit.setOnAction(e -> this.quit(e));
        file.getItems().addAll(open, new SeparatorMenuItem(), newd, newi, newc, new SeparatorMenuItem(), batch, export, new SeparatorMenuItem(), save, saveas, new SeparatorMenuItem(), quit);

        // SEARCH
        final Menu search = new Menu("Search");
        final MenuItem newSearch = new MenuItem("New Search");
        newSearch.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.META_DOWN));
        newSearch.setOnAction(e -> this.showSearch(0));
        search.getItems().addAll(newSearch, new SeparatorMenuItem(), new MenuItem("Recent Searches"));

        // SETTINGS
        final Menu settings = new Menu("Settings");
        final MenuItem cv = new MenuItem("Controlled Vocabularies");
        final MenuItem folders = new MenuItem("Directory Crawling");
        cv.setOnAction(e -> this.showSettings(0));
        folders.setOnAction(e -> this.showSettings(1));
        settings.getItems().addAll(cv, folders);

        // HELP
        final Menu help = new Menu("Help");
        help.setDisable(true);  //temp

        menuBar.getMenus().addAll(file, search, settings, help);
        if (registry.isMac()) {
            menuBar.setUseSystemMenuBar(true);
        }
        return menuBar;
    }

    private void quit(ActionEvent event) {
        //todo: close all modals
        // todo: check if anything needs saving
        // todo: stop aany running thread
        Platform.exit();
    }

    private void showSettings(int tab) {
        if (isModalUp.get()) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
            Parent tabs = loader.load();
            ((AbsController) loader.getController()).setMainController(this);
            ((TabPane) tabs.lookup("#tabs")).getSelectionModel().select(tab);
            Scene settingsScene = new Scene(tabs);
            Stage settingsWindow = new Stage();
            settingsWindow.initOwner(stage);
            settingsWindow.initModality(Modality.APPLICATION_MODAL);
            settingsWindow.setTitle("Settings");
            settingsWindow.setScene(settingsScene);
            isModalUp.set(true);
            settingsWindow.setOnCloseRequest(e -> {
                isModalUp.set(false);
            });

            settingsWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSearch(int searchIdx) {
        if (isModalUp.get()) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search.fxml"));
            Parent parent = loader.load();
            ((AbsController) loader.getController()).setMainController(this);
            Scene searchScene = new Scene(parent);
            Stage searchWindow = new Stage();
            searchWindow.initOwner(stage);
            searchWindow.initModality(Modality.APPLICATION_MODAL);
            searchWindow.setTitle("Search");
            searchWindow.setScene(searchScene);
            isModalUp.set(true);
            searchWindow.setOnCloseRequest(e -> {
                isModalUp.set(false);
            });
            searchWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showNewDescriptionDocument(NewDocumentType newDocumentType) {
        if (isModalUp.get()) {
            return;
        }
        menuActionListener.newDescriptionDocument();
   
    }

    public void showSelectElement(PBCoreElement pbCoreElement, ElementSelectionListener elementSelectionListener) {
        if (isModalUp.get()) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/element_selector.fxml"));
            Parent parent = loader.load();
            ElementSelectorController controller = loader.getController();
            controller.setPbCoreElement(pbCoreElement);
            controller.setMainController(this);
            Scene searchScene = new Scene(parent);
            Stage searchWindow = new Stage();
            searchWindow.initOwner(stage);
            searchWindow.initModality(Modality.APPLICATION_MODAL);
            searchWindow.setTitle("Add new element");
            searchWindow.setScene(searchScene);
            isModalUp.set(true);
            searchWindow.setOnCloseRequest(e -> isModalUp.set(false));
            searchWindow.show();
            controller.setElementSelectionListener(element -> {
                if (elementSelectionListener != null) {
                    elementSelectionListener.onElementSelected(element);
                }
                isModalUp.set(false);
                searchWindow.close();
            });
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public Registry getRegistry() {
        return registry;
    }

}
