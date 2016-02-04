package com.freshdesk.sdkupdate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.wiztools.appupdate.Version;
import org.wiztools.commons.FileUtil;
import org.wiztools.commons.ZipUtil;

/**
 *
 * @author subhash
 */
public class InstallUtil implements Rollbackable {
    
    private final File destDir;
    private final File source;
    private final Version version;
    
    private final File unxExecVer = new File(Constants.FRSH_HOME + "/config/exec_version");
    private final File winExecVer = new File(Constants.FRSH_HOME + "/config/exec_version.bat");

    private final File bakUnxExecVer = new File(Constants.FRSH_HOME + "/config/exec_version.bak");
    private final File bakWinExecVer = new File(Constants.FRSH_HOME + "/config/exec_version.bat.bak");
    
    public InstallUtil(File source, File destDir, Version latest) {
        this.destDir = destDir;
        this.source = source;
        this.version = latest;
    }
    
    public void install() {
        try {
            // Compute the final installed dir:
            String installedDirName = getNameWithoutExtn(source.getName());
            
            // Unzip:
            ZipUtil.unzip(source, destDir);
            
            // Set executable permission:
            File frshCmd = new File(destDir, installedDirName + "/bin/frsh");
            frshCmd.setExecutable(true, false); // set +x for all users
            
            // Create `$FRSH_HOME/config/exec_version` & `$FRSH_HOME/config/exec_version.bat`:
            updateExecVersionConfig();
        }
        catch(IOException ex) {
            throw new SdkUpdateException(ex);
        }
    }
    
    private String getNameWithoutExtn(String name) {
        return name.substring(0, name.lastIndexOf('.'));
    }
    
    // Overwrites the existing config:
    protected void updateExecVersionConfig() throws IOException {
        // 1. Rename exisiting config to .bak extension:
        Files.move(unxExecVer.toPath(),
                bakUnxExecVer.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        Files.move(winExecVer.toPath(),
                bakWinExecVer.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        
        // 2. Write version to files:
        FileUtil.writeString(unxExecVer, "version=" + version,
                StandardCharsets.US_ASCII);
        FileUtil.writeString(winExecVer, "version=" + version + "\r\n",
                StandardCharsets.US_ASCII);
    }

    @Override
    public void rollback() {
        try {
            // Rename .bak files to their original name:
            Files.move(bakUnxExecVer.toPath(),
                    unxExecVer.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.move(bakWinExecVer.toPath(),
                    winExecVer.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch(IOException ex) {
            System.err.println("Rollback failed: moving .bak configs failed!");
            ex.printStackTrace(System.err);
        }
    }
}
