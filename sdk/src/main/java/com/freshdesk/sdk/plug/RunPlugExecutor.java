package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.Constants;
import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.SdkException;
import com.freshdesk.sdk.AbstractRunExecutor;
import com.freshdesk.sdk.RunServletUtil;
import com.freshdesk.sdk.VerboseOptions;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 *
 * @author user
 */
public class RunPlugExecutor extends AbstractRunExecutor {

    @Override
    public void execute() throws SdkException {
        final Server server = new Server(Constants.SERVER_PORT);
        final ServletContextHandler ctx = new ServletContextHandler();
        ctx.setContextPath("/");
        server.setHandler(ctx);
        
        VerboseOptions opts = new VerboseOptions(verbose, verboseException, trace);
        ctx.addServlet(new ServletHolder(new PlugServlet(opts)),
                PlugServlet.PATH);
        
        RunServletUtil.registerCommonServlets(ctx, opts);
        
        try {
            if(verbose) {
                System.out.printf("Starting server at port %s...\n",
                        Constants.SERVER_PORT);
            }
            server.start();
        }
        catch (Exception ex) {
            throw new SdkException(ExitStatus.CMD_FAILED, ex);
        }
        server.setStopAtShutdown(true);
    }
    
}
