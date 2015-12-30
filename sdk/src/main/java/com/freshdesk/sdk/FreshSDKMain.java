package com.freshdesk.sdk;

import io.airlift.airline.Cli;
import io.airlift.airline.ParseArgumentsUnexpectedException;
import java.io.File;

/**
 *
 * @author subhash
 */
public class FreshSDKMain {

    public static void main(final String[] args) {

        if (Constants.FRSH_HOME == null) {
            System.err.println("Please set FRSH_HOME environment variable.");
            ExitStatus.SETUP_ERROR.exit();
        }

        if (args.length == 0) {
            HelpExecutor.printUsage(System.err);
            ExitStatus.NO_PARAM.exit();
        }

        CommandExecutor exec = null;
        try {
            Cli.CliBuilder<CommandExecutor> builder = Cli.<CommandExecutor>builder("frsh")
                    .withDescription("Freshapps SDK command.")
                    .withDefaultCommand(HelpExecutor.class)
                    .withCommands(
                            InitExecutor.class,
                            RunExecutor.class,
                            ValidateExecutor.class,
                            PackageExecutor.class,
                            CleanExecutor.class,
                            InfoExecutor.class,
                            HelpExecutor.class,
                            VersionExecutor.class
                    );
            Cli<CommandExecutor> cliParser = builder.build();
            exec = cliParser.parse(args);
            
            // Run lifecycle:
            exec.init(new File("."));
            exec.execute();
        }
        catch(SdkException | ParseArgumentsUnexpectedException ex) {
            // Message:
            if(ex instanceof SdkException) {
                System.err.println("Command failed: " + ex.getMessage());
            }
            else if(ex instanceof ParseArgumentsUnexpectedException) {
                System.err.println(ex.getMessage());
            }
            
            // Stack trace display:
            if(exec != null &&
                    exec instanceof BaseCommandExecutor &&
                    ((BaseCommandExecutor)exec).verboseException) {
                System.err.println("\nStack trace:");
                ex.printStackTrace(System.err);
            }
            
            // Exit:
            if(ex instanceof SdkException) {
                ((SdkException)ex).getExitStatus().exit();
            }
            else if(ex instanceof ParseArgumentsUnexpectedException) {
                ExitStatus.INVALID_PARAM.exit();
            }
        }
    }
}
