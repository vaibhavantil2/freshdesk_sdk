package com.freshdesk.sdk;

import com.freshdesk.sdk.version.VersionCompatibilityCheckServlet;
import com.freshdesk.sdk.version.VersionServlet;
import javax.servlet.ServletException;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 *
 * @author subhash
 */
public final class RunServletUtil {
    
    private RunServletUtil(){}
    
    public static void registerCommonServlets(ServletContextHandler ctx,
            VerboseOptions opts) {
        ctx.addServlet(new ServletHolder(new StaticServlet(opts)),
                StaticServlet.PATH);
        ctx.addServlet(new ServletHolder(new VersionServlet(opts)),
                VersionServlet.PATH);
        ctx.addServlet(new ServletHolder(new VersionCompatibilityCheckServlet(opts)),
                VersionCompatibilityCheckServlet.PATH);
        
        // Error handling:
        ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();
        
        // 1. ServletException:
        ctx.addServlet(new ServletHolder(new ErrorServlet(opts)),
                ErrorServlet.PATH);
        errorHandler.addErrorPage(ServletException.class, ErrorServlet.PATH);
        
        // 2. 404:
        ctx.addServlet(new ServletHolder(new Error404Servlet(opts)),
                Error404Servlet.PATH);
        errorHandler.addErrorPage(404, Error404Servlet.PATH);
        
        // 3. Assign error handler to ctx:
        ctx.setErrorHandler(errorHandler);
    }
}
