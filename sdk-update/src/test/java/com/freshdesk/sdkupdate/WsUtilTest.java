package com.freshdesk.sdkupdate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.wiztools.appupdate.Version;
import org.wiztools.appupdate.VersionImpl;

/**
 *
 * @author subhash
 */
public class WsUtilTest {
    
    public WsUtilTest() {
    }
    
    private static WebServerUtil server = new WebServerUtil();
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        server.start();
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
        server.stop();
    }
    
    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of getLatest method, of class WsUtil.
     */
    @Test
    public void testGetLatest() {
        System.out.println("getLatest");
        WsUtil instance = new WsUtil("http://localhost:12321/ver.json");
        Version expResult = new VersionImpl("3.4.0");
        Version result = instance.getLatest();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDlUrl method, of class WsUtil.
     */
    @Test
    public void testGetDlUrl() {
        System.out.println("getDlUrl");
        WsUtil instance = new WsUtil("http://localhost:12321/ver.json");
        String expResult = "http://www.example.com/app-version.tgz";
        String result = instance.getDlUrl();
        assertEquals(expResult, result);
    }
    
}
