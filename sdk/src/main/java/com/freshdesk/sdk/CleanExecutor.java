package com.freshdesk.sdk;

import io.airlift.airline.Command;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zeroturnaround.zip.commons.FileUtils;

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
                "work",
                "build"
            }));

    @Override
    public void execute() throws SdkException {
        ExitStatus exitStatus = null;
        String errorMsg = null;

        for (String cleanableDir : CLEANABLE_DIRS) {
            try {
                FileUtils.deleteDirectory(new File(prjDir, cleanableDir));
                if (verbose) {
                    System.out.println("Removed: " + cleanableDir + "/");
                }
            }
            catch (IOException e) {
                throw new SdkException(ExitStatus.CMD_FAILED, "Unable to delete dir: " + cleanableDir);
            }
        }
        
        if (exitStatus != null) {
            throw new SdkException(exitStatus, errorMsg);
        }
    }

}
