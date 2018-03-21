package digitalbedrock.software.pbcore.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PBCoreAttribute extends IPBCore implements Serializable {

    private Long id;
    private String fullPath;
    private String screenName;
    private String name;
    private String tooltip;
    private boolean required;
    private String description;
    @JsonIgnore
    public final StringProperty valueProperty = new SimpleStringProperty();
    private boolean readOnly;
    @JsonIgnore
    private int index;

    public PBCoreAttribute() {
        id = System.currentTimeMillis() + UUID.randomUUID().getLeastSignificantBits();
    }

    public PBCoreAttribute(String fullPath, String screenName, String name, String tooltip, boolean required, String description, String value, boolean readOnly) {
        this();
        this.fullPath = fullPath;
        this.screenName = screenName;
        this.name = name;
        this.required = required;
        this.description = description;
        this.valueProperty.setValue(value);
        this.readOnly = readOnly;
        this.tooltip = tooltip;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public String getValue() {
        return valueProperty.getValue();
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }

    public void setValue(String value) {
        if (!readOnly) {
            this.valueProperty.setValue(value);
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public boolean isAttribute() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void updateAttributeContext(boolean required, String description) {
        this.required = required;
        this.description = description;
    }

    public PBCoreAttribute copy() {
        return new PBCoreAttribute(fullPath, screenName, name, tooltip, required, description, getValue(), readOnly);
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    @Override
    public String getPathRepresentation() {
        return getScreenName();
    }

    @Override
    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
