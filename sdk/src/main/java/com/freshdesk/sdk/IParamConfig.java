package com.freshdesk.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author subhash
 */
public class IParamConfig {
    private static final String IPARAM_CFG_DIR = "iparam";
    
    private final Map<String, Map<String, Object>> data = new HashMap<>();
    
    public IParamConfig(final File prjDir, Charset c) throws FAException {
        File iparamDir = new File(prjDir, IPARAM_CFG_DIR);
        File[] iparamFiles = iparamDir.listFiles(
                (File pathname) ->
                        pathname.getName().matches("iparam_[a-z]{2}\\.yml"));
        if(iparamFiles != null) {
            for(File f: iparamFiles) {
                final String l = f.getName().substring(7, 9);
                try {
                    data.put(l, getYmlContent(f, c));
                }
                catch(IOException ex) {
                    throw new FAException(
                        "Error reading / parsing iparam config: " + f.getName(),
                        ex);
                }
            }
        }
    }
    
    private Map<String, Object> getYmlContent(File f, Charset c) throws IOException {
        try (final Reader reader = new InputStreamReader(
                new FileInputStream(f), c)) {
            final Yaml yaml = new Yaml();
            @SuppressWarnings("unchecked")
            final Map<String, Object> m = (Map<String, Object>) yaml.load(reader);
            
            if(m != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> d = (Map<String, Object>) m.get("iparam");
                if(d != null) return d;
            }
            return Collections.emptyMap();
        }
    }
    
    public Map<String, Object> getConfig(String lang) {
        Map<String, Object> d = data.get(lang);
        if(d != null) return Collections.unmodifiableMap(d);
        return null;
    }
    
    public Set<String> getConfigLangs() {
        return data.keySet();
    }
}
