package com.freshdesk.sdk;

/**
 *
 * @author subhash
 */
public class SdkException extends FAException {
    
    private final ExitStatus exitStatus;
    
    public SdkException(SdkException ex) {
        super(ex);
        exitStatus = ex.getExitStatus();
    }
    
    public SdkException(final ExitStatus exitStatus, final String message) {
        super(message);
        this.exitStatus = exitStatus;
    }

    public SdkException(final ExitStatus exitStatus, final Throwable cause) {
        super(cause);
        this.exitStatus = exitStatus;
    }

    public SdkException(final ExitStatus exitStatus, final String message, final Throwable cause) {
        super(message, cause);
        this.exitStatus = exitStatus;
    }
    
    public ExitStatus getExitStatus() {
        return exitStatus;
    }
}
