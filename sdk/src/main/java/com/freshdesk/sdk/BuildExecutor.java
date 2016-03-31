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
      try {
        BuildFileHandler bfh = new BuildFileHandler(prjDir, manifest);
        bfh.generateBuildFile();
        if(verbose) {
          System.out.println("Generated build file.");
        }
      }
      catch(SdkException ex) {
        if(verbose) {
          System.out.println("Error while generating build file.");
        }
      }
    }
    
}
