package digitalbedrock.software.pbcore.lucene;

import digitalbedrock.software.pbcore.MainApp;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LuceneEngine {

    private static final String SCREEN_NAME = "screenname";
    private static final String VALUE = "value";
    private static final String COMPOSED_VALUE = "composed_value";
    private static final String FILEPATH = "filepath";
    private static final String FILENAME = "filename";
    private static final String BASE_DIR_PATH = "base_dir_path";

    public LuceneEngine() {
        BooleanQuery.setMaxClauseCount(10000);
    }

    public static void createOrUpdateIndexesForFile(IndexWriter writer, File file1, String folder) {
        try {
            PBCoreElement pbCoreElement = PBCoreStructure.getInstance().parseFile(file1);
            saveDocumentForElement(writer, file1, pbCoreElement, folder);
        } catch (IOException | JAXBException | IllegalAccessException ex) {
        }
    }

    public static void deleteIndexesForFile(IndexWriter writer, String folderPath) {
        try {
            writer.deleteDocuments(new TermQuery(new Term(BASE_DIR_PATH, folderPath.toLowerCase())));
            writer.commit();
        } catch (IOException ex) {
        }
    }

    public static void clearIndexes() {
        try {
            String folder = MainApp.getInstance().getRegistry().defaultDirectory() + File.separator + "index";
            try (IndexWriter writer = new IndexWriter(FSDirectory.open(new File(folder).toPath()), new IndexWriterConfig(new KeywordAnalyzer()))) {
                writer.deleteAll();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveDocumentForElement(IndexWriter writer, File file, PBCoreElement pbCoreElement, String folder) throws IOException {
        List<LuceneEngineIndexerHelper> indexerHelpers = new ArrayList<>();
        fillDocuments(indexerHelpers, pbCoreElement, file);
        Document doc = new Document();
        boolean first = true;
        for (LuceneEngineIndexerHelper indexerHelper : indexerHelpers) {
            if (first) {
                doc.add(new StringField(FILENAME.toLowerCase(), indexerHelper.getFilename().toLowerCase(), Field.Store.YES));
                doc.add(new StringField(FILEPATH, indexerHelper.getFilepath().toLowerCase(), Field.Store.YES));
                doc.add(new StringField(BASE_DIR_PATH, folder.toLowerCase(), Field.Store.YES));
                first = false;
            }
            doc.add(new StringField(SCREEN_NAME.toLowerCase(), indexerHelper.getName().toLowerCase(), Field.Store.YES));
            doc.add(new StringField(VALUE.toLowerCase(), indexerHelper.getValue(), Field.Store.YES));
            doc.add(new StringField(COMPOSED_VALUE, indexerHelper.getFullPath().toLowerCase() + "|" + indexerHelper.getValue().toLowerCase(), Field.Store.YES));
        }
        writer.addDocument(doc);
    }

    private static void fillDocuments(List<LuceneEngineIndexerHelper> indexerHelpers, PBCoreElement pbCoreElement, File file) {
        indexerHelpers.add(new LuceneEngineIndexerHelper(pbCoreElement.getName(), pbCoreElement.getValue(), file.getName(), file.getAbsolutePath(), pbCoreElement.getPathRepresentation(), pbCoreElement.getFullPath()));
        pbCoreElement.getSubElements().forEach((coreElement) -> {
            fillDocuments(indexerHelpers, coreElement, file);
        });
        pbCoreElement.getAttributes().forEach((coreAttribute) -> indexerHelpers.add(new LuceneEngineIndexerHelper(coreAttribute.getName(), coreAttribute.getValue(), file.getName(), file.getAbsolutePath(), pbCoreElement.getPathRepresentation(), pbCoreElement.getFullPath())));
    }

    public Map.Entry<Long, List<HitDocument>> search(List<LuceneEngineSearchFilter> andOperators, int offset, @SuppressWarnings("SameParameterValue") int maxResults) {

        long totalResults = 0;
        List<HitDocument> documents = new ArrayList<>();
        try {
            String folder = MainApp.getInstance().getRegistry().defaultDirectory() + File.separator + "index";

            IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(folder).toPath()));
            IndexSearcher searcher = new IndexSearcher(reader);
            ScoreDoc[] hits;
            if (andOperators.isEmpty()) {
                TopScoreDocCollector collector = TopScoreDocCollector.create(maxResults);
                searcher.search(new MatchAllDocsQuery(), collector);
                TopDocs topDocs = collector.topDocs(offset, maxResults);
                hits = topDocs.scoreDocs;
                totalResults = topDocs.totalHits;
            } else {
                BooleanQuery.Builder builder = new BooleanQuery.Builder();
                andOperators.forEach(luceneEngineSearchFilter -> {
                    if (luceneEngineSearchFilter.isAllElements()) {
                        builder.add(new WildcardQuery(new Term(COMPOSED_VALUE, ("*" + luceneEngineSearchFilter.getTerm() + "*").toLowerCase())), BooleanClause.Occur.MUST);
                    } else {
                        BooleanQuery.Builder insideQuery = new BooleanQuery.Builder();
                        luceneEngineSearchFilter.getFieldsToSearch().forEach(ipbCore
                                -> insideQuery.add(new WildcardQuery(new Term(COMPOSED_VALUE, ("*" + ipbCore.getFullPath().replace("/", "\\/") + "|" + luceneEngineSearchFilter.getTerm() + "*").toLowerCase())), BooleanClause.Occur.SHOULD));
                        builder.add(insideQuery.build(), BooleanClause.Occur.MUST);
                    }
                });
                TopScoreDocCollector collector = TopScoreDocCollector.create(100000);
                searcher.search(builder.build(), collector);
                TopDocs topDocs = collector.topDocs(offset * maxResults, maxResults);
                hits = topDocs.scoreDocs;
                totalResults = topDocs.totalHits;
            }
            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);
                String filename = doc.get(FILENAME);
                String filepath = doc.get(FILEPATH);
                File file = new File(filepath);
                if (file.exists()) {
                    HitDocument hitDoc = new HitDocument(
                            filename,
                            filepath,
                            PBCoreStructure.getInstance().parseFile(file)
                    );
                    documents.add(hitDoc);
                }
            }

        } catch (IOException | IllegalAccessException | JAXBException e) {
            e.printStackTrace();
        }
        return new AbstractMap.SimpleEntry<>(totalResults, documents);
    }
}
