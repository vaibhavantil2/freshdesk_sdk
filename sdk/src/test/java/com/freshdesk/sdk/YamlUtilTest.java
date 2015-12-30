package com.freshdesk.sdk;

import com.freshdesk.sdk.YamlUtil;
import static java.nio.charset.StandardCharsets.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class YamlUtilTest {
    
    public YamlUtilTest() {
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
     * Test of getYaml method, of class YamlUtil.
     */
    @Test
    public void testGetYaml() throws Exception {
        System.out.println("getYaml1");
        File yamlFile = new File("src/test/resources/iparam.yml");
        Map<String, String> expResult = new HashMap<>();
        expResult.put("key", "value");
        System.out.println("\n\n" + yamlFile.getAbsolutePath() + "\n\n");
        Map<String, Object> result = YamlUtil.getYaml(yamlFile, UTF_8);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getYaml method, of class YamlUtil.
     */
    @Test
    public void testGetInvalidYaml() throws Exception {
        System.out.println("getYaml1");
        File yamlFile = new File("src/test/resources/iparam1.yml");
        Map<String, String> expResult = new HashMap<>();
        System.out.println("\n\n" + yamlFile.getAbsolutePath() + "\n\n");
        try {
            Map<String, Object> result = YamlUtil.getYaml(yamlFile, UTF_8);
            fail("Unpreset YAML not failing with Exception!");
        }
        catch(FileNotFoundException ex) {
            // expected!
        }
    }
    
    @Test
    public void testValidateYaml() throws IOException {
        System.out.println("ValidateYaml");
        final File prjDir = new File("src/test/resources/app-prj");
        File yamlfile = new File(prjDir, "run/iparam_en.yml");
    }
    
    @Test
    public void testValidateInvalidYaml() throws IOException {
        System.out.println("ValidateYaml");
        final File prjDir = new File("src/test/resources/app-prj");
        File yamlfile = new File(prjDir, "run/iparam_en1.yml");
    }
    
}
