package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.listeners.MenuListener;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;

public abstract class AbsController implements MenuListener, Initializable {

    @Override
    public void menuOptionSelected(MenuOption menuOption, Object... objects) {
        MainApp.getInstance().goTo(menuOption, objects);
    }

    public abstract MenuBar createMenu();

    public void onShown() {
    }
}
