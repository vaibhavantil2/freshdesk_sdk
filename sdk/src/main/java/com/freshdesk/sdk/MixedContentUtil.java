package com.freshdesk.sdk;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Raghav
 */
public class MixedContentUtil {
    
    private static final String HEADER_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    
    private MixedContentUtil(){};
    
    public static void allowedOrigin(HttpServletResponse resp, String origin) {
        resp.addHeader(HEADER_ALLOW_ORIGIN, origin);
    }
}
