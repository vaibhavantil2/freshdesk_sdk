package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.FAException;

/**
 *
 * @author raghav
 */
public enum PlugFile {
    
    HTML, CSS, JS;
    
    public static String toString(PlugFile file) {
        switch(file) {
            case HTML :
                return "template.html";
            case CSS :
                return "style.scss";
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
