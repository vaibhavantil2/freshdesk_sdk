package com.freshdesk.sdk;

import java.io.File;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author swathithangavel
 */
public class CleanExecutorTest {
    
    public CleanExecutorTest() {
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
     * Test of execute method, of class CleanExecutor.
     */
    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");
        // Unable to test cause of System.exit in execute()
        File f = File.createTempFile("fa_", "_tst");
        f.delete();
        f.mkdir();
        
        System.out.println("Tmp Prj Dir: " + f.getAbsolutePath());
        
        try {
            InitExecutor init = new InitExecutor();
            init.init(f);
            init.execute();
            
            PackageExecutor app = new PackageExecutor();
            app.init(f);
            app.execute();
            
            CleanExecutor clean = new CleanExecutor();
            clean.init(f);
            clean.execute();

            File distDir = new File(f, "dist");
            if (distDir.exists()) {
                Assert.fail("clean target failed to clean!");
            }
        }
        finally {
            FileUtils.deleteDirectory(f);
        }
    }
    
}
