package com.freshdesk.sdk;

import io.airlift.airline.Option;
import io.airlift.airline.OptionType;

/**
 *
 * @author subhash
 */
public abstract class BaseCommandExecutor implements CommandExecutor {
    
    @Option(type = OptionType.GLOBAL, name = "-v", description = "Verbose output.")
    public boolean verbose;
    
    @Option(type = OptionType.GLOBAL, name = "-x", description = "Print exception trace on failure.")
    public boolean verboseException;
    
}
