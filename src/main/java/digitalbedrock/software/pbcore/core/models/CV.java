package digitalbedrock.software.pbcore.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CV extends CVBase {

    private boolean isAttribute;
    private boolean hasSubs;
    private String bestPractice = "";

    private HashMap<String, CVBase> subs = new HashMap<>();

    public boolean isAttribute() {
        return isAttribute;
    }

    public void setIsAttribute(boolean isAttribute) {
        this.isAttribute = isAttribute;
    }

    public boolean isHasSubs() {
        return hasSubs;
    }

    public void setHasSubs(boolean hasSubs) {
        this.hasSubs = hasSubs;
    }

    public String getBestPractice() {
        return bestPractice;
    }

    public void setBestPractice(String bestPractice) {
        this.bestPractice = bestPractice;
    }

    public HashMap<String, CVBase> getSubs() {
        return subs;
    }

    public void setSubs(HashMap<String, CVBase> subs) {
        this.subs = subs;
    }

    public CV() {
        super();
    }
}
