package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.ExtnType;
import com.freshdesk.sdk.IParamConfig;
import com.freshdesk.sdk.IParamContents;
import com.freshdesk.sdk.ManifestContents;
import com.freshdesk.sdk.SdkException;
import com.freshdesk.sdk.validators.PkgValidatorUtil;
import com.freshdesk.sdk.validators.PostPkgValidator;
import com.freshdesk.sdk.validators.PrePkgValidator;
import java.io.File;
import java.util.Set;

/**
 *
 * @author raghav
 */
public class PackageValidations {
    
    private final File prjDir;
    private final ManifestContents manifest;
    private final IParamConfig iparamConfig;
    private final IParamContents iparams;
    private final ExtnType type;

    public PackageValidations(File prjDir, ManifestContents manifest, IParamConfig iparamConfig, IParamContents iparams, ExtnType type) {
        this.prjDir = prjDir;
        this.manifest = manifest;
        this.iparamConfig = iparamConfig;
        this.iparams = iparams;
        this.type = type;
    }
    
    public void runPrePkgValidations() {
        for(PrePkgValidator validator: getPrePkgValidators(type)) {
            // Instantiate:
            validator.setPrjDir(prjDir);
            validator.setManifest(manifest);
            validator.setIparamConfig(iparamConfig);
            validator.setIParam(iparams);
            
            // Now validate:
            validator.validate();
        }
    }
    
    public void runPostPkgValidations(final File pkg) {
        for(PostPkgValidator validator: getPostPkgValidators(type)) {
            try {
                // Instantiate:
                validator.setPkgFile(pkg);
                
                // Validate:
                validator.validate();
            }
            catch(SdkException ex) {
                // on validation error, delete package:
                pkg.delete();
                
                // rethrow the exception:
                throw new SdkException(ex);
            }
        }
    }
    
    private Set<PrePkgValidator> getPrePkgValidators(ExtnType extnType) {
        try {
            return PkgValidatorUtil.getPrePkgValidators(extnType);
        }
        catch(InstantiationException | IllegalAccessException ex) {
            throw new SdkException(ExitStatus.CMD_FAILED, ex);
        }
    }
    
    private Set<PostPkgValidator> getPostPkgValidators(ExtnType extnType) {
        try {
            return PkgValidatorUtil.getPostPkgValidators(extnType);
        }
        catch(InstantiationException | IllegalAccessException ex) {
            throw new SdkException(ExitStatus.CMD_FAILED, ex);
        }
    }
}
