package digitalbedrock.software.pbcore.controllers;

public abstract class AbsController {

    protected MainController mainController = null;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
