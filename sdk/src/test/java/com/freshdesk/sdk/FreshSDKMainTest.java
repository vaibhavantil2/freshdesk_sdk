package com.freshdesk.sdk;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author swathithangavel
 */
public class FreshSDKMainTest {
    
    public FreshSDKMainTest() {
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
     * Test of main method, of class FreshSDKMain.
     */
    @Test
    public void testMainforPlug() throws IOException {
        System.out.println("main");
        
        // init-app:
        final File prjDir = File.createTempFile("fa_", "_tc");
        prjDir.delete();
        String dir = prjDir.getAbsolutePath();
        String[] args = new String[]{"init", "plug", dir};
        FreshSDKMain.main(args);
        if (prjDir.exists()) {
            FileUtils.deleteDirectory(prjDir);
        }
        else {
            fail("init plug failed.");
        }
        
        // help:
        FreshSDKMain.main(new String[]{"help"});
        
        // invalid input:
        args[0] = "testdefault";
        // FreshSDKMain.main(args);
    }
    
}
