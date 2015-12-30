package com.freshdesk.sdk;

import com.freshdesk.sdk.InitExecutor;
import java.io.File;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author swathithangavel
 */
public class InitExecutorTest {
    
    public InitExecutorTest() {
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
     * Test of execute method, of class InitAppExecutor.
     */
    @Test
    public void testExecuteforApp() throws Exception {
        System.out.println("execute");
        final File prjDir = File.createTempFile("fa_", "_tc");
        prjDir.delete();
        prjDir.mkdirs();
        InitExecutor instance = new InitExecutor();
        instance.arguments = Arrays.asList(new String[]{"plug"});
        instance.init(prjDir);
        instance.execute();
        if (prjDir.exists()) {
            FileUtils.deleteDirectory(prjDir);
        }
        else {
            fail("init-app failed.");
        }
    }
    
    /**
     * Test of execute method, of class InitAppExecutor.
     */
    @Test
    public void testExecuteforPlug() throws Exception {
        System.out.println("execute");
        final File prjDir = File.createTempFile("fa_", "_tc");
        prjDir.delete();
        prjDir.mkdirs();
        InitExecutor instance = new InitExecutor();
        instance.arguments = Arrays.asList(new String[]{"plug"});
        instance.init(prjDir);
        instance.execute();
        if (prjDir.exists()) {
            FileUtils.deleteDirectory(prjDir);
        }
        else {
            fail("init-plug failed.");
        }
    }
    
}
