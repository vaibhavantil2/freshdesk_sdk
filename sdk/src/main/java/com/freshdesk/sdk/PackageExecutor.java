package com.freshdesk.sdk;

import com.freshdesk.sdk.validators.PkgValidatorUtil;
import com.freshdesk.sdk.validators.PostPkgValidator;
import com.freshdesk.sdk.validators.PrePkgValidator;
import com.freshdesk.sdk.plug.PlugContentUnifier;
import com.freshdesk.sdk.plug.PlugFile;
import com.freshdesk.sdk.plug.run.AppIdNSResolver;
import io.airlift.airline.Command;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private List<File> FILES_TO_DIGEST;

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

        // For build dir:
        ManifestContents mf = new ManifestContents(prjDir);
        File appDir = new File(prjDir, "app");
        File digestFile = new File(prjDir, "digest.md5");
        String response = null;
        try {
            response = new PlugContentUnifier(appDir, mf,
                    new AppIdNSResolver(prjDir).getNamespace()).getPlugResponse();
            AppIdNSResolver ns = new AppIdNSResolver(prjDir);
            response = replaceAssetUrl(replaceAppId(response, ns));
            File buildDir = new File(prjDir, "build");
            if(!buildDir.isDirectory()) {
                buildDir.mkdirs();
            }
            File indexFile = new File(buildDir, "index.html");
            OutputStream os = new FileOutputStream(indexFile);
            os.write(response.getBytes());
            os.close();
            FILES_TO_DIGEST = new ArrayList<>();
            for (String fileName : PlugFile.getAllFiles()) {
                FILES_TO_DIGEST.add(new File(appDir, fileName));
            }
            FILES_TO_DIGEST.add(indexFile);
            
            try (OutputStream osd = new FileOutputStream(digestFile)) {
                String hashCode = DigestUtil.getHashCodeForFiles(FILES_TO_DIGEST);
                osd.write(hashCode.getBytes());
            }
        }
        catch (IOException e) {
            throw new SdkException(ExitStatus.CMD_FAILED, e);
        }

        new PkgZipper(verbose).pack(prjDir, pkg);
        
        digestFile.delete();

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

    private String replaceAppId(String response,AppIdNSResolver ns){
        return response.replaceAll(ns.getLiquidVal(), "{{" + ns.getLiquidKey() + "}}");
    }

    private String replaceAssetUrl(String response){
        Matcher m = Pattern.compile("assets/(.*)\\w").matcher(response);
        while (m.find()) {
            String fileName =  m.group(0).split("/")[1];
            response = response.replace(Constants.URL_SCHEME + "://" +
                                            Constants.LOCAL_SERVER_URL + ":" +
                                            Constants.SERVER_PORT +
                                            "/assets/" + fileName,
                                            "{{'" + fileName +"' | asset_url}}");
        }
        return response;
    }
}
