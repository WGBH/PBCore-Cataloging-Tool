package digitalbedrock.software.pbcore.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CVTerm implements Comparable<CVTerm> {

    private String term;
    private String description;

    private boolean isCustom = false;
    private String source = "PBCore";
    private String version = "2.1";
    private String ref = "http://pbcore.org/pbcore-controlled-vocabularies/";

    public CVTerm() {
    }

    public CVTerm(String term, String description) {
        this.term = term;
        this.description = description;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setIsCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public int compareTo(CVTerm o) {
        return Objects.equals(this, o) ? 0 : -1;
    }
}
