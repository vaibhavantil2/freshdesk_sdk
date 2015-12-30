package com.freshdesk.sdk;

import org.wiztools.commons.MultiValueMap;
import org.wiztools.commons.MultiValueMapLinkedHashSet;

/**
 *
 * @author subhash
 */
public final class UrlParamParser {
    
    private UrlParamParser() {}
    
    public static MultiValueMap<String, String> parse(final String text) {
        final MultiValueMap<String, String> map = new MultiValueMapLinkedHashSet<>();
        
        final String[] params = text.split("&");
        for(final String param: params) {
            final String[] kv = param.split("=");
            if(2 == kv.length) {
                map.put(kv[0], kv[1]);
            }
        }
        
        return map;
    }
}
