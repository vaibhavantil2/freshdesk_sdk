package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.FAException;

/**
 *
 * @author raghav
 */
public enum PlugExecutionContext {
    PACKAGE, RUN;
    
    public String toString(PlugExecutionContext ctx) {
        switch (ctx) {
            case PACKAGE:
                return "package";
            case RUN:
                return "run";
            default:
                throw new FAException("Inavlid Execution Context");
        }
    }
}
