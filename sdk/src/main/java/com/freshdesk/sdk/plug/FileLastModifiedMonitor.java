package com.freshdesk.sdk.plug;

import java.io.File;

/**
 *
 * @author raghav
 */
public class FileLastModifiedMonitor {
    
    private static Long SCSS_LAST_MODIFIED = null;
    
    public static void setLastModified(File file) {
        SCSS_LAST_MODIFIED = file.lastModified();
    }
    
    public static Long getLastModified() {
        return SCSS_LAST_MODIFIED;
    }
}
