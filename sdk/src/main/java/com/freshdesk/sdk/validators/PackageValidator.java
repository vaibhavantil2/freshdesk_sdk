package com.freshdesk.sdk.validators;

import com.freshdesk.sdk.SdkException;

/**
 *
 * @author subhash
 */
public interface PackageValidator {
    void validate() throws SdkException;
}
