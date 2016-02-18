package com.freshdesk.sdkupdate;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.wiztools.appupdate.Version;
import joptsimple.OptionSet;
import joptsimple.OptionParser;

public class SdkUpdateMain {

    public static void main(String[] args) {
        List<Rollbackable> rollbackables = new ArrayList<>();
        List<Cleanable> cleanables = new ArrayList<>();
        final String ENDPT;

        OptionParser parser = new OptionParser( "d:" );
        OptionSet options = parser.parse(args);

        if (options.valueOf("d")!=null) {
            ENDPT = options.valueOf("d").toString();
        }
        else{
            ENDPT = Constants.DOMAIN;
        }

        try {
            // Updates core
            CoreUpdater cp = new CoreUpdater();
            cp.setCleanables(cleanables);
            cp.setRollbackables(rollbackables);
            cp.update(ENDPT + "/sdk/version.json");

            // Updates template
            TmplUpdater tp = new TmplUpdater();
            tp.setCleanables(cleanables);
            tp.setRollbackables(rollbackables);
            tp.update(ENDPT + "/sdk/freshdesk/template-version.json");
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
