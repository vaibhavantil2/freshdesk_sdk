package com.freshdesk.sdk.plug.run;

import java.io.File;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author raghav
 */
public class AppIdNSResolverTest {
    
    public AppIdNSResolverTest() {
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
     * Test of getNamespace method, of class AppIdNSResolver.
     */
    @Test
    public void testGetName() throws Exception {
        System.out.println("getNamespace");
        AppIdNSResolver instance = new AppIdNSResolver(new File("src/test/resources/plg-prj"));
        String expResult = "fa_plg_pr_101";
        String result = instance.getName("plg Prj");
        assertEquals(expResult, result);
    }
    
}
