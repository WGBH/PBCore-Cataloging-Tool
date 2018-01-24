package digitalbedrock.software.pbcore.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.Settings;
import digitalbedrock.software.pbcore.core.models.CV;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Registry implements Observer {

    //SYSTEM properties
    private final boolean isMac;

    private final Preferences userPreferences;
    private Settings settings = new Settings();

    private final Map<String, CV> controlledVocabularies;

    private static final String ACE_EDITOR_FOLDER = "editor" + File.separator + "ace" + File.separator;
    private static String ACE_EDITOR_HTML_FILE = "editor.html";

    public Registry() throws IOException {
        isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        userPreferences = Preferences.userNodeForPackage(MainApp.class);
        ObjectMapper mapper = new ObjectMapper();
        controlledVocabularies = mapper.readValue(Thread.currentThread().getContextClassLoader().getResource("cvs.json"), new TypeReference<HashMap<String, CV>>() {
        });


//        Properties pros = System.getProperties();
//        
//        pros.entrySet().forEach((entry) -> {
//            System.out.println(entry.getKey()+" = "+entry.getValue());
//        });

        verifyAndRetrieveAceEditorHtmlResourceFile();
    }

    public static String verifyAndRetrieveAceEditorHtmlResourceFile() {
        File file = new File(System.getProperty("java.io.tmpdir") + ACE_EDITOR_FOLDER + ACE_EDITOR_HTML_FILE);
        if (!file.exists()) {
            InputStream aceEditorTxt = Thread.currentThread().getContextClassLoader().getResourceAsStream("aceeditor.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(aceEditorTxt));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    line = line.replaceAll("/", File.separator);
                    InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(line);
                    File fileToSave = new File(System.getProperty("java.io.tmpdir") + line);
                    org.apache.commons.io.FileUtils.copyInputStreamToFile(resourceAsStream, fileToSave);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
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

    public Map<String, CV> getControlledVocabularies() {
        return controlledVocabularies;
    }
}
