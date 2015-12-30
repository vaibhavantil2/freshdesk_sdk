package com.freshdesk.sdk.plug.pkgvalidator;

import com.freshdesk.sdk.pkgvalidator.BasePrePkgValidator;
import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.pkgvalidator.PrePackageValidator;
import com.freshdesk.sdk.SdkException;
import java.io.File;

/**
 *
 * @author subhash
 */
@PrePackageValidator
public class PlugCodeValidator extends BasePrePkgValidator {
    
    private static final String PLUG_FILE = "index.html";

    @Override
    public void validate() throws SdkException {
        File libDir = new File(prjDir, "lib");
        
        for(File f: libDir.listFiles()) {
            final String fileName = f.getName();
            if(fileName.equals(PLUG_FILE)) {
                if(!(f.exists() && f.isFile() && f.canRead())) {
                    throw new SdkException(
                        ExitStatus.CMD_FAILED,
                            "Plug code missing / not readable: " + PLUG_FILE);
                }
            }
            else {
                throw new SdkException(ExitStatus.CMD_FAILED,
                    "Unsupported file detected in `lib/': " + fileName);
            }
        }
    }
    
}
