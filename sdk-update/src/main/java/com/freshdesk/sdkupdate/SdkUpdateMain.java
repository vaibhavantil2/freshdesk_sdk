package com.freshdesk.sdkupdate;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import joptsimple.OptionException;
import org.wiztools.appupdate.Version;
import joptsimple.OptionSet;
import joptsimple.OptionParser;

public class SdkUpdateMain {

    public static void main(String[] args) throws OptionException{

        // Cli parsing:
        final String ENDPT;

        OptionParser parser = new OptionParser( "d:" );
        try {
            OptionSet options = parser.parse(args);

            if (options.valueOf("d") != null) {
                ENDPT = options.valueOf("d").toString();
            } else {
                ENDPT = Constants.DL_HOST;
            }
        }
        catch(OptionException ex) {
            System.err.println("Cli parse error. Exiting...");
            ex.printStackTrace(System.err);
            return;
        }

        // Update core logic:
        List<Rollbackable> rollbackables = new ArrayList<>();
        List<Cleanable> cleanables = new ArrayList<>();
        try {

            // Updates core
            CoreUpdater cp = new CoreUpdater();
            cp.setCleanables(cleanables);
            cp.setRollbackables(rollbackables);
            boolean upSuccess = cp.update(ENDPT + Constants.SDK_VER_PATH);

            // Updates template
            if(upSuccess) {
                TmplUpdater tp = new TmplUpdater();
                tp.setCleanables(cleanables);
                tp.setRollbackables(rollbackables);
                tp.update(ENDPT + Constants.TMPL_VER_PATH);
            }
        }
        catch(SdkUpdateException ex) {
            // Feedback:
            System.err.println("Installation failed: " + ex.getMessage());
            System.err.println("Rolling back...");
            
            // Rollbacks should happen in reverse order:
            Collections.reverse(rollbackables);
            
            // Now rollback:
            rollbackables.stream().forEach((r) -> {
                try {
                    r.rollback();
                }
                catch(SdkUpdateException ex1) {
                    System.err.println("Error while rollbacking...");
                    ex1.printStackTrace(System.err);
                }
            });
            System.err.println("Rollback complete. Try fixing the error.");
        }
        finally {
            // Cleanup must happen in reverse order:
            Collections.reverse(cleanables);
            
            // Now clean:
            cleanables.stream().forEach((c) -> {
                try {
                    c.clean();
                }
                catch(SdkUpdateException ex) {
                    System.err.println("Error cleaning up...");
                    ex.printStackTrace(System.err);
                }
            });
        }
    }
}
