package digitalbedrock.software.pbcore.listeners;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreAttribute;

public interface AttributeSelectionListener {

    void onAttributeSelected(PBCoreAttribute pbCoreAttribute, boolean close);
}
