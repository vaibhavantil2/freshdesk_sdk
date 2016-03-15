package com.freshdesk.sdk.plug.validators;

import com.freshdesk.sdk.*;
import com.freshdesk.sdk.plug.validators.ScssLiquidsValidator;
import com.freshdesk.sdk.validators.BasePrePkgValidator;
import com.freshdesk.sdk.plug.PlugFile;
import org.junit.Test;
import org.wiztools.commons.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by raghu on 14/03/16.
 */
public class ScssLiquidsValidatorTest {

    @Test
    public void testValidateValidCode() throws Exception {
        File f = File.createTempFile("fa_", "_tst");
        f.delete();
        f.mkdir();
        try {
            InitExecutor init = new InitExecutor();
            init.arguments = Arrays.asList(new String[]{"plug"});
            init.init(f);
            init.execute();

            ManifestContents mf = new ManifestContents(f);
            IParamConfig ipc = new IParamConfig(f, mf.getCharset());
            IParamContents iparam = new IParamContents(f, mf.getCharset());

            BasePrePkgValidator validator = new ScssLiquidsValidator();
            validator.setPrjDir(f);
            validator.setManifest(mf);
            validator.setIparamConfig(ipc);
            validator.setIParam(iparam);

            validator.validate();
        }
        finally {
            f.delete();
        }
    }

    @Test
    public void testValidateInvalidLiquid() throws Exception {
        File f = File.createTempFile("fa_", "_tst");
        f.delete();
        f.mkdir();
        try {
            InitExecutor init = new InitExecutor();
            init.arguments = Arrays.asList(new String[]{"plug"});
            init.init(f);
            init.execute();

            ManifestContents mf = new ManifestContents(f);
            IParamConfig ipc = new IParamConfig(f, mf.getCharset());
            IParamContents iparam = new IParamContents(f, mf.getCharset());

            BasePrePkgValidator validator = new ScssLiquidsValidator();
            validator.setPrjDir(f);
            validator.setManifest(mf);
            validator.setIparamConfig(ipc);
            validator.setIParam(iparam);

            // Change the content (add an unsupported liquid) of the scss file
            // to check if validator works.

            File appDir = new File(f, "app");
            File scssFile = new File(appDir, PlugFile.toString(PlugFile.SCSS));
            String scssContent = FileUtil.getContentAsString(
                               scssFile, new ManifestContents(f).getCharset());
            scssContent = scssContent.replace("app_id","ticket_id");
            OutputStream os = new FileOutputStream(scssFile);
            os.write(scssContent.getBytes());
            os.close();

            validator.validate();
            fail();

        }
        catch (SdkException ex) {
            // Has to come here as it is an invalid liquid
        }
        finally {
            f.delete();
        }
    }

    @Test
    public void testValidateInvalidLiquidFilter() throws Exception {
        File f = File.createTempFile("fa_", "_tst");
        f.delete();
        f.mkdir();
        try {
            InitExecutor init = new InitExecutor();
            init.arguments = Arrays.asList(new String[]{"plug"});
            init.init(f);
            init.execute();

            ManifestContents mf = new ManifestContents(f);
            IParamConfig ipc = new IParamConfig(f, mf.getCharset());
            IParamContents iparam = new IParamContents(f, mf.getCharset());

            BasePrePkgValidator validator = new ScssLiquidsValidator();
            validator.setPrjDir(f);
            validator.setManifest(mf);
            validator.setIparamConfig(ipc);
            validator.setIParam(iparam);

            // Change the content (add an unsupported liquid) of the scss file
            // to check if validator works.

            File appDir = new File(f, "app");
            File scssFile = new File(appDir, PlugFile.toString(PlugFile.SCSS));
            String scssContent = FileUtil.getContentAsString(
                scssFile, new ManifestContents(f).getCharset());
            scssContent = scssContent.replace("asset_url", "wrong_url");
            OutputStream os = new FileOutputStream(scssFile);
            os.write(scssContent.getBytes());
            os.close();

            validator.validate();
            fail();

        }
        catch (SdkException ex) {
            // Has to come here as it is an invalid liquid filter
        }
        finally {
            f.delete();
        }
    }
}