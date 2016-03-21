package com.freshdesk.sdk;

import com.freshdesk.sdk.plug.BuildFileHandler;
import com.freshdesk.sdk.plug.DigestFileHandler;
import com.freshdesk.sdk.plug.PackageValidations;
import io.airlift.airline.Command;
import java.io.File;


/**
 *
 * @author subhash
 */
@Command(name = "package")
public class PackageExecutor extends AbstractProjectExecutor {
    
    
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
        
        PackageValidations pv = new PackageValidations(prjDir, manifest, iparamConfig, iparams, extnType);
        pv.runPrePkgValidations();
       
        final File pkg = new File(distDir, getPackageName());
        BuildFileHandler bfh = new BuildFileHandler(prjDir, manifest);
        DigestFileHandler dfh = new DigestFileHandler(prjDir);
        
        bfh.generateBuildFile();
        dfh.generateDigestFile();
        
        new PkgZipper(verbose).pack(prjDir, pkg);
        
        bfh.deleteBuildDir();
        dfh.deleteDigestFile();
        
        if(verbose) {
            System.out.println("Created package: dist/" + pkg.getName());
        }
        pv.runPostPkgValidations(pkg);
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
}
