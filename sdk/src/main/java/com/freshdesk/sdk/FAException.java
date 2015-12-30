package com.freshdesk.sdk;

/**
 *
 * @author subhash
 */
public class FAException extends RuntimeException {

    public FAException() {
        super();
    }

    public FAException(final String message) {
        super(message);
    }

    public FAException(final Throwable cause) {
        super(cause);
    }

    public FAException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
}
