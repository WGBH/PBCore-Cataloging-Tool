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

    private IPBCoreLayoutType typeForLayout = IPBCoreLayoutType.NORMAL;

    public abstract String getPathRepresentation();

    public abstract String getFullPath();

    public abstract String getDescription();

    public abstract boolean isRequired();

    public boolean isRepeatable() {
        return false;
    }

    public abstract String getName();

    public abstract String getScreenName();

    public boolean isHasChildElements() {
        return false;
    }

    public IPBCoreLayoutType getTypeForLayout() {
        return typeForLayout;
    }

    public void setTypeForLayout(IPBCoreLayoutType typeForLayout) {
        this.typeForLayout = typeForLayout;
    }

    public abstract boolean isAttribute();

    public abstract String getValue();

    public abstract String getType();

    public abstract int getIndex();

    public boolean isAnyElement() {
        return false;
    }
}
