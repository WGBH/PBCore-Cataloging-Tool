package digitalbedrock.software.pbcore.lucene;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;

public class HitDocument {

    private String filename;
    private String filepath;
    private int hitsCount;
    private PBCoreElement pbCoreElement;

    public HitDocument(String filename, String filepath, int hitsCount, PBCoreElement pbCoreElement) {
        this.filename = filename;
        this.filepath = filepath;
        this.pbCoreElement = pbCoreElement;
        this.hitsCount = hitsCount;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public PBCoreElement getPbCoreElement() {
        return pbCoreElement;
    }

    public void setPbCoreElement(PBCoreElement pbCoreElement) {
        this.pbCoreElement = pbCoreElement;
    }

    public int getHitsCount() {
        return hitsCount;
    }

    public void setHitsCount(int hitsCount) {
        this.hitsCount = hitsCount;
    }
}
