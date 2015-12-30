package com.freshdesk.sdk;

import java.io.File;
import java.io.PrintStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author swathithangavel
 */
public class HelpExecutorTest {
    
    public HelpExecutorTest() {
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
     * Test of printUsage method, of class HelpExecutor.
     */
    @Test
    public void testPrintUsage() {
        System.out.println("printUsage");
        PrintStream out = System.out;
        HelpExecutor.printUsage(out);
    }

    /**
     * Test of execute method, of class HelpExecutor.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        String[] args = new String[2];
        args[0] = "frsh";
        args[1] = "help";
        HelpExecutor instance = new HelpExecutor();
        instance.init(new File("src/test/resources/plg-prj"));
        instance.execute();
    }
    
}
