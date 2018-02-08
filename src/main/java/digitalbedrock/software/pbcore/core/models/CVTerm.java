package digitalbedrock.software.pbcore.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CVTerm implements Comparable<CVTerm> {

    private final StringProperty term = new SimpleStringProperty();
    private String description;

    private boolean isCustom = false;
    private final StringProperty source = new SimpleStringProperty("PBCore");
    private final StringProperty version = new SimpleStringProperty("2.1");
    private final StringProperty ref = new SimpleStringProperty("http://pbcore.org/pbcore-controlled-vocabularies/");

    public CVTerm() {
    }

    public CVTerm(String term, String description, boolean isCustom, String source, String version, String ref) {
        this.term.setValue(term);
        this.description = description;
        this.isCustom = isCustom;
        this.source.setValue(source);
        this.version.setValue(version);
        this.ref.setValue(ref);
    }

    public String getTerm() {
        return term.get();
    }

    public StringProperty termProperty() {
        return term;
    }

    public void setTerm(String term) {
        this.term.set(term);
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    public String getSource() {
        return source.get();
    }

    public StringProperty sourceProperty() {
        return source;
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public String getVersion() {
        return version.get();
    }

    public StringProperty versionProperty() {
        return version;
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public String getRef() {
        return ref.get();
    }

    public StringProperty refProperty() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref.set(ref);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(CVTerm o) {
        return Objects.equals(this, o) ? 0 : -1;
    }

    public void update(String term, String source, String version, String ref) {
        setTerm(term);
        setSource(source);
        setVersion(version);
        setRef(ref);
    }
}
