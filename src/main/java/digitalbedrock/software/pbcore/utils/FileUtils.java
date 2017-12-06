package digitalbedrock.software.pbcore.utils;

import java.io.File;

public class FileUtils {

    public static boolean isDirectoryEmpty(File dir) {
        return !dir.isDirectory() || dir.list().length == 0;
    }
}
