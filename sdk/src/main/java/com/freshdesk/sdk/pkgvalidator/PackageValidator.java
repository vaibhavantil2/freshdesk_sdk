package com.freshdesk.sdk.pkgvalidator;

import com.freshdesk.sdk.SdkException;

/**
 *
 * @author subhash
 */
public interface PackageValidator {
    void validate() throws SdkException;
}
