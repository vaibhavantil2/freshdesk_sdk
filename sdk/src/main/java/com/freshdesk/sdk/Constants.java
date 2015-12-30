package com.freshdesk.sdk;

import java.io.File;

/**
 *
 * @author user
 */
public final class Constants {
    
    public static final String FRSH_HOME_ENV = "FRSH_HOME";
    public static final String FRSH_DEV_ENV = "FRSH_SDK_DEV";
    
    // FRSH_HOME:
    public static final File FRSH_HOME = getFrshHome();
    
    // Java version:
    public static final String JAVA_VER = System.getProperty("java.version");
    
    // SDK Directories:
    public static final File SDK_DIR = getSdkHome();
    public static final File SDK_BIN_DIR = new File(SDK_DIR, "bin");
    public static final File SDK_TMPL_DIR = new File(SDK_DIR, "template");
    public static final File SDK_LIB_DIR = new File(SDK_DIR, "lib");
    public static final File SDK_FRMW_DIR = new File(SDK_DIR, "framework");
    public static final File SDK_GEMS_DIR = new File(SDK_DIR, "gems");
    
    public static final int SERVER_PORT = 10001;
    
    private static File getFrshHome() {
        String home = System.getProperty(FRSH_HOME_ENV);
        if(home == null) {
            home = System.getenv(FRSH_HOME_ENV);
        }
        if(home != null) {
            return new File(home);
        }
        return null;
    }
    
    private static File getSdkHome() {
        String sdkHome = System.getProperty(FRSH_DEV_ENV);
        if(sdkHome == null) {
            sdkHome = System.getenv(FRSH_DEV_ENV);
        }
        if(sdkHome != null) {
            return new File(sdkHome);
        }
        else {
            return new File(FRSH_HOME, "sdk/latest");
        }
    }
}
