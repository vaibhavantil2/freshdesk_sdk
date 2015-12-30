package com.freshdesk.sdk;

import io.airlift.airline.Command;
import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author subhash
 */
@Command(name = "clean")
public class CleanExecutor extends AbstractProjectExecutor {
    
    private static final Logger LOG = LogManager.getLogger(CleanExecutor.class);
    
    @Override
    public void execute() throws SdkException {
        ExitStatus exitStatus = null;
        String errorMsg = null;
        
        final File distDir = new File(prjDir, "dist");
        if (distDir.exists()) {
            final File[] files = distDir.listFiles();
            if (files != null) {
                for (final File f : files) {
                    boolean ret = f.delete();
                    if(!ret) {
                        errorMsg = "Unable to delete file: " + f.getName();
                        exitStatus = ExitStatus.CMD_FAILED;
                    }
                }
            }
            boolean ret = distDir.delete();
            if(!ret) {
                errorMsg = "Unable to delete dir: " + distDir.getName();
                exitStatus = ExitStatus.CMD_FAILED;
            }
            else if(verbose) {
                System.out.println("Removed: " + distDir.getName() + "/");
            }
        }
        if(exitStatus != null) {
            throw new SdkException(exitStatus, errorMsg);
        }
    }

}
