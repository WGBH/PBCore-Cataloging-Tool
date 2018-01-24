package digitalbedrock.software.pbcore.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CVBase {

    protected List<CVTerm> terms = new ArrayList<>();
    protected String description = "";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CVTerm> getTerms() {
        return terms;
    }

    public void setTerms(List<CVTerm> terms) {
        this.terms = terms;
    }

    public CVBase() {
    }

}
