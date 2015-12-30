package com.freshdesk.sdk;

import javax.servlet.http.HttpServlet;

/**
 *
 * @author subhash
 */
public class SuperServlet extends HttpServlet {
    protected final boolean verbose;
    protected final boolean verboseException;
    protected final boolean trace;
    
    public SuperServlet(VerboseOptions opts) {
        verbose = opts.isVerbose();
        verboseException = opts.isVerboseException();
        trace = opts.isTrace();
    }
}
