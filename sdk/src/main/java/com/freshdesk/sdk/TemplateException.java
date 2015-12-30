package com.freshdesk.sdk;

/**
 *
 * @author subhash
 */
public class TemplateException extends FAException {

    public TemplateException() {
        super();
    }

    public TemplateException(final String message) {
        super(message);
    }

    public TemplateException(final Throwable cause) {
        super(cause);
    }

    public TemplateException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
}
