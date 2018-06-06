package digitalbedrock.software.pbcore.listeners;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;

public interface ElementSelectionListener {

    void onElementSelected(String treeViewId, int index, PBCoreElement element, boolean close);
}
