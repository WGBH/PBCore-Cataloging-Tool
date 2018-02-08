package digitalbedrock.software.pbcore.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import digitalbedrock.software.pbcore.core.Settings;
import digitalbedrock.software.pbcore.core.models.CV;
import digitalbedrock.software.pbcore.core.models.CVTerm;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import digitalbedrock.software.pbcore.listeners.SavedSearchedUpdated;
import digitalbedrock.software.pbcore.lucene.LuceneEngineSearchFilter;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;

public class Registry implements Observer {

    private final boolean isMac;
    private final boolean isWindows;

    private Settings settings = new Settings();

    private final Map<String, CV> controlledVocabularies;
    private Map<String, PBCoreElement> pbCoreElements = new HashMap<>();

    private static final String ACE_EDITOR_FOLDER = "editor" + File.separator + "ace" + File.separator;
    private static final String ACE_EDITOR_HTML_FILE = "editor.html";
    private List<SavedSearchedUpdated> savedSearchesUpdated = new ArrayList<>();

    public Registry() throws IOException {
        isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        controlledVocabularies = loadControlledVocabularies();
        verifyAndRetrieveAceEditorHtmlResourceFile();
        try {
            loadPBCoreElements();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    public String defaultDirectory() {
        String file;
        if (isWindows) {
            file = System.getenv("APPDATA") + "/PBCore";
        } else {
            file = System.getProperty("user.home") + "/.pbcore";
        }
        File f = new File(file);
        if (!f.exists()) {
            f.mkdirs();
        }
        return file;
    }

    public static String verifyAndRetrieveAceEditorHtmlResourceFile() {
        File file = new File(System.getProperty("java.io.tmpdir") + ACE_EDITOR_FOLDER + ACE_EDITOR_HTML_FILE);
        if (!file.exists()) {
            InputStream aceEditorTxt = Thread.currentThread().getContextClassLoader().getResourceAsStream("aceeditor.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(aceEditorTxt));
            String line;
            try {
                while ((line = br.readLine()) != null) {
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

    private Map<String, CV> loadControlledVocabularies() {
        String file = defaultDirectory() + File.separator + "cvs" + File.separator + "cvs.json";
        File f = new File(file);
        if (!f.exists()) {
            try {
                new File(defaultDirectory() + File.separator + "cvs").mkdir();
                Files.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("cvs.json"), f.toPath());
            } catch (IOException ex) {
                Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(f, new TypeReference<HashMap<String, CV>>() {
            });
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            return mapper.readValue(Thread.currentThread().getContextClassLoader().getResource("cvs.json"), new TypeReference<HashMap<String, CV>>() {
            });
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
            return new HashMap<>();
        }
    }

    public CVTerm saveVocabulary(String cvId, String term, String source, String version, String ref) {
        try {
            CVTerm cvTerm = new CVTerm(term, "", true, source, version, ref);
            controlledVocabularies.get(cvId).getTerms().add(cvTerm);
            saveVocabulariesFile();
            return cvTerm;
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateVocabulary(String cvId, CVTerm selectedCVTerm) {
        int i = controlledVocabularies.get(cvId).getTerms().indexOf(selectedCVTerm);
        CVTerm cvTerm = controlledVocabularies.get(cvId).getTerms().set(i, selectedCVTerm);
        System.out.println(cvTerm);
        try {
            saveVocabulariesFile();
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteVocabulary(String cvId, CVTerm selectedCVTerm) {
        if (!selectedCVTerm.isCustom()) {
            return;
        }
        controlledVocabularies.get(cvId).getTerms().remove(selectedCVTerm);
        try {
            saveVocabulariesFile();
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void saveVocabulariesFile() throws IOException {
        String file = defaultDirectory() + File.separator + "cvs" + File.separator + "cvs.json";
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(file), controlledVocabularies);
    }

    private void loadPBCoreElements() throws BackingStoreException, JsonProcessingException {
        Map<String, String> pages = getCurrentWorkPages();
        List<Map.Entry<String, String>> pagesToRemove = new ArrayList<>();
        pages.entrySet().forEach((entry) -> {
            try {
                File file = new File(defaultDirectory() + File.separator + entry.getKey());
                if (!file.exists()) {
                    pagesToRemove.add(entry);
                } else {
                    pbCoreElements.put(entry.getKey(), PBCoreStructure.getInstance().parseFile(file, this));
                }
            } catch (IllegalAccessException | JAXBException ex) {
                Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pagesToRemove.forEach((entry) -> {
            pages.remove(entry.getKey());
        });
        saveCurrentWorkPages(pages);
    }

    public void removePBCoreElement(String token) {
        Map<String, String> pages = getCurrentWorkPages();
        Map<String, String> pagesFilenames = getCurrentWorkPagesFilenames();
        pages.remove(token);
        pagesFilenames.remove(token);
        try {
            File file = new File(defaultDirectory() + File.separator + token);
            if (file.exists()) {
                file.delete();
            }
            saveCurrentWorkPages(pages);
            saveCurrentWorkPagesFilenames(pagesFilenames);
        } catch (IOException | BackingStoreException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void savePBCoreElement(String token, String currentId, File f, PBCoreElement pbCoreElement) {
        Map<String, String> pages = getCurrentWorkPages();
        Map<String, String> pagesFilenames = getCurrentWorkPagesFilenames();
        pages.put(token, f != null ? f.getName() : currentId);
        pagesFilenames.put(token, f != null ? f.getAbsolutePath() : null);
        try {
            String file = defaultDirectory() + File.separator + token;
            PBCoreStructure.getInstance().saveFile(pbCoreElement, new File(file));
            saveCurrentWorkPages(pages);
            saveCurrentWorkPagesFilenames(pagesFilenames);
        } catch (IOException | BackingStoreException | ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<String, PBCoreElement> getPbCoreElements() {
        return pbCoreElements;
    }

    public void loadSavedSettings() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String file = defaultDirectory() + File.separator + "settings.json";
        File file1 = new File(file);
        if (file1.exists()) {
            try {
                settings = mapper.readValue(file1, new TypeReference<Settings>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
                settings = new Settings();
            }
        } else {
            settings = new Settings();
        }
        settings.addObserver(this);
    }

    private void saveSettings() {
        try {
            String file = defaultDirectory() + File.separator + "settings.json";
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.writeValue(new File(file), settings);
        } catch (IOException ex) {
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

    public List<List<LuceneEngineSearchFilter>> getSavedSearches() {
        ObjectMapper mapper = new ObjectMapper();
        String file = defaultDirectory() + File.separator + "saved-searches.json";
        File file1 = new File(file);
        if (file1.exists()) {
            try {
                return mapper.readValue(file1, new TypeReference<List<List<LuceneEngineSearchFilter>>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    public Map<String, String> getCurrentWorkPages() {
        ObjectMapper mapper = new ObjectMapper();
        String file = defaultDirectory() + File.separator + "work-pages-names.json";
        File file1 = new File(file);
        if (file1.exists()) {
            try {
                return mapper.readValue(file1, new TypeReference<Map<String, String>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>();
    }

    public Map<String, String> getCurrentWorkPagesFilenames() {
        ObjectMapper mapper = new ObjectMapper();
        String file = defaultDirectory() + File.separator + "work-pages-filenames.json";
        File file1 = new File(file);
        if (file1.exists()) {
            try {
                return mapper.readValue(file1, new TypeReference<Map<String, String>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>();
    }

    public void saveSavedSearches(List<List<LuceneEngineSearchFilter>> savedSearches) throws BackingStoreException, JsonProcessingException {
        try {
            String file = defaultDirectory() + File.separator + "saved-searches.json";
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(file), savedSearches);
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveCurrentWorkPages(Map<String, String> pages) throws BackingStoreException, JsonProcessingException {
        try {
            String file = defaultDirectory() + File.separator + "work-pages-names.json";
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(file), pages);
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveCurrentWorkPagesFilenames(Map<String, String> pages) throws BackingStoreException, JsonProcessingException {
        try {
            String file = defaultDirectory() + File.separator + "work-pages-filenames.json";
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(file), pages);
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addRecentSearch(List<LuceneEngineSearchFilter> savedSearch) {
        List<List<LuceneEngineSearchFilter>> savedSearches1 = getSavedSearches();
        if (savedSearches1.size() == 10) {
            savedSearches1.remove(savedSearches1.size() - 1);
        }
        savedSearches1.add(0, savedSearch);
        try {
            saveSavedSearches(savedSearches1);
            notifySavedSearches();
        } catch (BackingStoreException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void notifySavedSearches() {
        savedSearchesUpdated.forEach((observer) -> {
            observer.onSavedSearchesUpdated();
        });
    }

    public void addSavedSearchesListener(SavedSearchedUpdated observer) {
        if (!savedSearchesUpdated.contains(observer)) {
            savedSearchesUpdated.add(observer);
        }
    }

    public void removeSavedSearchesListener(SavedSearchedUpdated observer) {
        savedSearchesUpdated.remove(observer);
    }
}
