package com.freshdesk.sdk.validators;

import java.io.File;

/**
 * Created by raghu on 15/03/16.
 */
public interface RunValidator extends PackageValidator {
    void setPrjDir(File prjDir);
}
