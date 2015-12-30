package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.FAException;
import com.freshdesk.sdk.plug.run.TicketBean;
import com.freshdesk.sdk.plug.run.TicketLiquefier;
import com.freshdesk.sdk.plug.run.UserBean;
import com.freshdesk.sdk.plug.run.UserLiquefier;
import com.freshdesk.sdk.plug.run.UserType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author user
 */
public class PlugUtil {

    private final Map<String, Object> params;
    private final Map<String, Object> requester;
    private final PlugPages plugPage;
    private final String pageUrl;
    
    public PlugUtil(Map<String,Object> params,
            Map<String, Object> requester,
            PlugPages page,
            String pgUrl) throws FAException {
        if (params != null) {
            this.params = params;
        }
        else {
            throw new FAException("Page Params are empty. Cannot Continue.");
        }
        if(requester != null) {
            this.requester = requester;
        }
        else {
            throw new FAException("Requester Details are empty. Cannot Continue.");
        }
        if (page != null) {
            this.plugPage = page;
        }
        else {
            throw new FAException("Plug page param is empty. Cannot Continue.");
        }
        if(pgUrl != null) {
            this.pageUrl = pgUrl;
        }
        else {
            throw new FAException("Page Url param is empty. Cannot Continue.");
        }
    }
    
    public Map<String, Object> getParams() throws IOException, FAException {
        Map<String, Object> out = new HashMap<>();
        try {
            if (params != null) {
                if(plugPage == PlugPages.TICKET) {
                    out.putAll(prepareTicketObject(params));
                    out.putAll(prepareContactObject(requester));
                }
                if(plugPage == PlugPages.CONTACT) {
                    out = prepareContactObject(params);
                }
            }
            else {
                throw new FAException(
                        "Params empty and API key also not configured. Cannot Continue.");
            }
            return out;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return out;
    }
    
    private Map<String, Object> prepareTicketObject(Map<String, Object> in) {
        return TicketLiquefier.getTicketObject(new TicketBean(in, pageUrl));
    }
    
    private Map<String, Object> prepareContactObject(Map<String, Object> in) {
        return UserLiquefier.getUserObject(UserType.REQUESTER, new UserBean(in));
    }
    
}
