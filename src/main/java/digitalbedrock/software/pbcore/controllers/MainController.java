package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.models.NewDocumentType;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import digitalbedrock.software.pbcore.listeners.*;
import digitalbedrock.software.pbcore.lucene.HitDocument;
import digitalbedrock.software.pbcore.lucene.LuceneEngineSearchFilter;
import digitalbedrock.software.pbcore.utils.Registry;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MainController extends AbsController implements FileChangedListener, SearchResultListener, SavedSearchedUpdated {

    private static final String UNTITLED = "untitled";
    @FXML
    private AnchorPane splash;
    @FXML
    private AnchorPane spinnerLayer;
    @FXML
    private TabPane tabPane;

    private final AtomicInteger untitledTabsCount = new AtomicInteger(0);

    private SavableTabListener currentSavableTabListener;

    private Menu search;

    @Override
    public void menuOptionSelected(MenuOption menuOption, Object... objects) {
        switch (menuOption) {
            case OPEN_FILE:
                Object object = objects[0];
                openDocument((File) object);
                break;
            case NEW_DESCRIPTION_DOCUMENT:
                newDocument(NewDocumentType.DESCRIPTION_DOCUMENT);
                break;
            case NEW_INSTANTIATION_DOCUMENT:
                newDocument(NewDocumentType.INSTANTIATION_DOCUMENT);
                break;
            case NEW_COLLECTION:
                newDocument(NewDocumentType.COLLECTION);
                break;
            case SAVE:
                saveDocument();
                break;
            case SAVE_AS:
                saveDocumentAs();
                break;
            default:
                super.menuOptionSelected(menuOption, objects);
                break;
        }
    }

    public void newDocument(NewDocumentType newDocumentType) {
        PBCoreElement rootElement = PBCoreStructure.getInstance().getRootElement(newDocumentType);
        rootElement.clearOptionalSubElements();
        showTab(null, null, rootElement);
    }

    public void openDocument(File file) {
        try {
            PBCoreElement pbCoreElement = PBCoreStructure.getInstance().parseFile(file);
            showTab(null, file, pbCoreElement.copy());
        } catch (JAXBException | IllegalAccessException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid File");
            alert.setHeaderText("The selected file is not a valid PBCore file");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void saveDocument() {
        if (currentSavableTabListener != null) {
            currentSavableTabListener.saveDocument();
        }
    }

    public void saveDocumentAs() {
        if (currentSavableTabListener != null) {
            currentSavableTabListener.saveDocumentAs();
        }
    }

    private void showTab(String token, File file, PBCoreElement pbCoreElement) {
        if (file != null) {
            File finalFile = file;
            Tab tab1 = tabPane.getTabs().stream().filter(tab -> Objects.equals(tab.getId(), finalFile.getAbsolutePath())).findFirst().orElse(null);
            if (tab1 != null) {
                tabPane.getSelectionModel().select(tab1);
                return;
            }
        }
        String title;
        String id;
        if (file != null) {
            if (file.exists()) {
                title = file.getName();
                id = file.getAbsolutePath();
            } else {
                title = file.getName();
                id = file.getName();
                untitledTabsCount.addAndGet(1);
                file = null;
            }
        } else {
            title = UNTITLED + String.format("%04d", untitledTabsCount.addAndGet(1));
            id = title;
        }
        Tab tab = new Tab(title);
        tab.setId(id);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document.fxml"));
            Node node = loader.load();
            DocumentController controller = loader.getController();
            String s = token != null ? token : UUID.randomUUID().toString() + System.currentTimeMillis();
            controller.initializeDocument(s, id, file, pbCoreElement, this);
            tab.setOnClosed(t -> {
                if (tabPane.getTabs().isEmpty()) {
                    splash.setVisible(true);
                }
                if (tab.getText().startsWith(UNTITLED)) {
                    untitledTabsCount.set(untitledTabsCount.get() - 1);
                }
                MainApp.getInstance().getRegistry().removePBCoreElement(s);
            });
            tab.setOnCloseRequest(event -> {
                controller.saveDocument(true);
                event.consume();
            });
            tab.setContent(node);
            tab.selectedProperty().addListener((observable, oldValue, newValue) -> {
                currentSavableTabListener = controller;
                controller.onShow();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        splash.setVisible(false);
    }

    @Override
    public void onFileChanged(String currentId, File file, boolean close) {
        if (!Objects.equals(currentId, file.getAbsolutePath())) {
            Tab tabToRemove = tabPane.getTabs().stream().filter(tab -> Objects.equals(tab.getId(), file.getAbsolutePath())).findFirst().orElse(null);
            if (tabToRemove != null) {
                tabPane.getTabs().remove(tabToRemove);
                if (tabPane.getTabs().isEmpty()) {
                    splash.setVisible(true);
                }
            }
        }
        Tab tab1 = tabPane.getTabs().stream().filter(tab -> Objects.equals(tab.getId(), currentId)).findFirst().orElse(null);
        if (tab1 != null) {
            tab1.setId(file.getAbsolutePath());
            tab1.setText(file.getName());
            if (close) {
                tabPane.getTabs().remove(tab1);
                EventHandler<Event> handler = tab1.getOnClosed();
                if (handler != null) {
                    handler.handle(null);
                }
            }
        }
    }

    @Override
    public void discardChanges(String currentId, File file) {
        Tab tab1 = tabPane.getTabs().stream().filter(tab -> Objects.equals(tab.getId(), currentId)).findFirst().orElse(null);
        if (tab1 != null) {
            tabPane.getTabs().remove(tab1);
            EventHandler<Event> handler = tab1.getOnClosed();
            if (handler != null) {
                handler.handle(null);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public MenuBar createMenu() {
        final MenuBar menuBar = new MenuBar();
        // FILE
        final Menu file = new Menu("File");
        final MenuItem open = new MenuItem("Open...");
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.META_DOWN));
        open.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Document");
            File selectedFile = fileChooser.showOpenDialog(tabPane.getScene().getWindow());
            if (selectedFile == null) {
                return;
            }
            menuOptionSelected(MenuListener.MenuOption.OPEN_FILE, selectedFile);
        });

        final MenuItem newd = new MenuItem("New Description Document");
        newd.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.META_DOWN));
        newd.setOnAction(e -> menuOptionSelected(MenuListener.MenuOption.NEW_DESCRIPTION_DOCUMENT));

        final MenuItem newi = new MenuItem("New Instantiation Document");
        newi.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.META_DOWN));
        newi.setOnAction(e -> menuOptionSelected(MenuListener.MenuOption.NEW_INSTANTIATION_DOCUMENT));

        final MenuItem newc = new MenuItem("New Collection");
        newc.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHIFT_DOWN, KeyCombination.META_DOWN));
        newc.setOnAction(e -> menuOptionSelected(MenuListener.MenuOption.NEW_COLLECTION));

        final MenuItem batch = new MenuItem("Batch edit open documents");

        final MenuItem export = new MenuItem("Export open files to ZIP");
        final MenuItem save = new MenuItem("Save");
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.META_DOWN));
        save.setOnAction(e -> menuOptionSelected(MenuListener.MenuOption.SAVE));
        final MenuItem saveas = new MenuItem("Save as...");
        saveas.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN, KeyCombination.META_DOWN));
        saveas.setOnAction(e -> menuOptionSelected(MenuListener.MenuOption.SAVE_AS));

        final MenuItem quit = new MenuItem("Quit");
        quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.META_DOWN));
        quit.setOnAction(e -> menuOptionSelected(MenuOption.QUIT));
        file.getItems().addAll(open, new SeparatorMenuItem(), newd, newi, newc, new SeparatorMenuItem(), batch, export, new SeparatorMenuItem(), save, saveas, new SeparatorMenuItem(), quit);

        // SEARCH
        search = new Menu("Search");
        onSavedSearchesUpdated();
        Registry registry = MainApp.getInstance().getRegistry();
        registry.addSavedSearchesListener(this);
        // SETTINGS
        final Menu settings = new Menu("Settings");
        final MenuItem cv = new MenuItem("Controlled Vocabularies");
        final MenuItem folders = new MenuItem("Directory Crawling");
        cv.setOnAction(e -> menuOptionSelected(MenuOption.CONTROLLED_VOCABULARIES));
        folders.setOnAction(e -> menuOptionSelected(MenuOption.DIRECTORY_CRAWLING));
        settings.getItems().addAll(cv, folders);

        // HELP
        final Menu help = new Menu("Help");
        help.setDisable(true);  //temp

        menuBar.getMenus().addAll(file, search, settings, help);
        if (MainApp.getInstance().getRegistry().isMac()) {
            menuBar.setUseSystemMenuBar(true);
        }
        return menuBar;
    }

    @Override
    public void onShown() {
        splash.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> Platform.runLater(() -> {
                    Registry registry = MainApp.getInstance().getRegistry();
                    registry.getPbCoreElements().entrySet().forEach((entry) -> {
                        String s = registry.getCurrentWorkPagesFilenames().get(entry.getKey());
                        String s1 = registry.getCurrentWorkPages().get(entry.getKey());
                        showTab(entry.getKey(), new File(s == null ? s1 : s), entry.getValue());
                    });
                    spinnerLayer.setVisible(false);
                })));
        timeline.play();
    }

    @Override
    public void searchResultSelected(HitDocument hitDocument) {
        showTab(null, new File(hitDocument.getFilepath()), hitDocument.getPbCoreElement().copy());
    }

    @Override
    public void onSavedSearchesUpdated() {
        search.getItems().clear();
        final MenuItem newSearch = new MenuItem("New Search");
        newSearch.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.META_DOWN));
        newSearch.setOnAction(e -> menuOptionSelected(MenuOption.NEW_SEARCH));
        search.getItems().addAll(newSearch, new SeparatorMenuItem());
        Registry registry = MainApp.getInstance().getRegistry();
        registry.getSavedSearches().stream().map((luceneEngineSearchFilters) -> {
            StringBuilder terms = new StringBuilder();
            int c = 1;
            for (LuceneEngineSearchFilter luceneEngineSearchFilter : luceneEngineSearchFilters) {
                terms.append(luceneEngineSearchFilter.getTerm());
                if (c++ != luceneEngineSearchFilters.size()) {
                    terms.append(", ");
                }
            }
            MenuItem menuItem = new MenuItem(terms.toString());
            menuItem.setOnAction(e -> menuOptionSelected(MenuOption.SAVED_SEARCH, luceneEngineSearchFilters));
            return menuItem;
        }).forEachOrdered((menuItem) -> {
            search.getItems().add(menuItem);
        });
    }
}
