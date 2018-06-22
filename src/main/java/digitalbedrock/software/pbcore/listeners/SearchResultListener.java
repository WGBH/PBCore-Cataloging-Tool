package digitalbedrock.software.pbcore.listeners;

import digitalbedrock.software.pbcore.lucene.HitDocument;

import java.util.List;

public interface SearchResultListener {

    void searchResultSelected(List<HitDocument> hitDocuments);
}
