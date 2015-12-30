package com.freshdesk.sdkupdate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.wiztools.commons.ZipUtil;

/**
 *
 * @author subhash
 */
public class InstallUtil implements Rollbackable {
    
    private final File destDir;
    private final File source;
    
    private File latestLink;
    
    public InstallUtil(File source, File destDir) {
        this.destDir = destDir;
        this.source = source;
    }
    
    public void install() {
        try {
            // Compute the final installed dir:
            String installedDirName = getNameWithoutExtn(source.getName());
            
            // Unzip:
            System.out.println("Unzipping...");
            ZipUtil.unzip(source, destDir);
            
            // Set executable permission:
            File frshCmd = new File(destDir, installedDirName + "/bin/frsh");
            frshCmd.setExecutable(true, false); // set +x for all users
            
            // Create symbolic link to `latest`:
            System.out.println("Updating links...");            
            File installedDir = new File(destDir, installedDirName);
            latestLink = createLatestLink(installedDir);
            
            // Feedback:
            System.out.println("Successfully installed.");
        }
        catch(IOException ex) {
            throw new SdkUpdateException(ex);
        }
    }
    
    private String getNameWithoutExtn(String name) {
        return name.substring(0, name.lastIndexOf('.'));
    }
    
    private File createLatestLink(File installedDir) throws IOException {
        File latest = new File(destDir, "latest");
        
        Files.deleteIfExists(latest.toPath());
        
        Path link = latest.toPath();
        Path target = installedDir.toPath();
        return Files.createSymbolicLink(link, target).toFile();
    }

    @Override
    public void rollback() {
        if(latestLink != null && latestLink.exists()) latestLink.delete();
    }
}
