package com.freshdesk.sdk;

import io.airlift.airline.Option;

/**
 *
 * @author subhash
 */
public abstract class AbstractRunExecutor extends AbstractProjectExecutor {
    @Option(name = "-t")
    public boolean trace;
}
