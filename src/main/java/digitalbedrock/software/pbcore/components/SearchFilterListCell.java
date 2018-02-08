package digitalbedrock.software.pbcore.components;

import digitalbedrock.software.pbcore.controllers.SearchFilterItemController;
import digitalbedrock.software.pbcore.lucene.LuceneEngineSearchFilter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class SearchFilterListCell extends ListCell<LuceneEngineSearchFilter> {

    private SearchFilterItemController controller;
    private final SearchFilterItemController.FilterInteractionListener listener;
    private LuceneEngineSearchFilter searchFilter;

    public SearchFilterListCell(SearchFilterItemController.FilterInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    protected void updateItem(LuceneEngineSearchFilter sf, boolean empty) {
        super.updateItem(sf, empty);
        this.searchFilter = sf;
        if (empty) {
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search_condition_item.fxml"));
                Node graphic = loader.load();
                controller = loader.getController();
                controller.bind(getIndex(), searchFilter, listener);
                setGraphic(graphic);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }

    }

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i);
        if (controller != null && searchFilter != null) {
            controller.updateIndex(i);
        }
    }
}
