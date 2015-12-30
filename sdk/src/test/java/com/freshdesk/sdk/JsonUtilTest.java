package com.freshdesk.sdk;

import com.freshdesk.sdk.JsonUtil;
import java.util.HashMap;
import java.util.Map;
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
public class JsonUtilTest {
    
    public JsonUtilTest() {
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
     * Test of jsonToMap method, of class Util.
     */
    @Test
    public void testJsonToMap() {
        System.out.println("jsonToMap");
        String jsonStr = "{\"foo\":\"bar\"}";
        Map<String, Object> expResult = new HashMap<>();
        expResult.put("foo", "bar");
        Map<String, Object> result = JsonUtil.jsonToMap(jsonStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of maptoJson method, of class Util.
     */
    @Test
    public void testMaptoJson() {
        System.out.println("maptoJson");
        Map<String, Object> map = new HashMap<>();
        map.put("foo", "bar");
        String expResult = "{\"foo\":\"bar\"}";
        String result = JsonUtil.maptoJson(map);
        assertEquals(expResult, result);
    }
}
