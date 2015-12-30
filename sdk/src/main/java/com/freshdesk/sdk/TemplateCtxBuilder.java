package com.freshdesk.sdk;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author subhash
 */
public class TemplateCtxBuilder {
    private final Map<String, Object> ctx = new ConcurrentHashMap<>();
    
    public TemplateCtxBuilder addInstallationParams(final Map<String, Object> params) {
        ctx.put("iparam", params);
        return this;
    }
    
    public TemplateCtxBuilder addExisting(final Map<String, Object> existing) {
        ctx.putAll(existing);
        return this;
    }
    
    public Map<String, Object> build() {
        return Collections.unmodifiableMap(ctx);
    }
}
