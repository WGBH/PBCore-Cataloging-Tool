package digitalbedrock.software.pbcore.core.models.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;

public class PBCoreAttribute {

    private Long id;
    private String screenName;
    private String name;
    private boolean required;
    private String description;

    public StringProperty valueProperty = new SimpleStringProperty();
    boolean readOnly;

    public PBCoreAttribute() {
        id = System.currentTimeMillis() + UUID.randomUUID().getLeastSignificantBits();
    }

    public PBCoreAttribute(String screenName, String name, boolean required, String description, String value) {
        this();
        this.screenName = screenName;
        this.name = name;
        this.required = required;
        this.description = description;
        this.valueProperty.setValue(value);
    }


    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getValue() {
        return valueProperty.getValue();
    }

    public void setValue(String value) {
        if(!readOnly) {
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

    public String getScreenName() {
        return screenName;
    }

    public String getName() {
        return name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

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

    @Override
    public String toString() {
        return "PBCoreAttribute{" +
                "id=" + id +
                ", screenName='" + screenName + '\'' +
                ", name='" + name + '\'' +
                ", required=" + required +
                ", description='" + description + '\'' +
                ", value='" + getValue() + '\'' +
                '}';
    }


    public PBCoreAttribute copy() {
        return new PBCoreAttribute(screenName, name, required, description, getValue());
    }

}
