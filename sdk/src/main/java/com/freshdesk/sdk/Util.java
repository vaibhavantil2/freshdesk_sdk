package com.freshdesk.sdk;

import com.freshdesk.sdk.plug.PlugFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 *
 * @author subhash
 */
public final class Util {
    
    private Util() {}
    
    public static String[] skipN(final String[] arr, int n) {
        if(arr.length > n) {
            return Arrays.copyOfRange(arr, n, arr.length);
        }
        else {
            return new String[]{};
        }
    }
    
    public static String[] skipFirst(final String[] arr) {
        return skipN(arr, 1);
    }
    
    public static String getMimeType(final String fileName) {
        String contentType = null;
        if (fileName.endsWith("css")) {
            contentType = "text/css";
        } 
        else if (fileName.endsWith("js")) {
            contentType = "text/javascript";
        } 
        else if (fileName.endsWith("jpg")) {
            contentType = "image/jpeg";
        } 
        else if (fileName.endsWith("png")) {
            contentType = "image/png";
        } 
        else if (fileName.endsWith("gif")) {
            contentType = "image/gif";
        } 
        else if (fileName.endsWith("json")) {
            contentType = "application/json";
        }
        return contentType;
    }

    public static boolean isDirEmpty(File f) {
        return f.isDirectory() && f.list().length == 0;
    }

    public static void appDirValidator(File appDir) throws FileNotFoundException {
        if (appDir.isDirectory() && appDir.canRead()) {
            File htmlFile = new File(appDir, PlugFile.toString(PlugFile.HTML));
            File scssFile = new File(appDir, PlugFile.toString(PlugFile.SCSS));
            File jsFile = new File(appDir, PlugFile.toString(PlugFile.JS));
            if(!(htmlFile.isFile() && htmlFile.canRead()
                && scssFile.isFile() && scssFile.canRead()
                && jsFile.isFile() && jsFile.canRead())) {
                throw new FileNotFoundException();
            }
        }
    }
}
