/*
 * Validates the iparam used in local testing `test_data.yml` are also
 * present in the `iparam_??.yml` files.
 */
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
public class IParamInTestAvailableValidator extends BasePrePkgValidator {
    
    private static final String ERR_MSG = "`test_data.yml` key `%s` missing in `iparam_en.yml`.";

    @Override
    public void validate() throws SdkException {
        Set<String> ipDataKeys = iparams.getIParams().keySet();
        if(ipDataKeys.isEmpty()) {
            // no parameters in test file!
            return;
        }
        
        Set<String> ipCfgKeys = iparamConfig.getConfig("en").keySet();
        
        for(String k: ipDataKeys) {
            if(!ipCfgKeys.contains(k)) {
                throw new SdkException(ExitStatus.CORRUPT_IPARAM,
                    String.format(ERR_MSG, k));
            }
        }
    }
    
}
