package digitalbedrock.software.pbcore.core.models;

public enum NewDocumentType {
    DESCRIPTION_DOCUMENT("Description Document"),
    INSTANTIATION_DOCUMENT("Instantiation Document"),
    COLLECTION("Collection");

    private final String description;

    NewDocumentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
