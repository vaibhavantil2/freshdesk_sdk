package com.freshdesk.sdk;

import com.freshdesk.sdk.UrlParamParser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.wiztools.commons.MultiValueMap;
import org.wiztools.commons.MultiValueMapLinkedHashSet;

/**
 *
 * @author swathithangavel
 */
public class UrlParamParserTest {
    
    public UrlParamParserTest() {
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
     * Test of parse method, of class UrlParamParser.
     */
    @Test
    public void testParse() {
        System.out.println("parse");
        String text = "key1=value1&key2=value2";
        MultiValueMap<String, String> expResult = new MultiValueMapLinkedHashSet<>();
        expResult.put("key1", "value1");
        expResult.put("key2", "value2");
        MultiValueMap<String, String> result = UrlParamParser.parse(text);
        assertEquals(expResult, result);
    }
    
}
