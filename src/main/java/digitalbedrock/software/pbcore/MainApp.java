package digitalbedrock.software.pbcore;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import digitalbedrock.software.pbcore.utils.Registry;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        final Group rootgrp = new Group();
        final Scene scene = new Scene(rootgrp);
        rootgrp.getChildren().addAll(createMenu(), FXMLLoader.load(getClass().getResource("/fxml/splash.fxml")));
        stage.setTitle("PBCore Cataloging Tool");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    private MenuBar createMenu() {
        final MenuBar menuBar = new MenuBar();

        final Menu file = new Menu("File");
        final MenuItem quit = new MenuItem("Quit");
        quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.META_DOWN));
        quit.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        quit.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/imgs/pbcore-logo-small.png"), 16, 16, false, false)));
        file.getItems().addAll(quit);

        final Menu search = new Menu("Search");
        search.setDisable(true);  //temp

        final Menu settings = new Menu("Settings");
        settings.setDisable(true); //temp

        final Menu help = new Menu("Help");
        help.setDisable(true);  //temp

        menuBar.getMenus().addAll(file, search, settings, help);

        if (Registry.getInstance().isMac()) {
            menuBar.setUseSystemMenuBar(true);
        }
        return menuBar;
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
