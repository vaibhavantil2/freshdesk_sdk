package com.freshdesk.sdk;

import com.freshdesk.sdk.plug.BuildFileHandler;
import io.airlift.airline.Command;

/**
 *
 * @author subhash
 */
@Command(name = "build")
public class BuildExecutor extends AbstractProjectExecutor {

    @Override
    public void execute() throws SdkException {
        BuildFileHandler bfh = new BuildFileHandler(prjDir, manifest);
        bfh.generateBuildFile();
        if(verbose) {
            System.out.println("Generated build file: build/index.html.");
        }
    }

}
