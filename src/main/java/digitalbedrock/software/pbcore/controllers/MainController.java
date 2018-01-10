package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.utils.Registry;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController {

    private final Registry registry = new Registry();
    private boolean isModalUp = false;
    private final Stage stage;

    public MainController(Stage stage) {
        this.stage = stage;
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
        final MenuItem newi = new MenuItem("New Instantiation Document");
        newi.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.META_DOWN));
        final MenuItem newc = new MenuItem("New Collection");
        newc.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHIFT_DOWN, KeyCombination.META_DOWN));
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
        help.setDisable(true);

        menuBar.getMenus().addAll(file, search, settings, help);
        if (registry.isMac()) {
            menuBar.setUseSystemMenuBar(true);
        }
        return menuBar;
    }

    private void quit(ActionEvent event) {
        Platform.exit();
    }

    private void showSettings(int tab) {
        if (isModalUp) {
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
            isModalUp = true;
            settingsWindow.setOnCloseRequest(e -> {
                isModalUp = false;
            });

            settingsWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSearch(int searchIdx) {
        if (isModalUp) {
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
            isModalUp = true;
            searchWindow.setOnCloseRequest(e -> {
                isModalUp = false;
            });
            searchWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Registry getRegistry() {
        return registry;
    }
}
