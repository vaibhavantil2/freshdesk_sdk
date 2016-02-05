package com.freshdesk.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
            try {
                String version = getSdkInstalledVersion();
                return new File(FRSH_HOME, "sdk/frsh-" + version);
            }
            catch(IOException ex) {
                throw new SdkException(ExitStatus.SETUP_ERROR, ex);
            }
        }
    }
    
    private static String getSdkInstalledVersion() throws IOException {
        File f = new File(FRSH_HOME, "config/exec_version");
        if(f.canRead()) {
            Properties p = new Properties();
            p.load(new FileInputStream(f));
            String version = p.getProperty("version");
            if(version != null) return version;
            else throw new SdkException(ExitStatus.SETUP_ERROR,
                    "`version` not available in `" + f.getAbsolutePath() + "`.");
        }
        else {
            throw new IOException("Cannot read: " + f.getAbsolutePath());
        }
    }
}
