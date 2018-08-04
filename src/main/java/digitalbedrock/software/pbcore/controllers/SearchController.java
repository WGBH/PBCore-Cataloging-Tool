package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.components.SearchFilterListCell;
import digitalbedrock.software.pbcore.components.editor.IPBCorePreviewItemListCell;
import digitalbedrock.software.pbcore.core.models.entity.*;
import digitalbedrock.software.pbcore.listeners.SearchFilterElementsSelectionListener;
import digitalbedrock.software.pbcore.lucene.HitDocument;
import digitalbedrock.software.pbcore.lucene.LuceneEngine;
import digitalbedrock.software.pbcore.lucene.LuceneEngineSearchFilter;
import digitalbedrock.software.pbcore.parsers.CSVPBCoreParser;
import digitalbedrock.software.pbcore.utils.PBCoreUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private ListView<IPBCore> treeViewPreview;
    @FXML
    private Label lblNoFileSelected;
    @FXML
    private Label lblTotalResults;
    @FXML
    private StackPane stackPaneInstatiationPreview;
    @FXML
    private ListView<IPBCore> treeViewInstatiationPreview;
    @FXML
    private Button buttonCloseInstatiationPreview;
    @FXML
    private Button btnExportSearchToCsv;
    @FXML
    private Label previewItemsSubTitle;
    @FXML
    private Button btnShowInExplorer;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnOpen;

    private final int offset = 0;
    private static final int MAX_RESULTS = 10;

    private LuceneEngineSearchFilter mainFilter;

    public SearchController() {
        mainFilter = new LuceneEngineSearchFilter();
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
                        Logger.getLogger(getClass().getName()).log(Level.INFO, null, "other filter");
                        lvSearchOptions.refresh();
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
        btnOpen.setDisable(true);
        listViewHits.getSelectionModel().getSelectedItems().addListener((ListChangeListener<HitDocument>) c -> {
            if (c.getList() == null || c.getList().isEmpty() || c.getList().size() > 1) {
                lblNoFileSelected.setVisible(true);
                treeViewPreview.setVisible(false);
                stackPaneInstatiationPreview.setVisible(false);
                buttonCloseInstatiationPreview.setVisible(false);
                previewItemsSubTitle.setVisible(false);
                lblNoFileSelected.setText(c.getList() == null || c.getList().isEmpty() ? "No File Selected" : "Multiple Files selected");
                btnShowInExplorer.setDisable(c.getList() == null || c.getList().isEmpty());
                btnOpen.setDisable(c.getList() == null || c.getList().isEmpty());
            } else {
                btnOpen.setDisable(false);
                btnShowInExplorer.setDisable(false);
                HitDocument newValue = c.getList().get(0);
                lblNoFileSelected.setVisible(false);
                treeViewPreview.setVisible(true);
                stackPaneInstatiationPreview.setVisible(false);
                buttonCloseInstatiationPreview.setVisible(false);
                previewItemsSubTitle.setVisible(false);
                treeViewPreview.setItems(FXCollections.emptyObservableList());
                List<IPBCore> flatList = new ArrayList<>();
                AtomicInteger index = new AtomicInteger(0);
                if (newValue.getPbCoreElement().getName().equalsIgnoreCase("pbcoreInstantiationDocument")) {
                    getInstantiationFlatTree(index, flatList, newValue.getPbCoreElement());
                } else {
                    getFlatTree(index, flatList, newValue.getPbCoreElement());
                }
                treeViewPreview.setItems(FXCollections.observableArrayList(flatList));
            }
        });
        listViewHits.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buttonCloseInstatiationPreview.setOnAction(event -> {
            stackPaneInstatiationPreview.setVisible(false);
            treeViewPreview.setVisible(true);
            buttonCloseInstatiationPreview.setVisible(false);
            previewItemsSubTitle.setVisible(false);
        });
        textFieldTerm.textProperty().addListener((observable, oldValue, newValue) -> mainFilter.setTerm(newValue));
        textFieldTerm.setText(mainFilter.getTerm());
        reloadElementsCount();
        pagination.setPageCount(1);
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            search(null);
        });
        treeViewPreview.setVisible(false);
        stackPaneInstatiationPreview.setVisible(false);
        buttonCloseInstatiationPreview.setVisible(false);
        previewItemsSubTitle.setVisible(false);
        treeViewInstatiationPreview.setCellFactory(lv -> new IPBCorePreviewItemListCell(null));
        treeViewPreview.setCellFactory(lv -> new IPBCorePreviewItemListCell(element -> {
            stackPaneInstatiationPreview.setVisible(true);
            treeViewPreview.setVisible(false);
            buttonCloseInstatiationPreview.setVisible(true);
            previewItemsSubTitle.setVisible(true);

            treeViewInstatiationPreview.setItems(FXCollections.emptyObservableList());
            List<IPBCore> flatList = new ArrayList<>();
            AtomicInteger index = new AtomicInteger(0);
            PBCoreElement element1 = (PBCoreElement) element;
            element1.setTypeForLayout(IPBCoreLayoutType.NORMAL);
            getInstantiationFlatTree(index, flatList, element1);
            treeViewInstatiationPreview.setItems(FXCollections.observableArrayList(flatList));
        }));
        textFieldTerm.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                search(new ActionEvent());
            }
        });
    }

    private void getFlatTree(AtomicInteger index, List<IPBCore> listToAdd, PBCoreElement rootElement) {
        List<String> names = Arrays.asList("pbcoreDescriptionDocument", "pbcoreInstantiationDocument", "pbcoreTitle", "pbcoreAssetDate", "pbcoreDescription");
        int idx = index.get() + 1;
        if (rootElement == null) {
            return;
        }
        rootElement.setIndex(idx);
        listToAdd.add(rootElement);
        rootElement.getAttributes().forEach(pbCoreAttribute -> pbCoreAttribute.setIndex(idx));
        if (idx != 1) {
            listToAdd.addAll(rootElement.getAttributes());
        }
        index.incrementAndGet();
        List<PBCoreElement> orderedSubElements = rootElement.getOrderedSubElements();
        orderedSubElements.sort((o1, o2) -> {
            if (Objects.equals(o1.getName(), "pbcoreTitle") && !Objects.equals(o2.getName(), "pbcoreTitle")) {
                return -1;
            } else if (!Objects.equals(o1.getName(), "pbcoreTitle") && Objects.equals(o2.getName(), "pbcoreTitle")) {
                return 1;
            } else if (Objects.equals(o1.getName(), "pbcoreTitle") && Objects.equals(o2.getName(), "pbcoreTitle")) {
                int o1Index = getAttrIndex(o1);
                int o2Index = getAttrIndex(o2);
                int compare = Integer.compare(o2Index, o1Index);
                if (compare != 0) {
                    return compare;
                }
            }
            if (Objects.equals(o1.getName(), "pbcoreAssetDate") && !Objects.equals(o2.getName(), "pbcoreAssetDate")) {
                return -1;
            } else if (!Objects.equals(o1.getName(), "pbcoreAssetDate") && Objects.equals(o2.getName(), "pbcoreAssetDate")) {
                return 1;
            }
            if (Objects.equals(o1.getName(), "pbcoreDescription") && !Objects.equals(o2.getName(), "pbcoreDescription")) {
                return -1;
            } else if (!Objects.equals(o1.getName(), "pbcoreDescription") && Objects.equals(o2.getName(), "pbcoreDescription")) {
                return 1;
            }
            return 0;
        });
        orderedSubElements.stream().filter(pbCoreElement -> names.contains(pbCoreElement.getName())).forEach(coreElement -> getFlatTree(new AtomicInteger(idx), listToAdd, coreElement));

        verifyAndAddDummyRecord(listToAdd, rootElement, idx);
    }

    private int getAttrIndex(PBCoreElement pbCoreElement) {
        PBCoreAttribute pbCoreAttribute1 = pbCoreElement.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getName().equalsIgnoreCase("titleType")).findFirst().orElse(null);
        if (pbCoreAttribute1 != null && pbCoreAttribute1.getValue().equalsIgnoreCase("series")) {
            return 4;
        }
        pbCoreAttribute1 = pbCoreElement.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getName().equalsIgnoreCase("titleType")).findFirst().orElse(null);
        if (pbCoreAttribute1 != null && pbCoreAttribute1.getValue().equalsIgnoreCase("episode")) {
            return 3;
        }
        pbCoreAttribute1 = pbCoreElement.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getName().equalsIgnoreCase("titleType")).findFirst().orElse(null);
        if (pbCoreAttribute1 != null && pbCoreAttribute1.getValue().equalsIgnoreCase("program")) {
            return 2;
        }
        return 1;
    }

    private void verifyAndAddDummyRecord(List<IPBCore> listToAdd, PBCoreElement rootElement, int idx) {
        if (rootElement.getName().equals("pbcoreDescriptionDocument") && rootElement.getSubElements().stream().filter(pbCoreElement -> pbCoreElement.getName().equals("pbcoreInstantiation")).count() > 0) {
            PBCoreDummyItemsElement pbCoreDummyItemsElement = new PBCoreDummyItemsElement();
            pbCoreDummyItemsElement.setIndex(idx + 1);
            listToAdd.add(pbCoreDummyItemsElement);
            getInstantiationItems(listToAdd, idx, rootElement);
        }
    }

    private void getInstantiationFlatTree(AtomicInteger index, List<IPBCore> listToAdd, PBCoreElement rootElement) {
        int idx = index.get() + 1;
        if (rootElement == null) {
            return;
        }
        rootElement.setIndex(idx);
        listToAdd.add(rootElement);
        rootElement.getAttributes().forEach(pbCoreAttribute -> pbCoreAttribute.setIndex(idx));
        if (idx != 1) {
            listToAdd.addAll(rootElement.getAttributes());
        }
        index.incrementAndGet();
        rootElement.getOrderedSubElements().forEach(coreElement -> getInstantiationFlatTree(new AtomicInteger(idx), listToAdd, coreElement));

        verifyAndAddDummyRecord(listToAdd, rootElement, idx);
    }

    private void getInstantiationItems(List<IPBCore> listToAdd, int idx, PBCoreElement rootElement) {
        rootElement.getOrderedSubElements()
                .stream()
                .filter(pbCoreElement -> pbCoreElement.getName().equals("pbcoreInstantiation"))
                .forEach(pb -> {
                    pb.setTypeForLayout(IPBCoreLayoutType.INSTANTIATION);
                    pb.setIndex(idx + 2);
                    listToAdd.add(pb);
                });
    }

    private void reloadElementsCount() {
        lblElementsCount.setText(mainFilter.isAllElements() ? "All" : (mainFilter.getElementsCount() + ""));
        lblAttributesCount.setText(mainFilter.isAllElements() ? "All" : (mainFilter.getAttributesCount() + ""));
    }

    @Override
    public MenuBar createMenu() {
        return new MenuBar();
    }

    @FXML
    public void addCondition(ActionEvent event) {
        lvSearchOptions.getItems().add(new LuceneEngineSearchFilter());
    }

    @FXML
    public void resetSearch(ActionEvent event) {
        btnOpen.setDisable(true);
        btnShowInExplorer.setDisable(true);
        textFieldTerm.setText(null);
        lvSearchOptions.getItems().clear();
        mainFilter.setAsAllElementsFilter();
        reloadElementsCount();
    }

    @SuppressWarnings("WeakerAccess")
    @FXML
    public void search(@SuppressWarnings("SameParameterValue") ActionEvent event) {
        if (event != null) {
            pagination.setPageCount(1);
            pagination.setCurrentPageIndex(0);
        }
        lblTotalResults.setText("(" + 0 + " files)");
        LuceneEngine luceneEngine = new LuceneEngine();
        List<LuceneEngineSearchFilter> andOperators = new ArrayList<>();
        andOperators.add(mainFilter);
        andOperators.addAll(lvSearchOptions.getItems());
        new Thread(new Task<Object>() {
            @Override
            protected Object call() throws Exception {

                spinnerLayer.setVisible(true);
                btnSearch.setDisable(true);
                Map.Entry<Long, List<HitDocument>> search = luceneEngine.search(andOperators, pagination.getCurrentPageIndex(), MAX_RESULTS);
                Platform.runLater(() -> {
                    int i = (int) roundUp(search.getKey().intValue(), MAX_RESULTS);
                    pagination.setPageCount(i == 0 ? 1 : i);
                    listViewHits.setItems(FXCollections.observableArrayList(search.getValue()));
                    btnSearch.setDisable(false);
                    spinnerLayer.setVisible(false);
                    MainApp.getInstance().getRegistry().addRecentSearch(andOperators);
                    lblTotalResults.setText("(" + search.getKey() + " files)");
                    btnExportSearchToCsv.setDisable(search.getKey() < 1);
                });
                return null;
            }
        }).start();
    }

    private static long roundUp(long num, @SuppressWarnings("SameParameterValue") long divisor) {
        return (num + divisor - 1) / divisor;
    }

    @FXML
    public void selectMainFilterElements(ActionEvent actionEvent) {
        menuOptionSelected(MenuOption.SELECT_SEARCH_FILTER_ELEMENTS, 0, mainFilter, (SearchFilterElementsSelectionListener) (index, searchFilter) -> {
            reloadElementsCount();
        });
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) textFieldTerm.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    public void onExportSearchToCsv(ActionEvent actionEvent) {
        spinnerLayer.setVisible(true);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(listViewHits.getScene().getWindow());
        if (file == null) {
            spinnerLayer.setVisible(false);
            return;
        }
        exportFullSearch(file);
    }

    private void exportFullSearch(File file) {
        LuceneEngine luceneEngine = new LuceneEngine();
        List<LuceneEngineSearchFilter> andOperators = new ArrayList<>();
        andOperators.add(mainFilter);
        andOperators.addAll(lvSearchOptions.getItems());
        new Thread(new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                spinnerLayer.setVisible(true);
                btnSearch.setDisable(true);
                Map.Entry<Long, List<HitDocument>> search = luceneEngine.search(andOperators, 0, -1);
                Map<String, PBCoreElement> mapPBCoreElements = PBCoreUtils.convertToDescriptionDocsMap(search.getValue());
                CSVPBCoreParser.writeFile(mapPBCoreElements, file.getAbsolutePath());
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Documents exported");
                    alert.setContentText("Documents exported to csv successfully");
                    alert.setHeaderText(null);
                    alert.getButtonTypes().setAll(new ButtonType("Ok"));
                    alert.showAndWait();
                    spinnerLayer.setVisible(false);
                });
                return null;
            }
        }).start();
    }

    @FXML
    public void onFileSelected(ActionEvent actionEvent) {
        spinnerLayer.setVisible(true);
        List<HitDocument> selectedItems = listViewHits.getSelectionModel().getSelectedItems();
        if (selectedItems != null) {
            if (selectedItems.size() >= 10) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Open files");
                alert.setContentText("You have selected " + selectedItems.size() + " files to open and it can take a while to open this many files, do you want to proceed?");
                alert.setHeaderText(null);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    menuOptionSelected(MenuOption.SEARCH_RESULT_SELECTED, selectedItems);
                }
            } else {
                menuOptionSelected(MenuOption.SEARCH_RESULT_SELECTED, selectedItems);
            }
        }
        spinnerLayer.setVisible(false);
    }

    public void setFilters(List<LuceneEngineSearchFilter> filters) {
        mainFilter = filters.remove(0);
        textFieldTerm.textProperty().addListener((observable, oldValue, newValue) -> mainFilter.setTerm(newValue));
        textFieldTerm.setText(mainFilter.getTerm());
        reloadElementsCount();
        filters.forEach(filter -> lvSearchOptions.getItems().add(filter));
    }

    @FXML
    public void showInExplorer(ActionEvent actionEvent) {
        List<HitDocument> selectedItems = listViewHits.getSelectionModel().getSelectedItems();
        if (selectedItems != null) {
            for (HitDocument selectedItem : selectedItems) {
                if (PBCoreUtils.isWindows()) {
                    try {
                        Runtime.getRuntime().exec("explorer.exe /select," + selectedItem.getFilepath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (PBCoreUtils.isMac()) {
                    try {
                        Runtime.getRuntime().exec("open --reveal " + selectedItem.getFilepath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                        try {
                            File file = new File(selectedItem.getFilepath());
                            desktop.open(file.getParentFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
