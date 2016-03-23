package com.freshdesk.sdk.validators;

import java.io.File;

/**
 *
 * @author subhash
 */
public interface PostPkgValidator extends PackageValidator {
    void setPkgFile(File pkgFile);
}
