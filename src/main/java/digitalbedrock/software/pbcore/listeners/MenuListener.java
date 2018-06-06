package digitalbedrock.software.pbcore.listeners;

public interface MenuListener {

    void menuOptionSelected(MenuOption menuOption, Object... objects);

    enum MenuOption {
        OPEN_FILE,
        NEW_DESCRIPTION_DOCUMENT,
        NEW_INSTANTIATION_DOCUMENT,
        NEW_COLLECTION,
        CONVERT_FROM_CSV,
        EXPORT_SEARCHED_FILES_TO_CSV,
        EXPORT_OPEN_FILES_TO_CSV,
        BATCH_EDIT,
        EXPORT_OPEN_FILES_TO_ZIP,
        SAVE,
        SAVE_AS,
        SAVE_AS_TEMPLATE,
        QUIT,
        NEW_SEARCH,
        SAVED_SEARCH,
        CONTROLLED_VOCABULARIES,
        DIRECTORY_CRAWLING,
        HELP,
        SELECT_CV_ELEMENT,
        SELECT_CV_ATTRIBUTE,
        SELECT_ELEMENT,
        SELECT_ATTRIBUTE,
        SELECT_SEARCH_FILTER_ELEMENTS,
        SEARCH_RESULT_SELECTED,
        ADD_ELEMENT_ANY_VALUE,
        ABOUT
    }
}
