package com.freshdesk.sdkupdate;

import java.io.File;
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
public class SdkUtilTest {
    
    public SdkUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCurrentVersion method, of class SdkUtil.
     */
    @Test
    public void testGetCurrentVersion() {
        System.out.println("getCurrentVersion");
        File sdkDir = new File("src/test/resources/sdk-update/sdk");
        Version expResult = new VersionImpl("1.2.3");
        Version result = SdkUtil.getCurrentVersion(sdkDir);
        assertEquals(expResult, result);
    }
    
}
