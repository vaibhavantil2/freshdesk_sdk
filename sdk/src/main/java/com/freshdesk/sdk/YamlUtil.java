package com.freshdesk.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author subhash
 */
public final class YamlUtil {
    
    private static final Logger LOGGER = LogManager.getLogger(YamlUtil.class.getName());

    private static final String[] MANDATORY_FIELDS = {"display_name", "description", "type"};
    private static final String[] OPTIONAL_FIELDS = {"options", "default_value", "required"};
    private static final String[] ALL_FIELDS = Stream.concat(
            Arrays.stream(MANDATORY_FIELDS), Arrays.stream(OPTIONAL_FIELDS))
            .toArray(String[]::new);
    
    static {
        Arrays.sort(MANDATORY_FIELDS);
        Arrays.sort(OPTIONAL_FIELDS);
        Arrays.sort(ALL_FIELDS);
    }
    
    private YamlUtil() {}
    
    public static Map<String, Object> getYaml(
            final File yamlFile, Charset charset) throws IOException {
        if((!yamlFile.exists()) || (!yamlFile.canRead())) {
            throw new FileNotFoundException("File not found / cannot read: " + yamlFile.getAbsolutePath());
        }
        
        final Yaml yaml = new Yaml();
        try (final Reader reader = new InputStreamReader(
                new FileInputStream(yamlFile), charset)) {
            final Map<String, Object> yamlObj = (Map<String, Object>) yaml.load(reader);
            if(yamlObj != null) {
                return yamlObj.entrySet().stream().collect(
                    Collectors.toMap(
                            e -> e.getKey(), e -> e.getValue().toString()));
            }
            return Collections.EMPTY_MAP;
        }
    }
    
    public static void validate(final File yamlFile, Charset c) throws FileNotFoundException, IOException {
        if((!yamlFile.exists()) || (!yamlFile.canRead())) {
            throw new FileNotFoundException("yamlFile not found: " + yamlFile.getAbsolutePath());
        }
        
        final Yaml yaml = new Yaml();
        try (final Reader reader = new InputStreamReader(
                new FileInputStream(yamlFile), c)) {
            final Map<String, Object> yamlObj = (Map<String, Object>) yaml.load(reader);
            if(yamlObj == null) {
                LOGGER.info("Ignoring empty iparam file");
                return;
            }
            final Map<String, Object> innerFields = (Map<String, Object>)yamlObj.get("iparam");
            if(innerFields != null) {
                for(final Map.Entry<String, Object> e: innerFields.entrySet()) {
                    if(!validateYaml((Map<String, Object>) e.getValue())) {
                        throw new FAException("Yaml file not valid" + yamlFile.getName());
                    }
                }
            }
            else {
                throw new FAException("Yaml file not valid " + yamlFile.getName());
            }
        }
    }
    
    private static boolean validateYaml(final Map<String, Object> obj) throws IOException {
         if(checkMandatoryFields(obj)) {
            for(final String key : obj.keySet()) {
                if(Arrays.binarySearch(ALL_FIELDS, key) < 0) {
                    throw new FAException("Invalid field " + key + " encountered in Installation Params " + obj.toString());
                }
            }
            return true;
        }
        return false;
    }
    
    private static boolean checkMandatoryFields(final Map<String, Object> obj) {
        final String[] mandatoryFields = MANDATORY_FIELDS;
        for(final String field : mandatoryFields) {
            if(!obj.containsKey(field)) {
                return false;
            }
        }
        return validateFieldType(obj.get("type").toString(), obj);
    }
    
    private static boolean validateFieldType(final String fieldType, final Map<String, Object> obj) {
        switch(fieldType) {
            case "text" : 
                break;
            case "dropdown" :
                final Object options = obj.get("options");
                if(options == null) {
                    return false;
                }
                break;
            default :
                throw new FAException("Invalid Field Type : " + fieldType);
        }
        return true;
    }
    
}
