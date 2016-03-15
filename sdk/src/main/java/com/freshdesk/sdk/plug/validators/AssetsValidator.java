package com.freshdesk.sdk.plug.validators;

import com.freshdesk.sdk.validators.BasePrePkgValidator;
import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.validators.PrePackageValidator;
import com.freshdesk.sdk.SdkException;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author subhash
 */
@PrePackageValidator
public class AssetsValidator extends BasePrePkgValidator {
    
    private static final int MAX_ASSET_COUNT = 200;
    private static final long MAX_INDVL_ASSET_SIZE = 2000000L; // 2 mb in bytes
    
    private static final List<String> ALLOWED_EXTNS;
    static {
        String[] allowedExtns = new String[]{".jpg", ".png", ".gif", ".css", ".js"};
        Arrays.sort(allowedExtns);
        ALLOWED_EXTNS = Collections.unmodifiableList(Arrays.asList(allowedExtns));
    }
    
    private static final Pattern RE_ASSET_NAME = Pattern.compile(
            "[A-Za-z0-9_.-]+\\.(jpg|png|gif|css|js)");
    
    @Override
    public void validate() throws SdkException {
        File assetsDir = new File(prjDir, "assets");
        
        int count = 0;
        for(File f: assetsDir.listFiles()) {
            count++;
            final String fileName = f.getName();
            
            // Dir and read check:
            if(f.isDirectory()) {
                throw new SdkException(
                        ExitStatus.CMD_FAILED,
                        "Dir not allowed inside `assets/': " + fileName);
            }
            if(!f.canRead()) {
                throw new SdkException(
                        ExitStatus.CMD_FAILED,
                        "Cannot read: assets/" + fileName);
            }
            
            // Extension check (redundant check as RE does it too--keeping 
            // for developer friendly message):
            boolean isInAllowedExtn = ALLOWED_EXTNS.stream()
                    .anyMatch(extn -> fileName.endsWith(extn));
            if(!isInAllowedExtn) {
                throw new SdkException(ExitStatus.CMD_FAILED,
                        "Unsupported asset type: assets/" + fileName);
            }
            
            // Filename check:
            Matcher m = RE_ASSET_NAME.matcher(fileName);
            if(!m.matches()) {
                throw new SdkException(ExitStatus.CMD_FAILED,
                        "File name has unsupported special character(s): assets/" + fileName);
            }
            
            // Size check:
            if(MAX_INDVL_ASSET_SIZE < f.length()) {
                throw new SdkException(ExitStatus.CMD_FAILED,
                        "Asset exceeds threshold size: assets/" + fileName);
            }
        }
        
        if(count > MAX_ASSET_COUNT) {
            throw new SdkException(ExitStatus.CMD_FAILED,
                    "Asset count exceeded: " + count);
        }
    }
}
