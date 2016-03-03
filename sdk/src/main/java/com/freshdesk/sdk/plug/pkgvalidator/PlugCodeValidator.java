package com.freshdesk.sdk.plug.pkgvalidator;

import com.freshdesk.sdk.pkgvalidator.BasePrePkgValidator;
import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.pkgvalidator.PrePackageValidator;
import com.freshdesk.sdk.SdkException;
import com.freshdesk.sdk.plug.PlugFile;
import java.io.File;
import java.util.Arrays;

/**
 *
 * @author subhash
 */
@PrePackageValidator
public class PlugCodeValidator extends BasePrePkgValidator {
    
    private static final String[] PLUG_FILES = PlugFile.getAllFiles();

    @Override
    public void validate() throws SdkException {
        File appDir = new File(prjDir, "app");
        
        for(File f: appDir.listFiles()) {
            final String fileName = f.getName();  
            if(Arrays.asList(PLUG_FILES).contains(fileName)) {
                if(!(f.exists() && f.isFile() && f.canRead())) {
                    throw new SdkException(
                        ExitStatus.CMD_FAILED,
                            "Plug files missing / not readable: ");
                }
            }
            else {
                throw new SdkException(ExitStatus.CMD_FAILED,
                    "Unsupported file detected in `app/': " + fileName);
            }
        }
    }
    
}
