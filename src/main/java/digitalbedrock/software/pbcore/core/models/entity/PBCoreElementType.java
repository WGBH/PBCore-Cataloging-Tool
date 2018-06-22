package digitalbedrock.software.pbcore.core.models.entity;

public enum PBCoreElementType {
    ROOT_ELEMENT(1L, "Root Element"),
    INTELLECTUAL_CONTENT(2L, "Intellectual Content"),
    INTELLECTUAL_PROPERTY(3L, "Intellectual Property"),
    INSTANTIATION(4L, "Instantiation"),
    EXTENSION(5L, "Extension");

    private final long id;
    private final String name;

    PBCoreElementType(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
