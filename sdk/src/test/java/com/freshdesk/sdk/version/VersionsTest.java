package com.freshdesk.sdk.version;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.wiztools.appupdate.Version;
import org.wiztools.appupdate.VersionImpl;
import com.freshdesk.sdkcommon.Versions;

/**
 *
 * @author subhash
 */
public class VersionsTest {
    
    public VersionsTest() {
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
     * Test of isCompatible method, of class Versions.
     */
    @Test
    public void testIsCompatible() {
        System.out.println("isCompatible");
        Version extnVersion = new VersionImpl(
                Versions.SDK_VERSION.getMajor()+ ".100.200");
        boolean expResult = true;
        boolean result = Versions.isCompatible(extnVersion);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isCompatible method, of class Versions.
     */
    @Test
    public void testIsNotCompatible() {
        System.out.println("isNotCompatible");
        Version extnVersion = new VersionImpl(
                (Versions.SDK_VERSION.getMajor() + 10) + ".100.200");
        boolean expResult = false;
        boolean result = Versions.isCompatible(extnVersion);
        assertEquals(expResult, result);
    }
}
