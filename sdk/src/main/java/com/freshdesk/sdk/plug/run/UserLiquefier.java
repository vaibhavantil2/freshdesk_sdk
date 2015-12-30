package com.freshdesk.sdk.plug.run;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raghav
 */
public class UserLiquefier {
    
    public static Map<String, Object> getUserObject(UserType type, UserBean bean) {
        Map<String, Object> out = new HashMap<>();
        Map<String, Object> user = new HashMap<>();
        user.put("id", bean.getId());
        user.put("name", bean.getName());
        user.put("email", bean.getEmail());
        user.put("active", bean.getActive());
        if (type == UserType.REQUESTER) {
            user.putAll(bean.getCustomFields());
        }
        out.put(UserType.toString(type), user);
        
        return Collections.unmodifiableMap(out);
    }
}
