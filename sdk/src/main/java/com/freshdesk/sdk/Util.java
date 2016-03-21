package com.freshdesk.sdk;

import com.freshdesk.sdk.plug.PlugFile;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author subhash
 */
public final class Util {
    
    private Util() {}
    
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
            if(!(htmlFile.isFile() && scssFile.isFile() && jsFile.isFile())) {
                throw new FileNotFoundException();
            }
            else if(!(jsFile.canRead() || scssFile.canRead() || htmlFile.canRead())) {
                throw new FAException("Cannot read file(s) in app dir.");
            }
        }
    }
}
