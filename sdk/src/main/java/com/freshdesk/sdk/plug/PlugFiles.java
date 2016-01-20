package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.FAException;

/**
 *
 * @author raghav
 */
public enum PlugFiles {
    
    HTML, CSS, JS;
    
    public static String toString(PlugFiles file) {
        switch(file) {
            case HTML :
                return "app.html";
            case CSS :
                return "app.css";
            case JS :
                return "app.js";
            default :
                throw new FAException("Invalid Plug File.");
        }
    }  
    
    public static String[] getAllFiles() {
        return new String[] {
            toString(HTML),
            toString(CSS),
            toString(JS)
        };
    }
}
