package com.freshdesk.sdkcommon;

import org.wiztools.appupdate.Version;
import org.wiztools.appupdate.VersionImpl;

/**
 *
 * @author subhash
 */
public final class Versions {
    private Versions() {}
    
    public static final Version SDK_VERSION = new VersionImpl("0.7.1");
    public static final Version PLATFORM_VERSION = new VersionImpl("1.0");
    
    public static boolean isCompatible(Version extnVersion) {
        return SDK_VERSION.getMajor() == extnVersion.getMajor();
    }
}
