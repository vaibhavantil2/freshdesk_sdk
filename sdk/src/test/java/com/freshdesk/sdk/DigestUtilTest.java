package com.freshdesk.sdk;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author raghav
 */
public class DigestUtilTest {
    
    public DigestUtilTest() {
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
     * Test of getHashCodeForFiles method, of class DigestUtil.
     */
    @Test
    public void testGetHashCodeForFiles() {
        System.out.println("getHashCodeForFiles");
        List<File> filesToDigest = new ArrayList<>();
        filesToDigest.add(new File("src/test/resources/digest-test.txt"));
        String expectedResult = "8b1a9953c4611296a827abf8c47804d7";
        if( !(expectedResult.equals(DigestUtil.getHashCodeForFiles(filesToDigest)))) {
            Assert.fail();
        }
    }
}
