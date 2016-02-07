package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.Constants;
import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.SdkException;
import com.freshdesk.sdk.AbstractRunExecutor;
import com.freshdesk.sdk.LocalTestingFileChangeWatcher;
import com.freshdesk.sdk.NotifyCodeChangeWebSocketEndpoint;
import com.freshdesk.sdk.RunServletUtil;
import com.freshdesk.sdk.VerboseOptions;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

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
            NotifyCodeChangeWebSocketEndpoint.setVerbosity(opts);
            
            final ServerContainer container = WebSocketServerContainerInitializer
                    .configureContext(ctx);
            container.addEndpoint(NotifyCodeChangeWebSocketEndpoint.class);
            
            // Configure the file change watcher to notify to ws://
            final LocalTestingFileChangeWatcher watcher =
                new LocalTestingFileChangeWatcher((WatchEvent<Path> we) -> {
                    NotifyCodeChangeWebSocketEndpoint.sendMessage(we);
                });
            watcher.setVerbosity(opts);
            
            // Start file change watcher service:
            new Thread(() -> {
                try {
                    watcher.watch(prjDir);
                }
                catch(IOException ex) {
                    System.err.println("File change watcher failed to start...");
                    if(verboseException) {
                        ex.printStackTrace(System.err);
                    }
                }
            }).start();
        }
        catch(ServletException | DeploymentException ex) {
            throw new SdkException(ExitStatus.CMD_FAILED, ex);
        }
        
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
