package com.freshdesk.sdk.plug.run;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author raghav
 */
public class NamespaceResolver {
    
    private final String plugId;
    
    public NamespaceResolver() {
        // namespace generation logic
        this.plugId = "testplug";
    }
    
    public Map<String, Object> getNamespace() {
        Map<String, Object> namespaceObject = new HashMap();
        namespaceObject.put("plug_id", this.plugId);
        return Collections.unmodifiableMap(namespaceObject);
    }
    
}
