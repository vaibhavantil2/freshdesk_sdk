package com.freshdesk.sdkupdate;

import java.io.File;

/**
 *
 * @author subhash
 */
public class Constants {
    private static final String FRSH_HOME_ENV = "FRSH_HOME";
    public static final File FRSH_HOME = getFrshHome();
    public static final File BIN_DIR = new File(FRSH_HOME, "bin");
    public static final File SDK_DIR = new File(FRSH_HOME, "sdk");
    
    public static final String VER_WS_ENDPT = "http://s3.amazonaws.com/freshapps-staging-pub/sdk/version.json";
    
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
}
