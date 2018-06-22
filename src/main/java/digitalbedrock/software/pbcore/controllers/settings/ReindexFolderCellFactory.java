package digitalbedrock.software.pbcore.controllers.settings;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.models.FolderModel;
import digitalbedrock.software.pbcore.lucene.LuceneIndexer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class ReindexFolderCellFactory implements Callback<TableColumn<FolderModel, Boolean>, TableCell<FolderModel, Boolean>> {

    @Override
    public TableCell<FolderModel, Boolean> call(final TableColumn<FolderModel, Boolean> param) {
        return new TableCell<FolderModel, Boolean>() {
            final Button btnRefresh = new Button("", new FontIcon(MaterialDesign.MDI_REFRESH));
            final Button btnPause = new Button("", new FontIcon(MaterialDesign.MDI_STOP));

            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    FolderModel model = MainApp.getInstance().getRegistry().getSettings().getFolders().get(getIndex());
                    model.indexingProperty().addListener((observable, oldValue, newValue) -> {
                        System.out.println("indexing: " + newValue);
                        updateButtons(model);
                    });
                    model.scheduledProperty().addListener((observable, oldValue, newValue) -> {
                        System.out.println("scheduled: " + newValue);
                        updateButtons(model);
                    });
                    btnRefresh.setOnAction(event -> {
                        LuceneIndexer instance = LuceneIndexer.getInstance();
                        if (instance.startFolderIndexing(model.getFolderPath())) {
                            model.setScheduled(true);
                            MainApp.getInstance().getRegistry().getSettings().updateFolder(model);
                        }
                        updateButtons(model);
                    });
                    btnPause.setOnAction(event -> LuceneIndexer.getInstance().stopIndexingForFolder(model.getFolderPath()));
                    setText(null);
                    updateButtons(model);
                }

                setMaxWidth(25);
                setAlignment(Pos.CENTER);

                btnRefresh.getStyleClass().add("listActionButton");
                btnRefresh.getStyleClass().add("primaryIcon");
                btnPause.getStyleClass().add("listActionButton");
                btnPause.getStyleClass().add("primaryIcon");
            }

            private void updateButtons(FolderModel model) {
                Platform.runLater(() -> {
                    btnRefresh.setVisible(!LuceneIndexer.getInstance().isProcessingFolder(model.getFolderPath())
                            && !LuceneIndexer.getInstance().isScheduledFolder(model.getFolderPath()));
                    btnPause.setVisible(!btnRefresh.isVisible());
                    setGraphic(btnRefresh.isVisible() ? btnRefresh : btnPause);
                });
            }
        };
    }
}
