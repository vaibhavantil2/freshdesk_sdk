package com.freshdesk.sdk.plug.validators;

import com.freshdesk.sdk.FAException;
import com.freshdesk.sdk.ManifestContents;
import com.freshdesk.sdk.SdkException;
import com.freshdesk.sdk.Util;
import com.freshdesk.sdk.validators.BaseRunValidator;
import com.freshdesk.sdk.validators.RuntimeValidator;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by raghu on 15/03/16.
 */

@RuntimeValidator
public class AppDirValidator extends BaseRunValidator {

    private static final String appDirName = "app";

    public void validate() throws FAException {
        File appDir  = new File(prjDir, appDirName);
        try {
            Util.appDirValidator(appDir);
        }
        catch (FileNotFoundException ex) {
            throw new FAException("Files missing in app dir.", ex);
        }
    }
}
