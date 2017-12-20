package digitalbedrock.software.pbcore.utils;

import digitalbedrock.software.pbcore.MainApp;
import java.util.prefs.Preferences;

public class Registry {

    private static final Registry INSTANCE = new Registry();
    private final boolean isMac;
    private final Preferences userPreferences;
    

    private Registry() {
        isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        userPreferences=Preferences.userNodeForPackage(MainApp.class);
    }

    public static Registry getInstance() {
        return INSTANCE;
    }

    public boolean isMac() {
        return isMac;
    }
}
