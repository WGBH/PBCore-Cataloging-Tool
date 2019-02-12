package digitalbedrock.software.pbcore.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.Settings;
import digitalbedrock.software.pbcore.core.models.CV;
import digitalbedrock.software.pbcore.core.models.CVBase;
import digitalbedrock.software.pbcore.core.models.CVTerm;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import digitalbedrock.software.pbcore.listeners.SavedSearchedUpdated;
import digitalbedrock.software.pbcore.lucene.LuceneEngineSearchFilter;
import digitalbedrock.software.pbcore.parsers.CSVAttributeMapper;
import digitalbedrock.software.pbcore.parsers.CSVElementMapper;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Registry implements Observer {

    private final boolean isMac;
    private final boolean isWindows;

    private Settings settings = new Settings();

    private Map<String, CV> controlledVocabularies;
    private final Map<String, PBCoreElement> pbCoreElements = new HashMap<>();
    private PBCoreElement batchEditPBCoreElement;

    private static final String ACE_EDITOR_FOLDER = "editor" + File.separator + "ace" + File.separator;
    private static final String ACE_EDITOR_HTML_FILE = "editor.html";
    private final List<SavedSearchedUpdated> savedSearchesUpdated = new ArrayList<>();

    public Registry() {
        isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        controlledVocabularies = new TreeMap<>(loadControlledVocabularies());
        verifyAndRetrieveAceEditorHtmlResourceFile();
        loadPBCoreElements();
        loadBatchEditPBCoreElement();
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
            CVTerm cvTerm = new CVTerm(term, source, version, ref);
            CV cv = controlledVocabularies.get(cvId);
            if (cv == null) {
                String[] split = cvId.split(" - ");
                cv = MainApp.getInstance().getRegistry().getControlledVocabularies().get(split[0]);
                if (cv.isHasSubs()) {
                    for (Map.Entry<String, CVBase> stringCVBaseEntry : cv.getSubs().entrySet()) {
                        if (stringCVBaseEntry.getKey().equalsIgnoreCase(split[1])) {
                            stringCVBaseEntry.getValue().getTerms().add(cvTerm);
                            break;
                        }
                    }
                }
            } else {
                cv.getTerms().add(cvTerm);
            }
            saveVocabulariesFile();
            return cvTerm;
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateVocabulary(String cvId, CVTerm selectedCVTerm) {
        CV cv = controlledVocabularies.get(cvId);
        if (cv == null) {
            String[] split = cvId.split(" - ");
            cv = MainApp.getInstance().getRegistry().getControlledVocabularies().get(split[0]);
            if (cv.isHasSubs()) {
                for (Map.Entry<String, CVBase> stringCVBaseEntry : cv.getSubs().entrySet()) {
                    if (stringCVBaseEntry.getKey().equalsIgnoreCase(split[1])) {
                        int i = stringCVBaseEntry.getValue().getTerms().indexOf(selectedCVTerm);
                        stringCVBaseEntry.getValue().getTerms().set(i, selectedCVTerm);
                        break;
                    }
                }
            }
        } else {
            int i = cv.getTerms().indexOf(selectedCVTerm);
            cv.getTerms().set(i, selectedCVTerm);
        }
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
        CV cv = controlledVocabularies.get(cvId);
        if (cv == null) {
            String[] split = cvId.split(" - ");
            cv = MainApp.getInstance().getRegistry().getControlledVocabularies().get(split[0]);
            if (cv.isHasSubs()) {
                for (Map.Entry<String, CVBase> stringCVBaseEntry : cv.getSubs().entrySet()) {
                    if (stringCVBaseEntry.getKey().equalsIgnoreCase(split[1])) {
                        stringCVBaseEntry.getValue().getTerms().remove(selectedCVTerm);
                        break;
                    }
                }
            }
        } else {
            cv.getTerms().remove(selectedCVTerm);
        }
        try {
            saveVocabulariesFile();
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveVocabulariesFile() throws IOException {
        String file = defaultDirectory() + File.separator + "cvs" + File.separator + "cvs.json";
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(file), controlledVocabularies);
    }

    private void loadPBCoreElements() {
        Map<String, String> pages = getCurrentWorkPages();
        List<Map.Entry<String, String>> pagesToRemove = new ArrayList<>();
        pages.entrySet().forEach((entry) -> {
            try {
                File file = new File(defaultDirectory() + File.separator + entry.getKey());
                if (!file.exists()) {
                    pagesToRemove.add(entry);
                } else {
                    try {
                        pbCoreElements.put(entry.getKey(), PBCoreStructure.getInstance().parseFile(file, this));
                    } catch (NullPointerException e) {
                        Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, e.getMessage());
                    }
                }
            } catch (IllegalAccessException | JAXBException ex) {
                Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pagesToRemove.forEach((entry) -> pages.remove(entry.getKey()));
        saveCurrentWorkPages(pages);
    }

    private void loadBatchEditPBCoreElement() {
        try {
            File file = new File(defaultDirectory() + File.separator + "batch-edit");
            if (file.exists()) {
                batchEditPBCoreElement = PBCoreStructure.getInstance().parseFile(file, this);
            }
        } catch (IllegalAccessException | JAXBException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PBCoreElement getBatchEditPBCoreElement() {
        return batchEditPBCoreElement;
    }

    public void removePBCoreElement(String token) {
        Map<String, String> pages = getCurrentWorkPages();
        Map<String, String> pagesFilenames = getCurrentWorkPagesFilenames();
        pages.remove(token);
        pagesFilenames.remove(token);
        File file = new File(defaultDirectory() + File.separator + token);
        if (file.exists()) {
            file.delete();
        }
        saveCurrentWorkPages(pages);
        saveCurrentWorkPagesFilenames(pagesFilenames);
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
            pbCoreElements.put(token, pbCoreElement);
        } catch (IOException | ParserConfigurationException | TransformerException ex) {
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

    private void saveSavedSearches(List<List<LuceneEngineSearchFilter>> savedSearches) {
        try {
            String file = defaultDirectory() + File.separator + "saved-searches.json";
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(file), savedSearches);
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveCurrentWorkPages(Map<String, String> pages) {
        try {
            String file = defaultDirectory() + File.separator + "work-pages-names.json";
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(file), pages);
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveCurrentWorkPagesFilenames(Map<String, String> pages) {
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
        if (savedSearches1.stream().filter(filters -> filters.stream().filter(luceneEngineSearchFilter -> Objects.equals(luceneEngineSearchFilter.getTerm(), savedSearch.get(0).getTerm())).count() > 0).count() == 0) {
            savedSearches1.add(0, savedSearch);
            saveSavedSearches(savedSearches1);
            notifySavedSearches();
        }
    }

    private void notifySavedSearches() {
        savedSearchesUpdated.forEach(SavedSearchedUpdated::onSavedSearchesUpdated);
    }

    public void addSavedSearchesListener(SavedSearchedUpdated observer) {
        if (!savedSearchesUpdated.contains(observer)) {
            savedSearchesUpdated.add(observer);
        }
    }

    public void removeSavedSearchesListener(SavedSearchedUpdated observer) {
        savedSearchesUpdated.remove(observer);
    }

    public void clearBatchEditPBCoreElement() {
        batchEditPBCoreElement = null;
        saveBatchEditPBCoreElement();
    }

    public void saveBatchEditPBCoreElement(PBCoreElement pbCoreElement) {
        this.batchEditPBCoreElement = pbCoreElement;
        saveBatchEditPBCoreElement();
    }

    private void saveBatchEditPBCoreElement() {
        File file = new File(defaultDirectory() + File.separator + "batch-edit");
        if (batchEditPBCoreElement == null) {
            file.delete();
        } else {
            try {
                PBCoreStructure.getInstance().saveFile(batchEditPBCoreElement, file);
            } catch (ParserConfigurationException | TransformerException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createNewVocabulariesAggregator(String name, boolean attribute) {
        CV cv = new CV();
        cv.setCustom(true);
        cv.setAttribute(attribute);
        getControlledVocabularies().put(name, cv);
        try {
            saveVocabulariesFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeCV(String selectedCV) {
        getControlledVocabularies().remove(selectedCV);
        try {
            saveVocabulariesFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, CV> importControlledVocabularies(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, CV> importedVocabularies = mapper.readValue(file, new TypeReference<HashMap<String, CV>>() {
        });
        HashMap<String, CV> defaultVocabularies = mapper.readValue(Thread.currentThread().getContextClassLoader().getResource("cvs.json"), new TypeReference<HashMap<String, CV>>() {
        });
        if (importedVocabularies == null) {
            return new HashMap<>();
        }
        for (Map.Entry<String, CV> stringCVEntry : importedVocabularies.entrySet()) {
            if (defaultVocabularies.containsKey(stringCVEntry.getKey())) {
                CV cv = defaultVocabularies.get(stringCVEntry.getKey());
                cv.update(stringCVEntry.getValue());
            } else {
                CV value = stringCVEntry.getValue();
                value.setCustom(true);
                value.setHasSubs(false);
                for (CVTerm cvTerm : value.getTerms()) {
                    cvTerm.setCustom(true);
                }
                defaultVocabularies.put(stringCVEntry.getKey(), value);
            }
        }
        controlledVocabularies = new TreeMap<>(defaultVocabularies);
        saveVocabulariesFile();
        return getControlledVocabularies();
    }

    public void exportControlledVocabularies(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, controlledVocabularies);
    }

    public void markFirstTimeInstructionsShown() {
        getSettings().markFirstTimeInstructionsShown();
        saveSettings();
    }

    public List<CSVElementMapper> loadMappers() {
        List<CSVElementMapper> mappers = new ArrayList<>();
        String file = defaultDirectory() + File.separator + "mappers" + File.separator + "mappers.json";
        File f = new File(file);
        if (!f.exists()) {
            try {
                new File(defaultDirectory() + File.separator + "mappers").mkdir();
                Files.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("mappers.json"), f.toPath());
            } catch (IOException ex) {
                Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            mappers = mapper.readValue(f, new TypeReference<List<CSVElementMapper>>() {
            });
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            mappers = mapper.readValue(Thread.currentThread().getContextClassLoader().getResource("mappers.json"), new TypeReference<List<CSVElementMapper>>() {
            });
        } catch (IOException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (CSVElementMapper csvElementMapper : mappers) {
            for (CSVAttributeMapper csvAttributeMapper : csvElementMapper.getAttributes()) {
                csvAttributeMapper.setElementFullPath(csvElementMapper.getElementFullPath());
            }
        }
        return mappers;
    }
}
