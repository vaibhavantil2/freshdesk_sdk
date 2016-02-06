package com.freshdesk.sdk;

import java.io.File;
import java.io.IOException;
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
    private final ConcurrentLinkedQueue<Session> sessions = new ConcurrentLinkedQueue<>();
    
    private final LocalTestingFileChangeWatcher fileWatcher;
    
    public NotifyCodeChangeWebSocketEndpoint() {
        fileWatcher = new LocalTestingFileChangeWatcher(this);
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
        sessions.add(session);
    }
    
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }
    
    @OnMessage
    public void onMessage(String msg, Session session) {
        System.out.println(msg);
        sendToAllConnectedSessions("Got: " + msg);
    }
    
    private void sendToAllConnectedSessions(String message) {
        sessions.stream().forEach((session) -> {
            sendToSession(session, message);
        });
    }
    
    private void sendToSession(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        }
        catch(IOException ex) {
            sessions.remove(session);
            // handle logging!
        }
    }

    @Override
    public void notify(File f) {
        Map<String, Object> m = new HashMap<>();
        m.put("path", f.getAbsolutePath());
        String msg = JsonUtil.maptoJson(m);
        sendToAllConnectedSessions(msg);
    }
}
