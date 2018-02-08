package digitalbedrock.software.pbcore.listeners;

import digitalbedrock.software.pbcore.lucene.LuceneEngineSearchFilter;

public interface SearchFilterElementsSelectionListener {

    void onFiltersDefined(int index, LuceneEngineSearchFilter luceneEngineSearchFilter);
}
