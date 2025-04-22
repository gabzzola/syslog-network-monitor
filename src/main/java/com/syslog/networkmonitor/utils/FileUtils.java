package com.syslog.networkmonitor.utils;

import java.io.File;

public class FileUtils {

    public static File getDesktopFile(String fileName) {
        String userHome = System.getProperty("user.home");

        File desktopDir = new File(userHome + "/Área de trabalho");
        if (!desktopDir.exists()) {
            desktopDir = new File(userHome + "/Desktop");
        }
        return new File(desktopDir, fileName);
    }
}
