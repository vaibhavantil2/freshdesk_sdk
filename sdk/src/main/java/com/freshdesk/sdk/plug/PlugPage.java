package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.FAException;

/**
 *
 * @author user
 */
public enum PlugPage {
    TICKET, CONTACT;
    
    public static PlugPage fromString(String str) {
        switch(str) {
            case "ticket":
                return TICKET;
            case "contact":
                return CONTACT;
            default:
                throw new FAException("Unknown plug display page: " + str);
        }
    }
}
