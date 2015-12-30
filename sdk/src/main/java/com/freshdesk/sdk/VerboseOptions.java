package com.freshdesk.sdk;

/**
 *
 * @author subhash
 */
public class VerboseOptions {
    private final boolean verbose;
    private final boolean verboseException;
    private boolean trace;
    
    public VerboseOptions(boolean verbose, boolean verboseException, boolean trace) {
        this.verbose = verbose;
        this.verboseException = verboseException;
        this.trace = trace;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public boolean isVerboseException() {
        return verboseException;
    }

    public boolean isTrace() {
        return trace;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.verbose ? 1 : 0);
        hash = 61 * hash + (this.verboseException ? 1 : 0);
        hash = 61 * hash + (this.trace ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VerboseOptions other = (VerboseOptions) obj;
        if (this.verbose != other.verbose) {
            return false;
        }
        if (this.verboseException != other.verboseException) {
            return false;
        }
        if (this.trace != other.trace) {
            return false;
        }
        return true;
    }
    
}
