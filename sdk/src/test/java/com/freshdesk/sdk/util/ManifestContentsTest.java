package com.freshdesk.sdk.util;

import com.freshdesk.sdk.ManifestContents;
import static java.nio.charset.StandardCharsets.*;
import java.io.File;
import java.nio.charset.Charset;
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
 * @author user
 */
public class ManifestContentsTest {

    public ManifestContentsTest() {
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
     * Test of getCharset method, of class ManifestUtil.
     */
    @Test
    public void testGetCharset() {
        System.out.println("getCharset");
        ManifestContents instance = new ManifestContents(new File("src/test/resources/plg-prj"));
        Charset expResult = UTF_8;
        Charset result = instance.getCharset();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlatformVersion method, of class ManifestUtil.
     */
    @Test
    public void testGetPlatformVersion() {
        System.out.println("getPlatformVersion");
        ManifestContents instance = new ManifestContents(new File("src/test/resources/plg-prj"));
        Version expResult = new VersionImpl("1.0");
        Version result = instance.getPlatformVersion();
        assertEquals(expResult, result);
    }

}
