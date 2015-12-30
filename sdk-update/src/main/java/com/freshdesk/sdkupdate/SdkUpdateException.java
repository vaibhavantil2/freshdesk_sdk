package com.freshdesk.sdkupdate;

/**
 *
 * @author subhash
 */
public class SdkUpdateException extends RuntimeException {

    public SdkUpdateException(String message) {
        super(message);
    }

    public SdkUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public SdkUpdateException(Throwable cause) {
        super(cause);
    }

    public SdkUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
