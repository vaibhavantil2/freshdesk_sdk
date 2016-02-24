package com.freshdesk.sdk;

import com.freshdesk.sdkcommon.Versions;
import io.airlift.airline.Command;
import java.io.File;

/**
 *
 * @author subhash
 */
@Command(name = "version")
public class VersionExecutor extends BaseCommandExecutor {

    @Override
    public void init(File prjDir) throws SdkException {
        // do nothing!
    }

    @Override
    public void execute() throws SdkException {
        System.out.println("SDK version: " + Versions.SDK_VERSION);
        System.out.println("Platform version: " + Versions.PLATFORM_VERSION);
        System.out.println("Running on Java version: " + Constants.JAVA_VER);
    }
    
}
