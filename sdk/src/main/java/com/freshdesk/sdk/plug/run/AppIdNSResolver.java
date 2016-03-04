package com.freshdesk.sdk.plug.run;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author raghav
 */
public class AppIdNSResolver {
    
    private static final String NS_LIQUID_KEY = "app_id";
    private final String NS_LIQUID_VAL;

    public AppIdNSResolver(File prjDir) throws IOException {
        String dirName = prjDir.getCanonicalPath().substring(
                   prjDir.getCanonicalPath().lastIndexOf('/') + 1);
        this.NS_LIQUID_VAL = "fa_" + dirName.substring(0, 6) + "_101";
    }
    
    public Map<String, Object> getNamespace() {
        Map<String, Object> namespaceObject = new HashMap();
        namespaceObject.put(NS_LIQUID_KEY, NS_LIQUID_VAL);
        return Collections.unmodifiableMap(namespaceObject);
    }
    
}
