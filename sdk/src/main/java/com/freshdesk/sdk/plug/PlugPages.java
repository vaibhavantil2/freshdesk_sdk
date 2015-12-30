package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.FAException;

/**
 *
 * @author user
 */
public enum PlugPages {
    TICKET, CONTACT;
    
    public static PlugPages fromString(String str) {
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
