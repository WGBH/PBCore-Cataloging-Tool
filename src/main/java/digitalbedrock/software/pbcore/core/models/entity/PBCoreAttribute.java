package digitalbedrock.software.pbcore.core.models.entity;

public class PBCoreAttribute {

    private Long id;
    private String screenName;
    private String name;
    private boolean required;
    private String description;
    private String value;

    public PBCoreAttribute() {
    }

    public PBCoreAttribute(Long id, String screenName, String name) {
        this.id = id;
        this.screenName = screenName;
        this.name = name;
    }

    public PBCoreAttribute(Long id, String screenName, String name, boolean required, String description, String value) {
        this.id = id;
        this.screenName = screenName;
        this.name = name;
        this.required = required;
        this.description = description;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PBCoreAttribute{" +
                "id=" + id +
                ", screenName='" + screenName + '\'' +
                ", name='" + name + '\'' +
                ", required=" + required +
                ", description='" + description + '\'' +
                ", value='" + value + '\'' +
                '}';
    }


    @Override
    public PBCoreAttribute clone() {
        return new PBCoreAttribute(id, screenName, name, required, description, value);
    }
}
