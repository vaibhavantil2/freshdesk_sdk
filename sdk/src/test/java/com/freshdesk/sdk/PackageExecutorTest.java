package com.freshdesk.sdk;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author swathithangavel
 */
public class PackageExecutorTest {
    
    public PackageExecutorTest() {
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
     * Test of execute method, of class PackageExecutor.
     */
    @Test
    public void testExecute() throws IOException {
        System.out.println("execute");
        File f = File.createTempFile("fa_", "_tst");
        f.delete();
        f.mkdir();
        
        try {
            String[] args = new String[]{f.getAbsolutePath()};
            InitExecutor init = new InitExecutor();
            init.init(f);
            init.execute();
            
            PackageExecutor app = new PackageExecutor();
            app.init(f);
            app.execute();

            File distDir = new File(f, "dist");
            if (!distDir.exists()) {
                Assert.fail("package target failed!");
            }
        }
        finally {
            FileUtils.deleteDirectory(f);
        }
    }
    
    /**
     * Test of cleanFileName method, of class AbstractPackageExecutor.
     */
    @Test
    public void testCleanFileName() {
        System.out.println("cleanFileName");
        String input = "Sdk Project:1.0";
        String expResult = "sdk-project-1.0";
        String result = PackageExecutor.cleanFileName(input);
        assertEquals(expResult, result);
    }
}
