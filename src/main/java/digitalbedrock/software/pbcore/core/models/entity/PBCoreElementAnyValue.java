package digitalbedrock.software.pbcore.core.models.entity;

import java.io.Serializable;
import java.util.UUID;

public class PBCoreElementAnyValue implements Serializable {

    private final long id;
    public String value = "";

    public PBCoreElementAnyValue() {
        id = System.currentTimeMillis() + UUID.randomUUID().getLeastSignificantBits();
    }

    public PBCoreElementAnyValue(String s) {
        this();
        value = s;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
