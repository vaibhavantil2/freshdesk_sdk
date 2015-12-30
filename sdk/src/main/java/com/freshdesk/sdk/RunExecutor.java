package com.freshdesk.sdk;

import com.freshdesk.sdk.plug.RunPlugExecutor;
import io.airlift.airline.Command;
import java.io.File;

/**
 *
 * @author subhash
 */
@Command(name = "run")
public class RunExecutor extends AbstractRunExecutor {
    
    private AbstractRunExecutor exec;
    
    @Override
    public void init(File prjDir) {
        super.init(prjDir);
        
        if(isPlugProj()) {
            exec = new RunPlugExecutor();
        }
        
        exec.verbose = this.verbose;
        exec.verboseException = this.verboseException;
        exec.trace = this.trace;
        exec.init(prjDir);
    }

    @Override
    public void execute() throws SdkException {
        exec.execute();
    }
    
}
