package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.components.SearchFilterListCell;
import digitalbedrock.software.pbcore.components.editor.AceEditor;
import digitalbedrock.software.pbcore.listeners.SearchFilterElementsSelectionListener;
import digitalbedrock.software.pbcore.lucene.HitDocument;
import digitalbedrock.software.pbcore.lucene.LuceneEngine;
import digitalbedrock.software.pbcore.lucene.LuceneEngineSearchFilter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class SearchController extends AbsController {

    @FXML
    private TextField textFieldTerm;
    @FXML
    private ListView<LuceneEngineSearchFilter> lvSearchOptions;
    @FXML
    private AnchorPane spinnerLayer;
    @FXML
    private Label lblElementsCount;
    @FXML
    private Label lblAttributesCount;

    @FXML
    private ListView<HitDocument> listViewHits;

    @FXML
    private Pagination pagination;

    @FXML
    private AceEditor aceEditor;
    @FXML
    private Label lblNoFileSelected;
    @FXML
    private Label lblTotalResults;

    private final int offset = 0;
    private static final int MAX_RESULTS = 10;

    private LuceneEngineSearchFilter mainFilter;

    public SearchController() {
        mainFilter = new LuceneEngineSearchFilter("", true, null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lvSearchOptions.setCellFactory(param -> new SearchFilterListCell(new SearchFilterItemController.FilterInteractionListener() {
            @Override
            public void onRemove(int index, LuceneEngineSearchFilter searchFilter) {
                lvSearchOptions.getItems().remove(searchFilter);
            }

            @Override
            public void onSelectElements(int index, LuceneEngineSearchFilter searchFilter) {
                menuOptionSelected(MenuOption.SELECT_SEARCH_FILTER_ELEMENTS, index, searchFilter, new SearchFilterElementsSelectionListener() {
                    @Override
                    public void onFiltersDefined(int index, LuceneEngineSearchFilter luceneEngineSearchFilter) {
                        System.out.println("other filter");
                    }
                });
            }
        }));
        listViewHits.setCellFactory((ListView<HitDocument> param) -> new ListCell<HitDocument>() {
            @Override
            protected void updateItem(HitDocument item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search_result_item.fxml"));
                        Node graphic = loader.load();
                        SearchResultItemController controller = loader.getController();
                        controller.bind(item);
                        setGraphic(graphic);
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            }
        });
        listViewHits.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                lblNoFileSelected.setVisible(true);
                aceEditor.setVisible(false);
            } else {
                lblNoFileSelected.setVisible(false);
                aceEditor.setVisible(true);
                aceEditor.updatePreview(newValue.getPbCoreElement());
            }
        });

        textFieldTerm.textProperty().addListener((observable, oldValue, newValue) -> mainFilter.setTerm(newValue));
        textFieldTerm.setText(mainFilter.getTerm());
        reloadElementsCount();
        pagination.setPageCount(1);
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            search(null);
        });
        aceEditor.setVisible(false);
    }

    private void reloadElementsCount() {
        lblElementsCount.setText(mainFilter.isAllElements() ? "All" : (mainFilter.getElementsCount() + ""));
        lblAttributesCount.setText(mainFilter.isAllElements() ? "All" : (mainFilter.getAttributesCount() + ""));
    }

    @Override
    public MenuBar createMenu() {
        final MenuBar menuBar = new MenuBar();
        return menuBar;
    }

    @FXML
    public void addCondition(ActionEvent event) {
        lvSearchOptions.getItems().add(new LuceneEngineSearchFilter("", true, null));
    }

    @FXML
    public void resetSearch(ActionEvent event) {
        textFieldTerm.setText(null);
        lvSearchOptions.getItems().clear();
        mainFilter.setAllElements(true);
        mainFilter.setFieldsToSearch(null);
        reloadElementsCount();
    }

    @FXML
    public void search(ActionEvent event) {
        LuceneEngine luceneEngine = new LuceneEngine();
        List<LuceneEngineSearchFilter> andOperators = new ArrayList<>();
        andOperators.add(mainFilter);
        andOperators.addAll(lvSearchOptions.getItems());
        new Thread(new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                spinnerLayer.setVisible(true);
                System.out.println("start search");
                Map.Entry<Long, List<HitDocument>> search = luceneEngine.search(andOperators, pagination.getCurrentPageIndex(), MAX_RESULTS);
                System.out.println("end search");
                Platform.runLater(() -> {
                    pagination.setPageCount((int) roundUp(search.getKey().intValue(), MAX_RESULTS));
                    listViewHits.setItems(FXCollections.observableArrayList(search.getValue()));
                    spinnerLayer.setVisible(false);
                    MainApp.getInstance().getRegistry().addRecentSearch(andOperators);
                    lblTotalResults.setText("(" + search.getKey() + " files)");
                });
                return null;
            }
        }).start();
    }

    public static long roundUp(long num, long divisor) {
        return (num + divisor - 1) / divisor;
    }

    @FXML
    public void selectMainFilterElements(ActionEvent actionEvent) {
        menuOptionSelected(MenuOption.SELECT_SEARCH_FILTER_ELEMENTS, 0, mainFilter, (SearchFilterElementsSelectionListener) (index, searchFilter) -> {
            System.out.println("main filter");
            reloadElementsCount();
        });
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) textFieldTerm.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    public void onFileSelected(ActionEvent actionEvent) {
        HitDocument selectedItem = listViewHits.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            menuOptionSelected(MenuOption.SEARCH_RESULT_SELECTED, selectedItem);
        }
    }

    public void setFilters(List<LuceneEngineSearchFilter> filters) {
        LuceneEngineSearchFilter remove = filters.remove(0);
        mainFilter = remove;
        textFieldTerm.textProperty().addListener((observable, oldValue, newValue) -> mainFilter.setTerm(newValue));
        textFieldTerm.setText(mainFilter.getTerm());
        reloadElementsCount();
        filters.forEach(filter -> lvSearchOptions.getItems().add(filter));
    }
}
