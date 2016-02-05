package com.freshdesk.sdk;

import java.io.IOException;
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
public class NotifyCodeChangeWebSocketEndpoint {
    private final ConcurrentLinkedQueue<Session> sessions = new ConcurrentLinkedQueue<>();
    
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
}
