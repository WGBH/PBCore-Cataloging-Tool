package digitalbedrock.software.pbcore.lucene;

public class LuceneEngineIndexerHelper {

    private String name;
    private String value;
    private String filename;
    private String filepath;
    private String fullPath;
    private String pathRepresentation;

    public LuceneEngineIndexerHelper(String name, String value, String filename, String filepath, String pathRepresentation, String fullPath) {
        this.name = name;
        this.value = value;
        this.filename = filename;
        this.filepath = filepath;
        this.pathRepresentation = pathRepresentation;
        this.fullPath = fullPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getPathRepresentation() {
        return pathRepresentation;
    }

    public void setPathRepresentation(String pathRepresentation) {
        this.pathRepresentation = pathRepresentation;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
