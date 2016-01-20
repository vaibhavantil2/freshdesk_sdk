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

    @Override
    public void validate() throws SdkException {
        if(iparamConfig == null) {
            return;
        }
        Set first = null;
        for(String l: iparamConfig.getConfigLangs()) {
            Set current = iparamConfig.getConfig(l).keySet();
            if(first == null) {
                first = current;
                continue;
            }
            
            if(!first.equals(current)) {
                throw new SdkException(ExitStatus.CORRUPT_IPARAM,
                        "Inconsistent keys detected between `iparam_??.yml` files.");
            }
        }
    }
    
}
