package com.freshdesk.sdk;

/**
 *
 * @author subhash
 */
public enum ExitStatus {
    NO_PARAM, INVALID_PARAM, CORRUPT_MANIFEST, CORRUPT_IPARAM, SETUP_ERROR, CMD_FAILED,
    SUCCESS;
    
    public int getExitStatus() {
        switch(this) {
            case NO_PARAM:
                return 1;
            case INVALID_PARAM:
                return 2;
            case CMD_FAILED:
                return 3;
            case CORRUPT_MANIFEST:
                return 4;
            case CORRUPT_IPARAM:
                return 5;
            case SETUP_ERROR:
                return 6;
            default:
                return 0;
        }
    }
    
    public void exit() {
        System.exit(getExitStatus());
    }
}
