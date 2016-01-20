package com.freshdesk.sdk.pkgvalidator;

import com.freshdesk.sdk.IParamConfig;
import com.freshdesk.sdk.ManifestContents;
import com.freshdesk.sdk.IParamContents;
import java.io.File;

/**
 *
 * @author subhash
 */
public interface PrePkgValidator extends PackageValidator {
    void setPrjDir(File prjDir);
    void setManifest(ManifestContents mf);
    void setIparamConfig(IParamConfig ip);
    void setIParam(IParamContents ip);
}
