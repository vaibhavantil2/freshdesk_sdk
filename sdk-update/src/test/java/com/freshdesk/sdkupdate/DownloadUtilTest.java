package com.freshdesk.sdkupdate;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.wiztools.commons.Charsets;
import org.wiztools.commons.FileUtil;

/**
 *
 * @author subhash
 */
public class DownloadUtilTest {
    
    public DownloadUtilTest() {
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
     * Test of download method, of class DownloadUtil.
     */
    @Test
    public void testDownload() throws Exception {
        System.out.println("download");
        String url = "http://localhost:12321/hello.txt";
        String expResult = "Hello World!";
        File downloaded = new DownloadUtil(url).download();
        String result = FileUtil.getContentAsString(
                downloaded, Charsets.UTF_8).trim();
        
        assertEquals("hello.txt", downloaded.getName());
        assertEquals(expResult, result);
    }
    
}
