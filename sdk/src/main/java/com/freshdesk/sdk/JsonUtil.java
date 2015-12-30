package com.freshdesk.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wiztools.commons.StringUtil;

/**
 *
 * @author subhash
 */
public final class JsonUtil {
    
    private static final Logger LOG = LogManager.getLogger(JsonUtil.class);
    
    private JsonUtil(){}
    
    public static Map<String, Object> jsonToMap(final String jsonStr) {
        if(StringUtil.isEmpty(jsonStr)) {
            return Collections.EMPTY_MAP;
        }
        
        Map<String, Object> out = new HashMap<>();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            out = mapper.readValue(jsonStr,
                    new TypeReference<Map<String, Object>>() {});
        }
        catch (IOException e) {
            throw new FAException(e);
        }
        return out;
    }
    
    public static String maptoJson(final Map<String, Object> map) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final String json = mapper.writeValueAsString(map);
            return json;
        }
        catch (JsonProcessingException ex) {
            LOG.fatal((Object)null, ex);
            throw new IllegalArgumentException(ex);
        }
    }
}
