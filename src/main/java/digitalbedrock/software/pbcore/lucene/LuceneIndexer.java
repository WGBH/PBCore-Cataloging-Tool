package digitalbedrock.software.pbcore.lucene;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.Settings;
import digitalbedrock.software.pbcore.core.models.FolderModel;
import digitalbedrock.software.pbcore.utils.Registry;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LuceneIndexer {

    private final ObservableList<String> foldersToProcess = FXCollections.observableArrayList();
    private final StringProperty currentFolder = new SimpleStringProperty();
    private static LuceneIndexer instance;
    private IndexWriter indexWriter;

    private final LongProperty filesInFolder = new SimpleLongProperty();
    private LuceneIndexerService luceneIndexerService;

    private LuceneIndexer() {
        try {
            String folder = MainApp.getInstance().getRegistry().defaultDirectory() + File.separator + "index";
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            indexWriter = new IndexWriter(FSDirectory.open(new File(folder).toPath()), indexWriterConfig);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex.getMessage());
        }
        currentFolder.addListener((observable, oldValue, newValue) -> {
            Registry registry = MainApp.getInstance().getRegistry();
            Settings settings = registry.getSettings();
            if (oldValue != null) {
                FolderModel completedFolderModel = settings.getFolders().stream().filter(folderModel -> Objects.equals(folderModel.getFolderPath(), oldValue)).findFirst().orElse(null);
                completedFolderModel.setTotalValidFiles(filesInFolder.get());
                completedFolderModel.setDateLastIndexing(LocalDateTime.now());
                completedFolderModel.setIndexing(false);
                completedFolderModel.setScheduled(false);
                settings.updateFolder(completedFolderModel);
            }
            if (newValue != null) {
                FolderModel folderModelToIndex = settings.getFolders().stream().filter(folderModel -> Objects.equals(folderModel.getFolderPath(), newValue)).findFirst().orElse(null);
                folderModelToIndex.setTotalValidFiles(0);
                folderModelToIndex.setIndexing(true);
                folderModelToIndex.setScheduled(false);
                MainApp.getInstance().getRegistry().getSettings().updateFolder(folderModelToIndex);
            }
        });
        filesInFolder.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            Registry registry = MainApp.getInstance().getRegistry();
            Settings settings = registry.getSettings();
            FolderModel fm = settings.getFolders().stream().filter(folderModel -> Objects.equals(folderModel.getFolderPath(), currentFolder.getValue())).findFirst().orElse(null);
            if (fm != null) {
                fm.setTotalValidFiles(newValue.longValue());
                MainApp.getInstance().getRegistry().getSettings().updateFolder(fm);
            }
        }));
    }

    public static LuceneIndexer getInstance() {
        if (instance == null) {
            instance = new LuceneIndexer();
        }
        return instance;
    }

    public boolean startFolderIndexing(String folder) {
        boolean scheduled = false;
        if (!isProcessingFolder(folder) && !isScheduledFolder(folder)) {
            foldersToProcess.add(folder);
            if (currentFolder.get() == null) {
                processFolder();
            } else {
                scheduled = true;
            }
        }
        return scheduled;
    }

    private void processFolder() {
        if (foldersToProcess.isEmpty()) {
            return;
        }
        if (luceneIndexerService != null && luceneIndexerService.isRunning()) {
            luceneIndexerService.cancel();
        }
        luceneIndexerService = new LuceneIndexerService();
        luceneIndexerService.start();
    }

    public boolean isProcessingFolder(String folder) {
        return Objects.equals(currentFolder.get(), folder);
    }

    public boolean isScheduledFolder(String folder) {
        return foldersToProcess.contains(folder);
    }

    public void stopIndexingForFolder(String folderPath) {
        if (Objects.equals(currentFolder.get(), folderPath)) {

            Registry registry = MainApp.getInstance().getRegistry();
            Settings settings = registry.getSettings();
            FolderModel fm = settings.getFolders().stream().filter(folderModel -> Objects.equals(folderModel.getFolderPath(), currentFolder.getValue())).findFirst().orElse(null);
            fm.setIndexing(false);
            fm.setScheduled(false);
            MainApp.getInstance().getRegistry().getSettings().updateFolder(fm);

            currentFolder.set(null);
            if (luceneIndexerService != null && luceneIndexerService.isRunning()) {
                luceneIndexerService.cancel();
            }
            processFolder();
        } else if (foldersToProcess.contains(folderPath)) {
            foldersToProcess.remove(folderPath);
        }
    }

    public void deleteDocsForFolder(String folderPath) {
        LuceneEngine.deleteIndexesForFile(indexWriter, folderPath);
    }

    class LuceneIndexerService extends Service<String> {

        final int MAX_THREADS = 20;
        private final ExecutorService exec;
        private final AtomicBoolean processing = new AtomicBoolean();
        private final AtomicInteger currentFilesCount = new AtomicInteger();
        private final AtomicInteger currentFolderFilesProcessed = new AtomicInteger();

        public LuceneIndexerService() {
            this.processing.set(false);
            this.exec = Executors.newFixedThreadPool(MAX_THREADS, r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            });
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call()
                        throws IOException {
                    if (processing.get()) {
                        return null;
                    }
                    processing.set(true);
                    currentFolderFilesProcessed.set(0);
                    filesInFolder.set(0);
                    currentFilesCount.set(0);
                    currentFolder.set(foldersToProcess.remove(0));
                    LuceneIndexer.getInstance().deleteDocsForFolder(currentFolder.get());
                    File file = new File(currentFolder.get());
                    listFiles(file.getAbsolutePath(), file.getAbsolutePath());
                    processing.set(false);
                    if (currentFilesCount.get() == 0) {
                        currentFolder.set(null);
                        processFolder();
                    }
                    return null;
                }
            };

        }

        @Override
        protected void cancelled() {
            super.cancelled();
            if (exec != null) {
                exec.shutdown();
                try {
                    if (!exec.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                        exec.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    exec.shutdownNow();
                }
            }
        }

        void listFiles(String baseDirectory, String filePath) {
            try {
                Files.walkFileTree(Paths.get(filePath), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException io) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (!Objects.equals(baseDirectory, currentFolder.getValue())) {
                            return FileVisitResult.TERMINATE;
                        }
                        if (!attrs.isDirectory() && file.getFileName().normalize().toString().endsWith(".xml")) {
                            String s = file.normalize().toString();
                            LuceneIndexingTask luceneIndexingTask = new LuceneIndexingTask(indexWriter, currentFolder.get(), s);
                            currentFilesCount.addAndGet(1);
                            EventHandler<WorkerStateEvent> eventHandler = event -> {
                                if (!Objects.equals(baseDirectory, currentFolder.getValue())) {
                                    return;
                                }
                                if (luceneIndexingTask.getValue() != null && luceneIndexingTask.getValue()) {
                                    filesInFolder.setValue(currentFolderFilesProcessed.incrementAndGet());
                                }
                                currentFilesCount.decrementAndGet();
                                if (!processing.get() && currentFilesCount.get() == 0) {
                                    currentFolder.set(null);
                                    processFolder();
                                }

                            };
                            luceneIndexingTask.setOnFailed(eventHandler);
                            luceneIndexingTask.setOnSucceeded(eventHandler);
                            try {
                                exec.submit(luceneIndexingTask);
                            } catch (RejectedExecutionException ex) {
                                Logger.getLogger(LuceneIndexer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCurrentFolder() {
        return currentFolder.get();
    }

    public ObservableList<String> getFoldersToProcess() {
        return foldersToProcess;
    }
}
