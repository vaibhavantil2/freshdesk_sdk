package com.freshdesk.sdk;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author subhash
 */
public class IParamConfigTest {
    
    public IParamConfigTest() {
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
     * Test of getConfig method, of class IParamConfig.
     */
    @Test
    public void testGetConfig() {
        System.out.println("getConfig");
        IParamConfig instance = new IParamConfig(
                new File("src/test/resources/plg-prj"), StandardCharsets.UTF_8);
        String expResult = "Username";
        Map<String, Object> d = instance.getConfig("en");
        String result = ((Map<String, String>)d.get("username")).get("display_name");
        assertEquals(expResult, result);
    }
    
}
