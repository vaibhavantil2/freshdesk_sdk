package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.DigestUtil;
import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.SdkException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raghav
 */
public class DigestFileHandler {
    
    private final File prjDir;
    private final File appDir;
    private final File buildDir;
    private final File indexFile;
    private final List<File> filesToDigest;
    private final File digestFile;

    public DigestFileHandler(File prjDir) {
        this.prjDir = prjDir;
        this.appDir = new File(this.prjDir, "app");
        this.buildDir = new File(this.prjDir, "build");
        this.indexFile = new File(buildDir, "index.html");
        filesToDigest = new ArrayList<>();
        digestFile = new File(prjDir, "digest.md5");
        getFilesToDigest();
    }
    
    private List<File> getFilesToDigest() {
        for(String fileName : AppFile.getAllFiles()) {
            filesToDigest.add(new File(appDir, fileName));
        }
        filesToDigest.add(indexFile);
        return filesToDigest;
    }
    
    public void generateDigestFile() {
        try (OutputStream osd = new FileOutputStream(digestFile)) {
            String hashCode = DigestUtil.getHashCodeForFiles(filesToDigest);
            osd.write(hashCode.getBytes());
        }
        catch (IOException e) {
            throw new SdkException(ExitStatus.CMD_FAILED, e);
        } 
    }
    
    public void deleteDigestFile() {
        digestFile.delete();
    }
}
