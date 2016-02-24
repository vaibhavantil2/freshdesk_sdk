package com.freshdesk.sdk;

import com.freshdesk.sdkcommon.Versions;
import io.airlift.airline.Command;

/**
 *
 * @author subhash
 */
@Command(name = "info")
public class InfoExecutor extends AbstractProjectExecutor {

    @Override
    public void execute() throws SdkException {
        System.out.println("# Project details:");
        System.out.println("  Project type: " + extnType);
        System.out.println("  Source encoding: " + manifest.getCharset());
        System.out.println("  Platform version: " + manifest.getPlatformVersion());
        System.out.println();
        System.out.println("# Running on:");
        System.out.println("  Sdk version: " + Versions.SDK_VERSION);
        System.out.println("  Java version: " + Constants.JAVA_VER);
    }
    
}
