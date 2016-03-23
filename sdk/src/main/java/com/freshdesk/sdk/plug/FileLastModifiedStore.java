package com.freshdesk.sdk.plug;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author raghav
 */
public class FileLastModifiedStore {
    
    private static final Map<String, Long> STORE = new HashMap<>();
    
    public static void setLastModified(File file) {
        STORE.put(file.getAbsolutePath(), file.lastModified());
    }
    
    public static Long getLastModified(File file) {
        return STORE.get(file.getAbsolutePath());
    }
}
