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
    
    protected String getName(String input) {
        final int MAX_CHARS = 6;
        String processed = input.toLowerCase()
                .replaceAll("[^a-zA-Z0-9]", "_");
        if(processed.length() > MAX_CHARS) {
            processed = processed.substring(0, MAX_CHARS);
        }
        return "fa_" + processed + "_101";
    }

    public String getLiquidKey() {
        return NS_LIQUID_KEY;
    }

    public String getLiquidVal() {
        return NS_LIQUID_VAL;
    }

    public AppIdNSResolver(File prjDir) throws IOException {
        String dirName = prjDir.getCanonicalFile().getName();
        this.NS_LIQUID_VAL = getName(dirName);
    }
    
    public Map<String, Object> getNamespace() {
        Map<String, Object> namespaceObject = new HashMap();
        namespaceObject.put(NS_LIQUID_KEY, NS_LIQUID_VAL);
        return Collections.unmodifiableMap(namespaceObject);
    }
    
}
