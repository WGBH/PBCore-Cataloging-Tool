package digitalbedrock.software.pbcore.listeners;

import digitalbedrock.software.pbcore.core.models.CVTerm;

public interface CVSelectionListener {

    void onCVSelected(String key, CVTerm cvTerm, boolean attr);
}
