package com.freshdesk.sdk;

import java.io.File;

/**
 *
 * @author subhash
 */
public interface CommandExecutor {
    void init(File prjDir) throws SdkException;
    void execute() throws SdkException;
}
