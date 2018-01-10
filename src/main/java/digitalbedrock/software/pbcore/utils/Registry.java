package digitalbedrock.software.pbcore.utils;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.Settings;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Registry implements Observer {

    private final boolean isMac;

    private final Preferences userPreferences;
    private Settings settings = new Settings();

    public Registry() {
        isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        userPreferences = Preferences.userNodeForPackage(MainApp.class);
    }

    public void loadSavedSettings() {
        byte[] buff = userPreferences.getByteArray("settings", null);
        if (buff != null) {
            try {
                ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buff));
                settings = (Settings) in.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                settings = new Settings();
            }
        }
        settings.addObserver(this);
    }

    private void saveSettings() {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        try {
            ObjectOutputStream os = new ObjectOutputStream(ba);
            os.writeObject(settings);
            os.close();
            userPreferences.putByteArray("settings", ba.toByteArray());
            userPreferences.sync();
        } catch (IOException | BackingStoreException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isMac() {
        return isMac;
    }

    public Settings getSettings() {
        return settings;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Settings) {
            saveSettings();
        }
    }
}
