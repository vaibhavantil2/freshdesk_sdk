package com.freshdesk.sdk;

import java.io.File;

/**
 *
 * @author subwiz
 */
public abstract class AbstractProjectExecutor extends BaseCommandExecutor {
    
    protected File prjDir;
    protected ManifestContents manifest;
    protected IParamConfig iparamConfig;
    protected IParamContents iparams;
    protected ExtnType extnType;

    // Util.validateProject(sourceDir, manifest.getPackageType());
    
    @Override
    public void init(File prjDir) throws SdkException {
        this.prjDir = prjDir;
        
        // Manifest:
        try {
            this.manifest = new ManifestContents(prjDir);
        }
        catch(FAException ex) {
            throw new SdkException(ExitStatus.CORRUPT_MANIFEST,
                    "Manifest load error. Is this a project dir?");
        }
        
        // Extension type:
        extnType = manifest.getPackageType();
        
        // IParam Config:
        try {
            iparamConfig = new IParamConfig(prjDir, manifest.getCharset());
        }
        catch(FAException ex) {
            throw new SdkException(ExitStatus.CORRUPT_IPARAM, ex.getMessage());
        }
        
        // IParam Data:
        try {
            iparams = new IParamContents(prjDir, manifest.getCharset());
        }
        catch(FAException ex) {
            throw new SdkException(ExitStatus.CORRUPT_IPARAM, ex.getMessage());
        }
    }
    
    protected boolean isAppProj() {
        return extnType == ExtnType.APP;
    }
    
    protected boolean isPlugProj() {
        return extnType == ExtnType.PLUG;
    }
}
