package com.freshdesk.sdk;

import org.wiztools.appupdate.Version;
import org.wiztools.appupdate.VersionImpl;

/**
 *
 * @author subhash
 */
public final class PlatformVersion {
    private PlatformVersion() {}
    
    public static final Version CURRENT_VERSION = new VersionImpl("1.0");
    private static final Version[] SUPPORTED_VERSIONS = new Version[]{CURRENT_VERSION};
    
    public static final boolean isSupported(Version version) {
        for(Version v: SUPPORTED_VERSIONS) {
            if(v.equals(version)) {
                return true;
            }
        }
        return false;
    }
}
