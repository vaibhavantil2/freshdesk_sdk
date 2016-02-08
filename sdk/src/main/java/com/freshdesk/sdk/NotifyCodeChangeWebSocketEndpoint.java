package com.freshdesk.sdk;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.HashMap;
import java.util.Iterator;
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
    
    private static final LinkedBlockingQueue<Session> SESSIONS = new LinkedBlockingQueue<>();
    
    @OnOpen
    public void onOpen(Session session) {
        SESSIONS.add(session);
        
        if(trace) System.out.printf("%s %s.\n", MSG_OPN, session.getId());
    }
    
    @OnClose
    public void onClose(Session session) {
        SESSIONS.remove(session);
        
        if(trace) System.out.printf("%s %s.\n", MSG_CLS, session.getId());
    }
    
    @OnMessage
    public void onMessage(String msg, Session session) {
        if(trace) System.out.printf("%s (%s) %s.\n", MSG_REC, session.getId(), msg);
    }
    
    public static void sendMessage(WatchEvent<Path> we) {
        String msg = watchEvent2Json(we);
        for(Iterator<Session> itr=SESSIONS.iterator();itr.hasNext();) {
            Session s = itr.next();
            try {
                s.getBasicRemote().sendText(msg);
                System.out.printf("%s (%s) %s.\n", MSG_SND, s.getId(), msg);
            }
            catch(IOException ex) {
                itr.remove();
                if(verboseException) {
                    System.err.printf(
                            "Error sending ws://notify-change for session %s."
                                    + " Marked session as closed.\n",
                            s.getId());
                    ex.printStackTrace(System.err);
                }
            }
        }
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
