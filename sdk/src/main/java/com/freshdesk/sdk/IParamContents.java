package com.freshdesk.sdk;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to hold the iparam/test_data.yml information.
 * @author subhash
 */
public class IParamContents {
    
    private static final String IPARAM_CFG_DIR = "iparam";
    private static final String LOCAL_TEST_IPARAM = "test_data.yml";
    
    private static final Logger LOG = LogManager.getLogger(IParamContents.class);
    
    private final Map<String, Object> iparams;
    
    public IParamContents(final File prjDir, Charset c) throws FAException {
        File iparamDir = new File(prjDir, IPARAM_CFG_DIR);
        File f = new File(iparamDir, LOCAL_TEST_IPARAM);
        if((!f.exists()) || (!f.canRead())) {
            LOG.warn(
                    String.format("iparam file not loaded: %s/%s. Ignoring.",
                            IPARAM_CFG_DIR, LOCAL_TEST_IPARAM));
            iparams = Collections.emptyMap();
        }
        else {
            try {
                iparams = YamlUtil.getYaml(f, c);
            }
            catch(IOException ex) {
                throw new FAException(
                        "Installation parameter config (iparam/test_data.yml) load error.", ex);
            }
        }
    }
    
    public Map<String, Object> getIParams() {
        return Collections.unmodifiableMap(iparams);
    }
    
    public Object getIParam(String key) {
        return iparams.get(key);
    }
}
