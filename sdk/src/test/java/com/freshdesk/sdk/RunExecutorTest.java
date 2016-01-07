package com.freshdesk.sdk;

import java.io.File;
import java.util.Arrays;
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
public class RunExecutorTest {
    
    public RunExecutorTest() {
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
     * Test of execute method, of class RunAppExecutor.
     */
    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");
        
        final File prjDir = File.createTempFile("fa_", "_tc");
        prjDir.delete();
        prjDir.mkdirs();
        
        InitExecutor inst1 = new InitExecutor();
        inst1.arguments = Arrays.asList(new String[]{"plug"});
        inst1.init(prjDir);
        inst1.execute();
        
        if (prjDir.exists()) {
            FileUtils.deleteDirectory(prjDir);
        }
        else {
            fail("run failed.");
        }
    }
    
}
