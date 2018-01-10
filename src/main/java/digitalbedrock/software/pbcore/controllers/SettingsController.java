package digitalbedrock.software.pbcore.controllers;

import javafx.fxml.FXML;

public class SettingsController extends AbsController {

    @FXML
    private SettingsCrawlingController settingsCrawlingController;

    @FXML
    private SettingsVocabulariesController settingsVocabulariesController;

    @Override
    public void setMainController(MainController mainController) {
        settingsCrawlingController.setMainController(mainController);
        settingsVocabulariesController.setMainController(mainController);
    }

}
