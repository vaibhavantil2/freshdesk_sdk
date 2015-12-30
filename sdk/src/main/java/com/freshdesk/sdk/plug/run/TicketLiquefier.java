package com.freshdesk.sdk.plug.run;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raghav
 */
public class TicketLiquefier {
    
    public static Map<String, Object> getTicketObject(TicketBean bean) {
        Map<String, Object> out = new HashMap<>();
        Map<String, Object> ticket = new HashMap<>();
        ticket.put("id", bean.getTicketId());
        ticket.put("description", bean.getDescription());
        ticket.put("due_by_time", bean.getDueByFormatted());
        ticket.put("status", bean.getStatus());
        ticket.put("priority", bean.getPriority());
        ticket.put("source", bean.getSource());
        ticket.put("fr_due_by_time", bean.getFrDueByTimeFormatted());
        ticket.put("subject", bean.getSubject());
        ticket.put("ticket_type", bean.getType());
        ticket.put("url", bean.getPageUrl());
        ticket.putAll(bean.getCustomFields());
        ticket.put("agent", getAgentDetails(bean));
        ticket.put("requester", getRequesterDetails(bean));
        out.put("ticket", ticket);
        return Collections.unmodifiableMap(out);
    }
    
    public static Map<String, Object> getRequesterDetails(TicketBean bean) {
         Map<String, Object> requester = new HashMap<>();
        requester.put("id", bean.getRequesterId());
        requester.put("name", bean.getRequesterName());
        return Collections.unmodifiableMap(requester);
    }
    
    public static Map<String, Object> getAgentDetails(TicketBean bean) {
        Map<String, Object> out = new HashMap<>();
        out.put("name", bean.getResponderName());
        return out;
    }
}
