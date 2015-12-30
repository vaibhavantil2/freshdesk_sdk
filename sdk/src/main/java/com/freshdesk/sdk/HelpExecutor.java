package com.freshdesk.sdk;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.wiztools.commons.MultiValueMap;
import org.wiztools.commons.MultiValueMapLinkedHashSet;

/**
 *
 * @author subhash
 */
@Command(name = "help")
public class HelpExecutor extends BaseCommandExecutor {
    
    private static final String INDENT = "    ";
    
    private static final Map<String, String> CMD = new LinkedHashMap<>();
    private static final Map<String, String> CMD_USAGE = new LinkedHashMap<>();
    private static final MultiValueMap<String, String> CMD_OPTS = new MultiValueMapLinkedHashSet<>();
    private static final List<String> CMD_GLOBAL_OPTS = new ArrayList<>();
    
    static {
        CMD_GLOBAL_OPTS.add("-v: Verbose mode.");
        CMD_GLOBAL_OPTS.add("-x: Print exception trace on failure.");
        
        CMD.put("init", "Create a new plug project.");
        CMD.put("run", "Run embedded server for local testing.");
        CMD.put("validate", "Validate project for issues.");
        CMD.put("package", "Generate the deployable package.");
        CMD.put("clean", "Remove `dist/' dir created by package command.");
        CMD.put("info", "Print project details.");
        CMD.put("version", "Print version details.");
        CMD.put("help", "Print the help message.");
        
        CMD_USAGE.put("init", "frsh init plug [folder].\nWhen [folder] is not given, cwd is taken as project dir.");
        CMD_USAGE.put("help", "frsh help [command]");
        
        CMD_OPTS.put("run", "-t: Enable request tracing.");
    }
    
    
    @Arguments
    public List<String> arguments;
    
    public static void printUsage(final PrintStream out) {
        out.println("Usage:");
        out.println("  frsh [global-options] <command> [options]");
        out.println("Where <command> is one of:");
        CMD.entrySet().stream().forEach((entry) -> {
            out.println(INDENT + entry.getKey() + ": " + entry.getValue());
        });
        out.println("Global options are:");
        CMD_GLOBAL_OPTS.stream().forEach((opt) -> {
            out.println(INDENT + opt);
        });
    }

    @Override
    public void execute() throws SdkException {
        if(arguments == null) {
            printUsage(System.out);
        }
        else {
            String helpFor = arguments.get(0);
            String cmdDesc = CMD.get(helpFor);
            String cmdUsage = CMD_USAGE.get(helpFor);
            Collection<String> cmdOpts = CMD_OPTS.get(helpFor);
            if(cmdDesc != null) {
                System.out.println(helpFor + ": " + cmdDesc);
            }
            else {
                throw new SdkException(ExitStatus.INVALID_PARAM,
                    "Help for unknown command requested: " + helpFor);
            }
            // Print usage:
            if(cmdUsage != null) {
                System.out.println("Usage: " + cmdUsage);
            }
            
            // Print command usage:
            if(cmdOpts != null) {
                System.out.println("\nOptions:");
                cmdOpts.stream().forEach((opt) -> {
                    System.out.println(INDENT + opt);
                });
            }
        }
    }    

    @Override
    public void init(File prjDir) throws SdkException {
        // do nothing!
    }
}
