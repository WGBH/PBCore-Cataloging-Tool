package digitalbedrock.software.pbcore.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class PBCoreDummyItemsElement extends IPBCore implements Serializable {

    @JsonIgnore
    private int index;

    @Override
    public IPBCoreLayoutType getTypeForLayout() {
        return IPBCoreLayoutType.DUMMY;
    }

    @Override
    public String getPathRepresentation() {
        return null;
    }

    @Override
    public String getFullPath() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public boolean isAttribute() {
        return false;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
