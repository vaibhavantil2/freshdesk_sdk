package com.freshdesk.sdk;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author subhash
 */
public class UtilTest {
    
    /**
     * Test of skipFirst method, of class Util.
     */
    @org.junit.Test
    public void testSkipFirst() {
        System.out.println("skipFirst");
        String[] arr = new String[]{"one", "two"};
        String[] expResult = new String[]{"two"};
        String[] result = Util.skipFirst(arr);
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of skipFirst method, of class Util.
     */
    @org.junit.Test
    public void testSkipFirstWithoutInput() {
        System.out.println("skipFirst");
        String[] arr = new String[]{};
        String[] expResult = new String[]{};
        String[] result = Util.skipFirst(arr);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getMimeType method, of class Util.
     */
    @Test
    public void testGetMimeTypeCss() {
        System.out.println("testGetMimeTypeCss");
        String fileName = "abc.css";
        String expResult = "text/css";
        String result = Util.getMimeType(fileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getMimeType method, of class Util.
     */
    @Test
    public void testGetMimeTypeJs() {
        System.out.println("testGetMimeTypeJs");
        String fileName = "abc.js";
        String expResult = "text/javascript";
        String result = Util.getMimeType(fileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getMimeType method, of class Util.
     */
    @Test
    public void testGetMimeTypeJpg() {
        System.out.println("testGetMimeTypeJpg");
        String fileName = "abc.jpg";
        String expResult = "image/jpeg";
        String result = Util.getMimeType(fileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getMimeType method, of class Util.
     */
    @Test
    public void testGetMimeTypePng() {
        System.out.println("testGetMimeTypePng");
        String fileName = "abc.png";
        String expResult = "image/png";
        String result = Util.getMimeType(fileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getMimeType method, of class Util.
     */
    @Test
    public void testGetMimeTypeGif() {
        System.out.println("testGetMimeTypeGif");
        String fileName = "abc.gif";
        String expResult = "image/gif";
        String result = Util.getMimeType(fileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getMimeType method, of class Util.
     */
    @Test
    public void testGetMimeTypeJson() {
        System.out.println("testGetMimeTypeJson");
        String fileName = "abc.json";
        String expResult = "application/json";
        String result = Util.getMimeType(fileName);
        assertEquals(expResult, result);
    }
    
}
