package com.freshdesk.sdk.plug.pkgvalidator;

import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.SdkException;
import com.freshdesk.sdk.pkgvalidator.BasePrePkgValidator;
import com.freshdesk.sdk.pkgvalidator.PrePackageValidator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author subhash
 */
@PrePackageValidator
public class IParamDropdownOptionsIsArrayValidator extends BasePrePkgValidator {

    @Override
    public void validate() throws SdkException {
        if(iparamConfig == null) {
            return;
        }
        for(String l: iparamConfig.getConfigLangs()) {
            Map<String, Object> ips = iparamConfig.getConfig(l);
            for(Map.Entry<String, Object> e: ips.entrySet()) {
                String k = e.getKey();
                Object v = e.getValue();
                if(v instanceof Map) {
                    Object type = ((Map<String, Object>) v).get("type");
                    if("dropdown".equals(type)) {
                        if(!(((Map<String, Object>) v).get("options") instanceof List)) {
                            String msg = String.format(
                                    "Dropdown `options` in `iparam_%s.yml` is not a List.", l);
                            throw new SdkException(ExitStatus.CORRUPT_IPARAM,
                                msg);
                        }
                    }
                }
            }
        }
    }
    
}
