package com.freshdesk.sdk.plug.run;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.freshdesk.sdk.FAException;
import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.SdkException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raghav
 */
public class TicketBean implements java.io.Serializable {
    
    private static final DateFormat HELPKIT_DATE_FORMAT = new SimpleDateFormat(
            "MMMMM dd yyyy 'at' hh:mm a");
    
    @SuppressWarnings("unchecked")
    private final Map<String, Object> ticketDetails;
    
    private final String ticketId;
    private final String description;
    private final String status;
    private final String priority;
    private final String source;
    private final String requesterId;
    private final String requesterName;
    private final String responderName;
    private final String subject;
    private final String type;
    private final String pageUrl;
    private final Date frDueByTime;
    private final Date dueBy;
    
    public TicketBean(Map<String, Object> ticketDetails, String url) {
        this.ticketDetails = (Map<String, Object>)ticketDetails.get("helpdesk_ticket");
        
        this.ticketId = getStrValue("id");
        this.description = getStrValue("description");
        this.status = getStrValue("status_name");
        this.priority = getStrValue("priority_name");
        this.source = getStrValue("source_name");
        this.requesterId = getStrValue("requester_id");
        this.requesterName = getStrValue("requester_name");
        this.responderName = getStrValue("responder_name");
        this.subject = getStrValue("subject");
        this.type = getStrValue("ticket_type");
        this.dueBy = parseDate(getStrValue("due_by"));
        this.frDueByTime = parseDate(getStrValue("frDueBy"));
        this.pageUrl = url;
    }
    
    public String getTicketId() { return this.ticketId; };
    public String getDescription() { return this.description; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public String getSource() { return source; }
    public String getRequesterId() { return requesterId; }
    public String getRequesterName() { return requesterName; }
    public String getResponderName() { return responderName; }
    public String getSubject() { return subject; }
    public String getType() { return type; }
    public Date getFrDueByTime() { return frDueByTime; }
    public String getFrDueByTimeFormatted() { return formatDate(frDueByTime); }
    public Date getDueBy() { return dueBy; }
    public String getDueByFormatted() { return formatDate(dueBy); }
    public String getPageUrl() { return pageUrl; }
    
    private String getStrValue(String in) {
        Object o = ticketDetails.get(in);
        
        if(o == null) {
            return null;
        }
        else if(o instanceof String) {
            return (String) o;
        }
        else {
            return o.toString();
        }
    }
    
    private Map<String, Object> stripName(Map<String, Object> in) {
        Map<String, Object> out = new HashMap<>();
        for(String key : in.keySet()) {
            if(key.contains("_")) {
                out.put(key.split("_")[0], in.get(key));
            }
        }
        return out;
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> getCustomFields() {
        return this.stripName((Map<String, Object>)ticketDetails.get("custom_field"));
    }
    
    private Date parseDate(String date) throws FAException {
        try {
            ISO8601DateFormat df = new ISO8601DateFormat();
            return df.parse(date);
        }
        catch (ParseException ex) {
            throw new SdkException(ExitStatus.INVALID_PARAM, "Error while parsing date.");
        }
    }
    
    private String formatDate(Date date) {
        return HELPKIT_DATE_FORMAT.format(date);
    }
}
