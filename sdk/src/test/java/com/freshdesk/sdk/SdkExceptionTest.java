package com.freshdesk.sdk;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author swathithangavel
 */
public class SdkExceptionTest {
    
    public SdkExceptionTest() {
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

    @Test
    public void testSomeMethod() {
        Throwable throwable = new Throwable();
        SdkException exception2 = new SdkException(ExitStatus.CMD_FAILED, "Test method");
        SdkException exception3 = new SdkException(ExitStatus.CMD_FAILED, throwable);
        SdkException exception4 = new SdkException(ExitStatus.CMD_FAILED, "Test method", throwable);
    }
    
}
