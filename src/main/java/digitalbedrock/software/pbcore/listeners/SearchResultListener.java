package digitalbedrock.software.pbcore.listeners;

import digitalbedrock.software.pbcore.lucene.HitDocument;

public interface SearchResultListener {

    void searchResultSelected(HitDocument hitDocument);
}
