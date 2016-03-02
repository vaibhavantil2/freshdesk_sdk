package com.freshdesk.sdk;

import io.airlift.airline.Command;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author subhash
 */
@Command(name = "clean")
public class CleanExecutor extends AbstractProjectExecutor {

    private static final Logger LOG = LogManager.getLogger(CleanExecutor.class);
    private static final List<String> CLEANABLE_DIRS = Collections.unmodifiableList(
            Arrays.asList(new String[]{
                "dist",
                "work"
            }));

    @Override
    public void execute() throws SdkException {
        ExitStatus exitStatus = null;
        String errorMsg = null;

        for (String cleanableDir : CLEANABLE_DIRS) {
            File dir = new File(prjDir, cleanableDir);
            if (dir.exists() && dir.isDirectory()) {
                final File[] files = dir.listFiles();
                if (files != null) {
                    for (final File f : files) {
                        boolean ret = f.delete();
                        if (!ret) {
                            errorMsg = "Unable to delete file: " + f.getName();
                            exitStatus = ExitStatus.CMD_FAILED;
                        }
                    }
                }
                boolean ret = dir.delete();
                if (!ret) {
                    errorMsg = "Unable to delete dir: " + dir.getName();
                    exitStatus = ExitStatus.CMD_FAILED;
                } else if (verbose) {
                    System.out.println("Removed: " + dir.getName() + "/");
                }
            }
        }
        
        if (exitStatus != null) {
            throw new SdkException(exitStatus, errorMsg);
        }
    }

}
