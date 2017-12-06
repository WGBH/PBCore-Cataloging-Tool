package digitalbedrock.software.pbcore.utils;

public class Registry {

    private static final Registry INSTANCE = new Registry();
    private final boolean isMac;

    private Registry() {
        isMac = System.getProperty("os.name").toLowerCase().contains("mac");
    }

    public static Registry getInstance() {
        return INSTANCE;
    }

    public boolean isMac() {
        return isMac;
    }
}
