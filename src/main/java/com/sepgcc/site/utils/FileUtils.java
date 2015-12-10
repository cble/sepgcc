package com.sepgcc.site.utils;

import com.google.common.io.Files;

import java.io.File;

public class FileUtils {

    public static boolean createDir(String path) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                return dir.mkdirs();
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean writeFile(File file, String path) {
        try {
            Files.copy(file, new File(path));
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
