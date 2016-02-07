package com.freshdesk.sdk;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
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
public class NotifyCodeChangeWebSocketEndpoint {
    
    private static boolean verbose = false;
    private static boolean verboseException = false;
    private static boolean trace = false;
    
    private static final String MSG_PREFIX = "[ws://notify-change %s]";
    private static final String MSG_REC = String.format(MSG_PREFIX, "receive");
    private static final String MSG_SND = String.format(MSG_PREFIX, "send");
    private static final String MSG_OPN = String.format(MSG_PREFIX, "open");
    private static final String MSG_CLS = String.format(MSG_PREFIX, "close");
    
    private static final LinkedBlockingQueue<Session> sessions = new LinkedBlockingQueue<>();
    
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
        if(trace) System.out.printf("%s %s.\n", MSG_REC, msg);
    }
    
    public static void sendMessage(WatchEvent<Path> we) {
        String msg = watchEvent2Json(we);
        sessions.stream().forEach((s) -> {
            try {
                s.getBasicRemote().sendText(msg);
                System.out.printf("%s %s.\n", MSG_SND, msg);
            }
            catch(IOException ex) {
                sessions.remove(s);
                if(verboseException) ex.printStackTrace(System.err);
            }
        });
    }
    
    private static String watchEvent2Json(WatchEvent<Path> we) {
        Map<String, Object> m = new HashMap<>();
        m.put("path", we.context().toAbsolutePath().toString());
        m.put("kind", we.kind().name());
        return JsonUtil.maptoJson(m);
    }
    
    public static void setVerbosity(VerboseOptions opts) {
        verbose = opts.isVerbose();
        verboseException = opts.isVerboseException();
        trace = opts.isTrace();
    }
}
