package com.freshdesk.sdkupdate;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.wiztools.appupdate.Version;
import org.wiztools.appupdate.VersionImpl;

/**
 *
 * @author subhash
 */
public class SdkUtil {
    
    private static final Pattern RE = Pattern.compile("frsh-(.*)");
    
    public static Version getCurrentVersion(File sdkDir) {
        List<Version> versions = new ArrayList<>();
        File[] files = sdkDir.listFiles();
        if(files != null) {
            for(File f: files) {
                if(f.isDirectory()) {
                    String name = f.getName();
                    Matcher m = RE.matcher(name);
                    if(m.matches()) {
                        String verStr = m.group(1);
                        try {
                            versions.add(new VersionImpl(verStr));
                        }
                        catch(IllegalArgumentException ex) {
                            System.err.println("Wrong folder in $FRSH_HOME/sdk: " + f.getName());
                        }
                    }
                }
            }
        }
        if(!versions.isEmpty()) {
            Collections.sort(versions, (Version v1, Version v2) -> {
                if(v1.isGreaterThan(v2)) {
                    return 1;
                }
                else if(v1.equals(v2)) {
                    return 0;
                }
                else {
                    return -1;
                }
            });
            return versions.get(versions.size()-1);
        }
        else {
            return new VersionImpl("0.0.0");
        }
    }
}
