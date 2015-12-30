package com.freshdesk.sdk;

import java.io.File;

/**
 *
 * @author subhash
 */
public abstract class AbstractInitExecutor extends BaseCommandExecutor {
    
    protected File dir;
    
    @Override
    public void init(File dir) throws SdkException {
        this.dir = dir;
    }
}
