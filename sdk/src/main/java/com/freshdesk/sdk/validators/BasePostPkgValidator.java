package com.freshdesk.sdk.validators;

import java.io.File;

/**
 *
 * @author subhash
 */
public abstract class BasePostPkgValidator implements PostPkgValidator {
    protected File pkgFile;
    
    @Override
    public void setPkgFile(File pkgFile) {
        this.pkgFile = pkgFile;
    }
}
