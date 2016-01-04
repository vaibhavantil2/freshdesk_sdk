package com.freshdesk.sdk.plug.run;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raghav
 */
public class UserBean implements java.io.Serializable {
    
    protected Map<String, Object> userDetails;
    
    public UserBean(Map<String, Object> userDetails) {
        this.userDetails = (Map<String, Object>) userDetails.get("user");
        setValues();
    }
    private long id;
    private String name;
    private String email;
    private boolean active;

    protected long getId() { return id; }
    protected String getName() { return name; }
    protected String getEmail() { return email; }
    protected boolean getActive() { return active; }
    
    private void setId() { this.id = Long.parseLong(getValue("id").toString()); }
    private void setName() {this.name = (String) getValue("name"); }
    private void setEmail() { this.email = (String) getValue("email"); }
    private void  setActive() { this.active = (boolean) getValue("active"); } 
    
    private Object getValue(String in) {
        return userDetails.get(in);
    }
    
    private void setValues() {
        this.setId();
        this.setName();
        this.setEmail();
        this.setActive();
    }
    
    private Map<String, Object> stripName(Map<String, Object> in) {
        Map<String, Object> out = null;
        if (in != null) {
            out = new HashMap<>();
            for(String key : in.keySet()) {
                if (key.startsWith("cf_")) {
                    out.put(key.split("cf_")[1], in.get(key));
                }
            }
        }
        return out;  
    }
    
    public Map<String, Object> getCustomFields() {
        return stripName((Map<String, Object>) userDetails.get("custom_field"));
    }
}

