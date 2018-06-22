package digitalbedrock.software.pbcore.lucene;

import digitalbedrock.software.pbcore.MainApp;
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
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This indexer is intended to index a single file at a time
 */
public class LuceneFileIndexer {

    private final ObservableList<String> filesToProcess = FXCollections.observableArrayList();
    private final StringProperty currentFile = new SimpleStringProperty();
    private static LuceneFileIndexer instance;
    private IndexWriter indexWriter;

    private LuceneFileIndexer.LuceneIndexerService luceneIndexerService;


    private LuceneFileIndexer() {
        try {
            String folder = MainApp.getInstance().getRegistry().defaultDirectory() + File.separator + "index";
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            indexWriter = new IndexWriter(FSDirectory.open(new File(folder).toPath()), indexWriterConfig);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    public static LuceneFileIndexer getInstance() {
        if (instance == null) {
            instance = new LuceneFileIndexer();
        }
        return instance;
    }

    public boolean startFileIndexing(String file) {
        boolean scheduled = false;
        if (!isProcessingFile(file) && !isScheduledFile(file) && MainApp.getInstance().getRegistry().getSettings().isFileInFolder(file)) {
            filesToProcess.add(file);
            if (currentFile.get() == null) {
                processFile();
            } else {
                scheduled = true;
            }
        }
        return scheduled;
    }

    private void processFile() {
        if (filesToProcess.isEmpty()) {
            return;
        }
        if (luceneIndexerService != null && luceneIndexerService.isRunning()) {
            luceneIndexerService.cancel();
        }
        luceneIndexerService = new LuceneIndexerService();
        luceneIndexerService.start();
    }

    public boolean isProcessingFile(String folder) {
        return Objects.equals(currentFile.get(), folder);
    }

    public boolean isScheduledFile(String folder) {
        return filesToProcess.contains(folder);
    }

    public void deleteDocsForFile(String filePath) {
        LuceneEngine.deleteIndexesForFile(indexWriter, filePath);
    }

    class LuceneIndexerService extends Service<String> {

        final int MAX_THREADS = 20;
        private final ExecutorService exec;
        private final AtomicBoolean processing = new AtomicBoolean();

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
                    currentFile.set(filesToProcess.remove(0));
                    LuceneFileIndexer.getInstance().deleteDocsForFile(currentFile.get());
                    File file = new File(currentFile.get());
                    listFiles(file.getAbsolutePath(), file.getAbsolutePath());
                    processing.set(false);
                    currentFile.set(null);
                    processFile();
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
                        if (!Objects.equals(baseDirectory, currentFile.getValue())) {
                            return FileVisitResult.TERMINATE;
                        }
                        if (!attrs.isDirectory() && file.getFileName().normalize().toString().endsWith(".xml")) {
                            String s = file.normalize().toString();
                            LuceneIndexingTask luceneIndexingTask = new LuceneIndexingTask(indexWriter, currentFile.get(), s);
                            EventHandler<WorkerStateEvent> eventHandler = event -> {
                                if (!Objects.equals(baseDirectory, currentFile.getValue())) {
                                    return;
                                }
                                if (!processing.get()) {
                                    currentFile.set(null);
                                    processFile();
                                }

                            };
                            luceneIndexingTask.setOnFailed(eventHandler);
                            luceneIndexingTask.setOnSucceeded(eventHandler);
                            try {
                                exec.submit(luceneIndexingTask);
                            } catch (RejectedExecutionException ex) {
                                Logger.getLogger(LuceneFileIndexer.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getCurrentFile() {
        return currentFile.get();
    }

    public ObservableList<String> getFilesToProcess() {
        return filesToProcess;
    }
}
