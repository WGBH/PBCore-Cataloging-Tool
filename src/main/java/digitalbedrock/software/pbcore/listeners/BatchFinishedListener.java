package digitalbedrock.software.pbcore.listeners;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;

public interface BatchFinishedListener {

    void onSaveBatch(PBCoreElement pbCoreElement);
}
