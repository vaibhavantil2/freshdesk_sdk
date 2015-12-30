package com.freshdesk.sdk.plug.run;

import com.freshdesk.sdk.FAException;

/**
 *
 * @author Raghav
 */
public enum UserType {
    REQUESTER, CURRENT_USER;
    
    public static UserType fromString(String userType) {
        switch (userType) {
            case "requester" :
                return REQUESTER;
            case "current_user" :
                return CURRENT_USER;
            default:
                throw new FAException("Unknown User Type : " + userType);
        }
    }
    
    public static String toString(UserType userType) {
        switch (userType) {
            case REQUESTER :
                return "requester";
            case CURRENT_USER :
                return "current_user";
            default:
                throw new FAException("Unknown User Type : " + userType);
        }
    }
}
