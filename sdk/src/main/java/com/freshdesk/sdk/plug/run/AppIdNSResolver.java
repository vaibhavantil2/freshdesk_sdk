package com.freshdesk.sdk.plug.run;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author raghav
 */
public class AppIdNSResolver {
    
    private static final String NS_LIQUID_KEY = "app_id";
    private static final String NS_LIQUID_VAL = "fa_prefix";
    
    public Map<String, Object> getNamespace() {
        Map<String, Object> namespaceObject = new HashMap();
        namespaceObject.put(NS_LIQUID_KEY, NS_LIQUID_VAL);
        return Collections.unmodifiableMap(namespaceObject);
    }
    
}
