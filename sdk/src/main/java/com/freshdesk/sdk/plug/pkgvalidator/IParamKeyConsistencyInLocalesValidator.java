package com.freshdesk.sdk.plug.pkgvalidator;

import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.SdkException;
import com.freshdesk.sdk.pkgvalidator.BasePrePkgValidator;
import com.freshdesk.sdk.pkgvalidator.PrePackageValidator;
import java.util.Set;

/**
 *
 * @author subhash
 */
@PrePackageValidator
public class IParamKeyConsistencyInLocalesValidator extends BasePrePkgValidator {
    
    private static final String ERR_MSG = "Inconsistent keys detected between `iparam_??.yml` files.";

    @Override
    public void validate() throws SdkException {
        if(iparamConfig == null) {
            return;
        }
        
        boolean isFirst = true;
        Set first = null;
        for(String l: iparamConfig.getConfigLangs()) {
            Set current = iparamConfig.getConfig(l).keySet();
            if(isFirst) {
                isFirst = false;
                first = current;
                continue;
            }
            
            // Check for equality:
            if((first == null && current == null)) {
                continue;
            }
            if((first != null) && (!first.equals(current))) {
                throw new SdkException(ExitStatus.CORRUPT_IPARAM, ERR_MSG);
            }
            if((current != null) && (!current.equals(first))) {
                throw new SdkException(ExitStatus.CORRUPT_IPARAM, ERR_MSG);
            }
        }
    }
    
}
