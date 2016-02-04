package com.freshdesk.sdkupdate;

import org.junit.Test;
import org.wiztools.appupdate.Version;

/**
 *
 * @author raghav
 */
public class InstallUtilTest {
    
    public InstallUtilTest() {
    }

    /**
     * Test of updateExecVersionConfig method, of class InstallUtil.
     */
    @Test
    public void testUpdateExecVersionConfig() throws Exception {
        System.out.println("updateExecVersionConfig");
        System.out.println("FRSH HOME" + Constants.FRSH_HOME.getAbsolutePath());
        WsUtil wsu = new WsUtil(Constants.VER_WS_ENDPT);
        Version version = wsu.getLatest();
        InstallUtil instance = new InstallUtil(Constants.FRSH_HOME, Constants.FRSH_HOME, version);
        instance.updateExecVersionConfig();
    }
    
}
