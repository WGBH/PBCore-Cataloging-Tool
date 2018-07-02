package digitalbedrock.software.pbcore.parsers;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class CSVElementMapper {
    private boolean needsParentVerification;
    private String name;
    private String elementFullPath;
    private List<CSVAttributeMapper> attributes;

    public CSVElementMapper() {
    }

    public CSVElementMapper(String name, String elementFullPath, List<CSVAttributeMapper> attributes) {
        this(name, elementFullPath, attributes, false);
    }

    public CSVElementMapper(String name, String elementFullPath, List<CSVAttributeMapper> attributes, boolean needsParentVerification) {
        this.name = name;
        this.elementFullPath = elementFullPath;
        this.attributes = attributes;
        this.needsParentVerification = needsParentVerification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElementFullPath() {
        return elementFullPath;
    }

    public void setElementFullPath(String elementFullPath) {
        this.elementFullPath = elementFullPath;
    }

    public List<CSVAttributeMapper> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<CSVAttributeMapper> attributes) {
        this.attributes = attributes;
    }

    public boolean isNeedsParentVerification() {
        return needsParentVerification;
    }

    public void setNeedsParentVerification(boolean needsParentVerification) {
        this.needsParentVerification = needsParentVerification;
    }

    @JsonIgnore
    public String getParentElementFullPath() {
        int i = elementFullPath.lastIndexOf("/");
        return elementFullPath.substring(0, i);
    }
}
