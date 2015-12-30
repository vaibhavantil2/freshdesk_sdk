package com.freshdesk.sdkupdate;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 *
 * @author subhash
 */
class WebServerUtil {
    
    private Server server;
    
    void start() throws Exception {
        server = new Server(12321);
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
        resource_handler.setResourceBase("src/test/resources/http");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{ resource_handler, new DefaultHandler() });
        server.setHandler(handlers);
        server.start();
        System.out.println("Started server...");
    }
    
    void stop() throws Exception {
        System.out.println("Stopping server...");
        server.stop();
    }
}
