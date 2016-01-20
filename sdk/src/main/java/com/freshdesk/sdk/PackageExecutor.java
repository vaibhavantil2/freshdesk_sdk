package com.freshdesk.sdk;

import com.freshdesk.sdk.pkgvalidator.PkgValidatorUtil;
import com.freshdesk.sdk.pkgvalidator.PostPkgValidator;
import com.freshdesk.sdk.pkgvalidator.PrePkgValidator;
import io.airlift.airline.Command;
import java.io.File;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author subhash
 */
@Command(name = "package")
public class PackageExecutor extends AbstractProjectExecutor {
    
    private static final Logger LOG = LogManager.getLogger(PackageExecutor.class);
    
    private static final String DIST_DIR = "dist";
    
    @Override
    public final void execute() throws SdkException {
        final File distDir = new File(prjDir, DIST_DIR);
        if(!distDir.exists()) {
            if(!distDir.mkdir()) {
                throw new SdkException(ExitStatus.CMD_FAILED,
                        "Dir creation failed: " + distDir.getName());
            }
        }

        // 1. Run pre-package validations:
        for(PrePkgValidator validator: getPrePkgValidators()) {
            // Instantiate:
            validator.setPrjDir(prjDir);
            validator.setManifest(manifest);
            validator.setIparamConfig(iparamConfig);
            validator.setIParam(iparams);
            
            // Now validate:
            validator.validate();
        }
        
        // 2. Create package:
        final File pkg = new File(distDir, getPackageName());
        new PkgZipper(verbose).pack(prjDir, pkg);

        if(verbose) {
            System.out.println("Created package: dist/" + pkg.getName());
        }
        
        // 3. Run post-package validations:
        for(PostPkgValidator validator: getPostPkgValidators()) {
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
    
    protected static String cleanFileName(String input) {
        return input.toLowerCase().replaceAll("[^a-z0-9.]", "-");
    }
    
    protected String getPackageName() {
        return cleanFileName(prjDir.getAbsoluteFile().getParentFile().getName())
                + "." + getPkgExtn();
    }

    private String getPkgExtn() {
        if(isPlugProj()) {
            return "plg";
        }
        else {
            return "zip";
        }
    }

    private Set<PrePkgValidator> getPrePkgValidators() {
        try {
            return PkgValidatorUtil.getPrePkgValidators(extnType);
        }
        catch(InstantiationException | IllegalAccessException ex) {
            throw new SdkException(ExitStatus.CMD_FAILED, ex);
        }
    }
    
    private Set<PostPkgValidator> getPostPkgValidators() {
        try {
            return PkgValidatorUtil.getPostPkgValidators(extnType);
        }
        catch(InstantiationException | IllegalAccessException ex) {
            throw new SdkException(ExitStatus.CMD_FAILED, ex);
        }
    }
}
