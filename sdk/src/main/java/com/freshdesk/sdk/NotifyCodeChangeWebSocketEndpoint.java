package com.freshdesk.sdk;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author subhash
 */
@ServerEndpoint(value="/notify-change")
public class NotifyCodeChangeWebSocketEndpoint implements NotifyLocalModification {
    
    public static boolean verbose = false;
    public static boolean verboseException = false;
    public static boolean trace = false;
    public static VerboseOptions verbosity = null;
    
    private static final String MSG_PREFIX = "[ws://notify-change %s]";
    private static final String MSG_REC = String.format(MSG_PREFIX, "receive");
    private static final String MSG_SND = String.format(MSG_PREFIX, "send");
    private static final String MSG_OPN = String.format(MSG_PREFIX, "open");
    private static final String MSG_CLS = String.format(MSG_PREFIX, "close");
    
    private final ConcurrentLinkedQueue<Session> sessions = new ConcurrentLinkedQueue<>();
    private final LocalTestingFileChangeWatcher fileWatcher;
    
    public NotifyCodeChangeWebSocketEndpoint() {
        fileWatcher = new LocalTestingFileChangeWatcher(this);
        if(verbosity != null) fileWatcher.setVerbosity(verbosity);
        
        Runnable r = () -> {
            try {
                fileWatcher.watch(new File("."));
            }
            catch(IOException ex) {
                ex.printStackTrace(System.err);
            }
        };
        new Thread(r).start();
    }
    
    @OnOpen
    public void onOpen(Session session) {
        if(trace) System.out.printf("%s %s.\n", MSG_OPN, session.getId());
        sessions.add(session);
    }
    
    @OnClose
    public void onClose(Session session) {
        if(trace) System.out.printf("%s %s.\n", MSG_CLS, session.getId());
        sessions.remove(session);
    }
    
    @OnMessage
    public void onMessage(String msg, Session session) {
        if(trace) {
            System.out.printf("%s %s.\n", MSG_REC, msg);
        }
    }
    
    private void sendToAllConnectedSessions(String msg) {
        if(trace) System.out.printf("%s %s.\n", MSG_SND, msg);
        
        sessions.stream().forEach((session) -> {
            sendToSession(session, msg);
        });
    }
    
    private void sendToSession(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        }
        catch(IOException ex) {
            sessions.remove(session);
        }
    }
    
    public static void setVerbosity(VerboseOptions opts) {
        verbose = opts.isVerbose();
        verboseException = opts.isVerboseException();
        trace = opts.isTrace();
        verbosity = opts;
    }

    @Override
    public void notify(WatchEvent<Path> we) {
        Map<String, Object> m = new HashMap<>();
        m.put("path", we.context().toAbsolutePath().toString());
        m.put("kind", we.kind().name());
        String msg = JsonUtil.maptoJson(m);
        sendToAllConnectedSessions(msg);
    }
}
