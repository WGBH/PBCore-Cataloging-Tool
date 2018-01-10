package digitalbedrock.software.pbcore;

import digitalbedrock.software.pbcore.controllers.MainController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        final MainController mainController = new MainController(stage);
        final BorderPane root = new BorderPane();

        root.setTop(mainController.createMenu());
        root.setCenter(FXMLLoader.load(getClass().getResource("/fxml/splash.fxml")));

        Scene scene = new Scene(root);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.setTitle("PBCore Cataloging Tool");
        stage.setOnShown(e->{mainController.initialize(e);});
        stage.show();
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
