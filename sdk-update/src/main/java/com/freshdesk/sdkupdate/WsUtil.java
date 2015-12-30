package com.freshdesk.sdkupdate;

import java.io.IOException;
import org.wiztools.appupdate.Version;
import org.wiztools.appupdate.VersionUrl;
import org.wiztools.appupdate.VersionWSUtil;

/**
 *
 * @author subhash
 */
public class WsUtil {
    
    public final String ENDPOINT;
    
    private final VersionUrl vUrl;
    
    public WsUtil(String endpoint) {
        ENDPOINT = endpoint;
        try {
            vUrl = VersionWSUtil.getLatestVersion(ENDPOINT);
        }
        catch(IOException ex) {
            throw new SdkUpdateException(ex);
        }
    }
    
    public Version getLatest() {
        return vUrl.getVersion();
    }
    
    public String getDlUrl() {
        return vUrl.getDlUrl();
    }
}
