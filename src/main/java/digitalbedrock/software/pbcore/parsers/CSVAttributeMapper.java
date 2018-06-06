package digitalbedrock.software.pbcore.parsers;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CSVAttributeMapper {
    private String name;
    private String attributeFullPath;
    @JsonIgnore
    private String elementFullPath;

    public CSVAttributeMapper() {
    }

    public CSVAttributeMapper(String name, String attributeFullPath) {
        this.name = name;
        this.attributeFullPath = attributeFullPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttributeFullPath() {
        return attributeFullPath;
    }

    public void setAttributeFullPath(String attributeFullPath) {
        this.attributeFullPath = attributeFullPath;
    }

    public void setElementFullPath(String elementFullPath) {
        this.elementFullPath = elementFullPath;
    }

    public String getElementFullPath() {
        return elementFullPath;
    }
}
