package digitalbedrock.software.pbcore.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends AbsController {

    @FXML
    private SettingsCrawlingController settingsCrawlingController;

    @FXML
    private SettingsVocabulariesController settingsVocabulariesController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public MenuBar createMenu() {
        final MenuBar menuBar = new MenuBar();
        return menuBar;
    }
}
