package com.freshdesk.sdk;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;
import java.util.List;
import java.io.File;
import java.io.IOException;
import org.wiztools.commons.ZipUtil;

/**
 *
 * @author subhash
 */
@Command(name = "init")
public class InitExecutor extends AbstractInitExecutor {
    
    private static final String PLUG_TEMPLATE = "plug-template.zip";
    private static final String APP_TEMPLATE = "app-template.zip";
    
    @Arguments
    public List<String> arguments;
    
    private ExtnType type;
    
    private boolean isPlugProject() {
        return type == ExtnType.PLUG;
    }
    
    private boolean isAppProject() {
        return type == ExtnType.APP;
    }
    
    private String tmpl;
    private File prjDir;
    
    @Override
    public void init(File dir) {
        super.init(dir);
        
        // Validate command arguments:
        if(arguments == null) {
            throw new SdkException(ExitStatus.INVALID_PARAM,
                    "What type to init? Give plug / app as parameter.");
        }
        if(arguments.size() > 2) {
            throw new SdkException(ExitStatus.INVALID_PARAM,
                    "init command takes atmost 2 parameters: type and foldername (optional).");
        }

        // Project type determination:
        final String typeStr = arguments.get(0);
        switch(typeStr) {
            case "plug":
                type = ExtnType.PLUG;
                break;
            case "app":
                type = ExtnType.APP;
                break;
            default:
                throw new SdkException(ExitStatus.INVALID_PARAM,
                        "init type not recognized: " + typeStr);
        }
        
        // Project dir determination & validations:
        if(arguments.size() == 2) {
            String folderName = arguments.get(1);
            prjDir = new File(folderName);
        }
        else {
            prjDir = dir;
        }
        if(!prjDir.exists()) {
            boolean ret = prjDir.mkdirs();
            if(!ret) {
                throw new SdkException(ExitStatus.CMD_FAILED,
                        "Project dir creation failed: "
                        + prjDir.getName());
            }
        }
        if(!Util.isDirEmpty(prjDir)) {
            throw new SdkException(ExitStatus.CMD_FAILED,
                    "Project dir is not a dir/not empty: " + prjDir.getName());
        }

        // Execute lifecycle:
        if(isPlugProject()) {
            tmpl = PLUG_TEMPLATE;
        }
        else if(isAppProject()) {
            tmpl = APP_TEMPLATE;
        }
    }

    @Override
    public void execute() throws SdkException {
        try {
            ZipUtil.unzip(
                    new File(Constants.SDK_TMPL_DIR, tmpl), prjDir);
            if(verbose) {
                System.out.println("Project created: " + prjDir.getName());
            }
        }
        catch (IOException ex) {
            throw new SdkException(
                    ExitStatus.CMD_FAILED,
                    "Project creation failed due to IO error.",
                    ex);
        }
    }
    
}
