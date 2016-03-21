package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.Constants;
import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.ManifestContents;
import com.freshdesk.sdk.SdkException;
import com.freshdesk.sdk.plug.run.AppIdNSResolver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.zeroturnaround.zip.commons.FileUtils;

/**
 *
 * @author raghav
 */
public class BuildFileHandler {
    
    private final File prjDir;
    private final File appDir;
    private final File buildDir;
    private final File indexFile;
    private final ManifestContents mf;

    public BuildFileHandler(File prjDir, ManifestContents mf) {
        this.prjDir = prjDir;
        this.appDir = new File(prjDir, "app");
        this.buildDir = new File(prjDir, "build");
        this.indexFile = new File(buildDir, "index.html");
        this.mf = mf;
    }
    
    public void generateBuildFile() {
        if(!buildDir.isDirectory()) {
            buildDir.mkdirs();
        }
        try {
            AppIdNSResolver ns = new AppIdNSResolver(prjDir);
            String response = new PlugContentUnifier(appDir, mf,
                        ns.getNamespace()).getPlugResponse();
            OutputStream os = new FileOutputStream(indexFile);
            response = replaceAssetUrl(replaceAppId(response, ns));
            os.write(response.getBytes());
            os.close();
        }
        catch (IOException ex) {
            throw new SdkException(ExitStatus.CMD_FAILED, "Could not generate build file.");
        }
    }
    
    public void deleteBuildDir() {
        try {
            FileUtils.deleteDirectory(buildDir);
        } catch (IOException ex) {
            throw new SdkException(ExitStatus.CMD_FAILED, "Couldn't delete build dir.");
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
