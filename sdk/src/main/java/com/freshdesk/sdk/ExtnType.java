package com.freshdesk.sdk;

/**
 *
 * @author user
 */
public enum ExtnType {
    APP, PLUG, THEME;
    
    public static ExtnType fromString(String str) {
        switch(str) {
            case "app":
                return APP;
            case "plug":
                return PLUG;
            case "theme":
                return THEME;
            default:
                throw new FAException("Unknown package type in manifest: " + str);
        }
    }
}
