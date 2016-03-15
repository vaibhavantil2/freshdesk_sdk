package com.freshdesk.sdk.plug.validators;

import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.SdkException;
import com.freshdesk.sdk.validators.BasePostPkgValidator;
import com.freshdesk.sdk.validators.PostPackageValidator;

/**
 *
 * @author subhash
 */
@PostPackageValidator
public class PkgSizeValidator extends BasePostPkgValidator {
    
    private static final long MAX_PKG_SIZE = 5000000; // 5 MB in bytes

    @Override
    public void validate() throws SdkException {
        long size = pkgFile.length();
        if(size > MAX_PKG_SIZE) {
            throw new SdkException(ExitStatus.CMD_FAILED,
                    "Package size exceeds allowed limit: dist/" + pkgFile.getName());
        }
    }
    
}
