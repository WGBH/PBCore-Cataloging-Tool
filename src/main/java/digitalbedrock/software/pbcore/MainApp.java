package digitalbedrock.software.pbcore;

import digitalbedrock.software.pbcore.controllers.*;
import digitalbedrock.software.pbcore.core.Settings;
import digitalbedrock.software.pbcore.core.models.FolderModel;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreAttribute;
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
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {

    private Stage stage;

    private static MainApp instance;
    private SearchResultListener searchResultListener;
    private final Registry registry;

    private Stage searchStage;
    private Stage settingsStage;

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
            System.setProperty("file.encoding","UTF-8");
            Field charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null,null);
//            System.out.println("Default Locale:   " + Locale.getDefault());
//            System.out.println("Default Charset:  " + Charset.defaultCharset());
//            System.out.println("file.encoding;    " + System.getProperty("file.encoding"));
//            System.out.println("sun.jnu.encoding: " + System.getProperty("sun.jnu.encoding"));
            stage = primaryStage;
            stage.setTitle("PBCore Cataloging Tool");

            registry.loadSavedSettings();
            PBCoreStructure.getInstance();
            goToMainScreen();
            primaryStage.show();
            if (registry.getSettings().getFolders().isEmpty()) {
                showSettings(true, 1);
            }
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

    public Registry getRegistry() {
        return registry;
    }

    private void showSettings(boolean block, int tab) {
        if (settingsStage != null) {
            settingsStage.toFront();
            return;
        }
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
            settingsWindow.initModality(block ? Modality.APPLICATION_MODAL : Modality.WINDOW_MODAL);
            settingsWindow.setTitle("Settings");
            settingsWindow.setScene(settingsScene);
            settingsWindow.show();
            settingsWindow.setOnCloseRequest(event -> settingsStage = null);
            settingsStage = settingsWindow;
            settingsStage.toFront();
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSearch() {
        showSearch(null);
    }

    private void showSearch(List<LuceneEngineSearchFilter> filters) {
        if (searchStage != null) {
            searchStage.toFront();
            return;
        }
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
            settingsWindow.setOnCloseRequest(event -> searchStage = null);
            searchStage = settingsWindow;
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void showAbout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
            Parent tabs = loader.load();

            final BorderPane borderPane = new BorderPane();
            loader.getController();
            borderPane.setCenter(tabs);

            Scene settingsScene = new Scene(borderPane);
            Stage settingsWindow = new Stage();
            settingsWindow.initOwner(settingsScene.getWindow());
            settingsWindow.initModality(Modality.APPLICATION_MODAL);
            settingsWindow.setTitle("About");
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
            searchWindow.initModality(Modality.APPLICATION_MODAL);
            searchWindow.setTitle("Add new attribute");
            searchWindow.setScene(searchScene);
            searchWindow.show();
            controller.setAttributeSelectionListener((element, close) -> {
                if (attributeSelectionListener != null) {
                    attributeSelectionListener.onAttributeSelected(element, close);
                }
                if (close) {
                    searchWindow.close();
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showAddElementAnyValue(PBCoreElement pbCoreElement, AddElementAnyValueListener addElementAnyValueListener) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/embedded_dialog.fxml"));
            Parent parent = loader.load();
            AddElementAnyValueController controller = loader.getController();
            Scene searchScene = new Scene(parent);
            Stage searchWindow = new Stage();
            searchWindow.initOwner(searchScene.getWindow());
            searchWindow.initModality(Modality.APPLICATION_MODAL);
            searchWindow.setTitle("Add new embedded value");
            searchWindow.setScene(searchScene);
            searchWindow.show();
            controller.setAttributeSelectionListener(element -> {
                if (addElementAnyValueListener != null) {
                    addElementAnyValueListener.onValueAdded(element);
                }
                searchWindow.close();
            });
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSelectElement(String treeViewId, int index, PBCoreElement pbCoreElement, ElementSelectionListener elementSelectionListener) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/element_selector.fxml"));
            Parent parent = loader.load();
            ElementSelectorController controller = loader.getController();
            controller.setPbCoreElement(pbCoreElement);
            Scene searchScene = new Scene(parent);
            Stage selectElementWindow = new Stage();
            selectElementWindow.initOwner(stage);
            selectElementWindow.initOwner(searchScene.getWindow());
            selectElementWindow.initModality(Modality.APPLICATION_MODAL);
            selectElementWindow.setTitle("Add new element");
            selectElementWindow.setScene(searchScene);
            selectElementWindow.show();
            controller.setElementSelectionListener(treeViewId, index, (treeViewId1, index1, element, close) -> {
                if (elementSelectionListener != null) {
                    elementSelectionListener.onElementSelected(treeViewId, index, element, close);
                }
                if (close) {
                    selectElementWindow.close();
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSelectCV(boolean attr, String key, CVSelectionListener cvSelectionListener) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cv_selector.fxml"));
            Parent parent = loader.load();
            CVSelectorController controller = loader.getController();
            controller.setKey(attr, key);
            Scene searchScene = new Scene(parent);
            Stage selectElementWindow = new Stage();
            selectElementWindow.initOwner(stage);
            selectElementWindow.initOwner(searchScene.getWindow());
            selectElementWindow.initModality(Modality.APPLICATION_MODAL);
            selectElementWindow.setTitle("Select controlled vocabulary term");
            selectElementWindow.setScene(searchScene);
            selectElementWindow.show();
            controller.setCVSelectionListener((key1, cvTerm, attr1) -> {
                if (cvSelectionListener != null) {
                    cvSelectionListener.onCVSelected(key1, cvTerm, attr1);
                }
                selectElementWindow.close();

            });
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSelectSearchFieldElements(int index, LuceneEngineSearchFilter luceneEngineSearchFilter, SearchFilterElementsSelectionListener elementSelectionListener) {
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
            case NEW_DESCRIPTION_DOCUMENT:
            case NEW_INSTANTIATION_DOCUMENT:
            case SAVE:
            case SAVE_AS:
            case SAVE_AS_TEMPLATE:
            case CONVERT_FROM_CSV:
            case EXPORT_SEARCHED_FILES_TO_CSV:
            case NEW_COLLECTION:
            case BATCH_EDIT:
            case EXPORT_OPEN_FILES_TO_ZIP:
                goToMainScreen();
                break;
            case QUIT:
                quit();
                break;
            case NEW_SEARCH:
                showSearch();
                break;
            case ABOUT:
                showAbout();
                break;
            case SAVED_SEARCH:
                List<LuceneEngineSearchFilter> filters = new ArrayList<>();
                if (objects[0] != null && objects[0] instanceof Collection) {
                    ((Collection) objects[0]).stream().filter((o) -> (o instanceof LuceneEngineSearchFilter)).forEachOrdered((o) -> {
                        filters.add((LuceneEngineSearchFilter) o);
                    });
                }
                showSearch(filters);
                break;
            case CONTROLLED_VOCABULARIES:
                showSettings(false, 0);
                break;
            case DIRECTORY_CRAWLING:
                showSettings(false, 1);
                break;
            case HELP:
                break;
            case SELECT_SEARCH_FILTER_ELEMENTS:
                showSelectSearchFieldElements((int) objects[0], (LuceneEngineSearchFilter) objects[1], (SearchFilterElementsSelectionListener) objects[2]);
                break;
            case SELECT_ELEMENT:
                showSelectElement((String) objects[0], (int) objects[1], (PBCoreElement) objects[2], (ElementSelectionListener) objects[3]);
                break;
            case SELECT_CV_ELEMENT:
                showSelectCV(false, ((PBCoreElement) objects[0]).getName(), (CVSelectionListener) objects[1]);
                break;
            case SELECT_CV_ATTRIBUTE:
                showSelectCV(true, ((PBCoreAttribute) objects[0]).getName(), (CVSelectionListener) objects[1]);
                break;
            case SELECT_ATTRIBUTE:
                showSelectAttribute((PBCoreElement) objects[0], (AttributeSelectionListener) objects[1]);
                break;
            case ADD_ELEMENT_ANY_VALUE:
                showAddElementAnyValue((PBCoreElement) objects[0], (AddElementAnyValueListener) objects[1]);
                break;
            case SEARCH_RESULT_SELECTED:
                if (searchResultListener != null) {
                    searchResultListener.searchResultSelected((List<HitDocument>) objects[0]);
                }
                break;
        }
    }

    private void goToMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
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
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void quit() {
        Platform.exit();
    }
}
