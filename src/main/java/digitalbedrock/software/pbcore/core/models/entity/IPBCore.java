package digitalbedrock.software.pbcore.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PBCoreElement.class, name = "PBCoreElement")
    ,
        @JsonSubTypes.Type(value = PBCoreAttribute.class, name = "PBCoreAttribute")
})
public abstract class IPBCore {

    public abstract String getPathRepresentation();

    public abstract String getFullPath();

    public abstract String getDescription();

    public abstract boolean isRequired();

    public boolean isRepeatable() {
        return false;
    }

    public abstract String getScreenName();

    public boolean isHasChildElements() {
        return false;
    }

    public abstract boolean isAttribute();

    public abstract String getValue();

    public abstract String getType();
}
