package com.freshdesk.sdk.plug.validators;

import com.freshdesk.sdk.FAException;
import com.freshdesk.sdk.InitExecutor;
import com.freshdesk.sdk.plug.PlugFile;
import com.freshdesk.sdk.plug.validators.AppDirValidator;
import com.freshdesk.sdk.validators.BaseRunValidator;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static junit.framework.TestCase.fail;

/**
 * Created by raghu on 15/03/16.
 */
public class AppDirValidatorTest {

    @Test
    public void testValidate() throws Exception {
        File f = File.createTempFile("fa_", "_tst");
        f.delete();
        f.mkdir();
        try {
            InitExecutor init = new InitExecutor();
            init.arguments = Arrays.asList(new String[]{"plug"});
            init.init(f);
            init.execute();

            BaseRunValidator validator = new AppDirValidator();
            validator.setPrjDir(f);
            validator.validate();
        }
        finally {
            f.delete();
        }
    }

    @Test
    public void testValidateMissingFile() throws Exception {
        File f = File.createTempFile("fa_", "_tst");
        f.delete();
        f.mkdir();
        try {
            InitExecutor init = new InitExecutor();
            init.arguments = Arrays.asList(new String[]{"plug"});
            init.init(f);
            init.execute();

            // Delete file for negative test case
            File appDir = new File(f, "app");
            File htmlFile = new File(appDir, PlugFile.toString(PlugFile.HTML));
            htmlFile.delete();

            try {
                BaseRunValidator validator = new AppDirValidator();
                validator.setPrjDir(f);
                validator.validate();
                fail();
            }

            catch (FAException ex) {
                //expected output
            }
        }
        finally {
            f.delete();
        }
    }
}