package digitalbedrock.software.pbcore;

import digitalbedrock.software.pbcore.controllers.MainAppController;
import digitalbedrock.software.pbcore.controllers.MainController;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException ioe) {
            return;
        }
        MainAppController controller = loader.getController();
        final MainController mainController = new MainController(controller, stage);
        final BorderPane borderPane = new BorderPane();

        borderPane.setTop(mainController.createMenu());
        borderPane.setCenter(root);

        Scene scene = new Scene(borderPane);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.setTitle("PBCore Cataloging Tool");
        stage.setOnShown(mainController::initialize);
        stage.show();

        PBCoreStructure.getInstance();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
