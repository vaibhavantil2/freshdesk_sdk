package com.freshdesk.sdk.validators;

import java.io.File;

/**
 * Created by raghu on 15/03/16.
 */
public abstract class BaseRunValidator implements RunValidator {

    protected File prjDir;

    public void setPrjDir(File prjDir) {
        this.prjDir = prjDir;
    }
}
