package digitalbedrock.software.pbcore;

import digitalbedrock.software.pbcore.controllers.*;
import digitalbedrock.software.pbcore.core.Settings;
import digitalbedrock.software.pbcore.core.models.FolderModel;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import digitalbedrock.software.pbcore.listeners.*;
import digitalbedrock.software.pbcore.lucene.HitDocument;
import digitalbedrock.software.pbcore.lucene.LuceneEngineSearchFilter;
import digitalbedrock.software.pbcore.lucene.LuceneIndexer;
import digitalbedrock.software.pbcore.utils.Registry;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {

    private Stage stage;

    private static MainApp instance;
    private SearchResultListener searchResultListener;
    private final Registry registry;

    public MainApp() throws IOException {
        instance = this;
        this.registry = new Registry();
    }

    public static MainApp getInstance() {
        return instance;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setTitle("PBCore Cataloging Tool");

            registry.loadSavedSettings();
            if (registry.getSettings().getFolders().isEmpty()) {
                showSettings(1);
            }
            PBCoreStructure.getInstance();
            goToMainScreen();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        Settings settings = registry.getSettings();
        try {
            new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    settings.getFolders().stream().filter(FolderModel::isIndexing).forEach(folderModel -> {
                        folderModel.setTotalValidFiles(0);
                        LuceneIndexer.getInstance().startFolderIndexing(folderModel.getFolderPath());
                        settings.updateFolder(folderModel);
                    });
                    settings.getFolders().stream().filter(FolderModel::isScheduled).forEach(folderModel -> LuceneIndexer.getInstance().startFolderIndexing(folderModel.getFolderPath()));
                    return null;
                }
            }.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Parent replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent page = null;
        try {
            page = loader.load();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Scene scene = stage.getScene();

        final BorderPane borderPane = new BorderPane();
        AbsController controller = loader.getController();
        if (controller instanceof SearchResultListener) {
            searchResultListener = (SearchResultListener) controller;
        }
        borderPane.setTop(controller.createMenu());
        borderPane.setCenter(page);

        if (scene == null) {
            scene = new Scene(borderPane);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setScene(scene);
            stage.setTitle("PBCore Cataloging Tool");
            stage.setOnShown(event -> controller.onShown());
            stage.show();
        } else {
            stage.getScene().setRoot(page);
        }
        return page;
    }

    public Registry getRegistry() {
        return registry;
    }

    private void showSettings(int tab) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
            Parent tabs = loader.load();
            ((TabPane) tabs.lookup("#tabs")).getSelectionModel().select(tab);

            final BorderPane borderPane = new BorderPane();
            AbsController controller = loader.getController();
            borderPane.setTop(controller.createMenu());
            borderPane.setCenter(tabs);

            Scene settingsScene = new Scene(borderPane);
            Stage settingsWindow = new Stage();
            settingsWindow.initOwner(settingsScene.getWindow());
            settingsWindow.initModality(Modality.WINDOW_MODAL);
            settingsWindow.setTitle("Settings");
            settingsWindow.setScene(settingsScene);
            settingsWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSearch() {
        showSearch(null);
    }

    private void showSearch(List<LuceneEngineSearchFilter> filters) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search.fxml"));
            Parent tabs = loader.load();

            final BorderPane borderPane = new BorderPane();
            SearchController controller = loader.getController();
            if (filters != null) {
                controller.setFilters(filters);
            }
            borderPane.setTop(controller.createMenu());
            borderPane.setCenter(tabs);

            Scene settingsScene = new Scene(borderPane);
            Stage settingsWindow = new Stage();
            settingsWindow.initOwner(settingsScene.getWindow());
            settingsWindow.initModality(Modality.WINDOW_MODAL);
            settingsWindow.setTitle("Search");
            settingsWindow.setScene(settingsScene);
            settingsWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showSelectAttribute(PBCoreElement pbCoreElement, AttributeSelectionListener attributeSelectionListener) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/attribute_selector.fxml"));
            Parent parent = loader.load();
            AttributeSelectorController controller = loader.getController();
            controller.setPbCoreElement(pbCoreElement);
            Scene searchScene = new Scene(parent);
            Stage searchWindow = new Stage();
            searchWindow.initOwner(searchScene.getWindow());
            searchWindow.initModality(Modality.WINDOW_MODAL);
            searchWindow.setTitle("Add new attribute");
            searchWindow.setScene(searchScene);
            searchWindow.show();
            controller.setAttributeSelectionListener(element -> {
                if (attributeSelectionListener != null) {
                    attributeSelectionListener.onAttributeSelected(element);
                }
                searchWindow.close();
            });
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showSelectElement(int index, PBCoreElement pbCoreElement, ElementSelectionListener elementSelectionListener) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/element_selector.fxml"));
            Parent parent = loader.load();
            ElementSelectorController controller = loader.getController();
            controller.setPbCoreElement(pbCoreElement);
            Scene searchScene = new Scene(parent);
            Stage selectElementWindow = new Stage();
            selectElementWindow.initOwner(stage);
            selectElementWindow.initOwner(searchScene.getWindow());
            selectElementWindow.initModality(Modality.WINDOW_MODAL);
            selectElementWindow.setTitle("Add new element");
            selectElementWindow.setScene(searchScene);
            selectElementWindow.show();
            controller.setElementSelectionListener(index, (index1, element) -> {
                if (elementSelectionListener != null) {
                    elementSelectionListener.onElementSelected(index1, element);
                }
                selectElementWindow.close();
            });
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showSelectSearchFieldElements(int index, LuceneEngineSearchFilter luceneEngineSearchFilter, SearchFilterElementsSelectionListener elementSelectionListener) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search_filter_elements_selector.fxml"));
            Parent parent = loader.load();
            SearchFilterElementsSelectorController controller = loader.getController();
            controller.setSearchFilter(luceneEngineSearchFilter);
            Scene searchScene = new Scene(parent);
            Stage selectElementWindow = new Stage();
            selectElementWindow.initOwner(stage);
            selectElementWindow.initOwner(searchScene.getWindow());
            selectElementWindow.initModality(Modality.APPLICATION_MODAL);
            selectElementWindow.setTitle("Select fields to search");
            selectElementWindow.setScene(searchScene);
            selectElementWindow.show();
            controller.setElementSelectionListener(index, (index1, element) -> {
                if (elementSelectionListener != null) {
                    elementSelectionListener.onFiltersDefined(index1, element);
                }
                selectElementWindow.close();
            });
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goTo(MenuListener.MenuOption menuOption, Object... objects) {
        switch (menuOption) {
            case OPEN_FILE:
                goToMainScreen();
                break;
            case NEW_DESCRIPTION_DOCUMENT:
                goToMainScreen();
                break;
            case NEW_INSTANTIATION_DOCUMENT:
                goToMainScreen();
                break;
            case NEW_COLLECTION:
                goToMainScreen();
                break;
            case BATCH_EDIT:
                break;
            case EXPORT_OPEN_FILES_TO_ZIP:
                break;
            case SAVE:
                goToMainScreen();
                break;
            case SAVE_AS:
                goToMainScreen();
                break;
            case QUIT:
                quit();
                break;
            case NEW_SEARCH:
                showSearch();
                break;
            case SAVED_SEARCH:
                showSearch((List<LuceneEngineSearchFilter>) objects[0]);
                break;
            case CONTROLLED_VOCABULARIES:
                showSettings(0);
                break;
            case DIRECTORY_CRAWLING:
                showSettings(1);
                break;
            case HELP:
                break;
            case SELECT_SEARCH_FILTER_ELEMENTS:
                showSelectSearchFieldElements((int) objects[0], (LuceneEngineSearchFilter) objects[1], (SearchFilterElementsSelectionListener) objects[2]);
                break;
            case SELECT_ELEMENT:
                showSelectElement((int) objects[0], (PBCoreElement) objects[1], (ElementSelectionListener) objects[2]);
                break;
            case SELECT_ATTRIBUTE:
                showSelectAttribute((PBCoreElement) objects[0], (AttributeSelectionListener) objects[1]);
                break;
            case SEARCH_RESULT_SELECTED:
                if (searchResultListener != null) {
                    searchResultListener.searchResultSelected((HitDocument) objects[0]);
                }
                break;
        }
    }

    private void goToMainScreen() {
        try {
            replaceSceneContent("/fxml/main.fxml");
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void quit() {
        Platform.exit();
    }
}
