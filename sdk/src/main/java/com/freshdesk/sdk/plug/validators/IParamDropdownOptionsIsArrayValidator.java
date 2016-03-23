package com.freshdesk.sdk.plug.validators;

import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.SdkException;
import com.freshdesk.sdk.validators.BasePrePkgValidator;
import com.freshdesk.sdk.validators.PrePackageValidator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author subhash
 */
@PrePackageValidator
public class IParamDropdownOptionsIsArrayValidator extends BasePrePkgValidator {
    
    private static final String ERR_MSG = "Dropdown `options` for key `%s` in `iparam_%s.yml` is not a list.";

    @Override
    public void validate() throws SdkException {
        if(iparamConfig == null) {
            return;
        }
        for(String l: iparamConfig.getConfigLangs()) {
            Map<String, Object> ips = iparamConfig.getConfig(l);
            for(Map.Entry<String, Object> e: ips.entrySet()) {
                Object k = e.getKey();
                Object v = e.getValue();
                
                if(v instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> value = (Map<String, Object>) v;
                    
                    Object type = value.get("type");
                    if("dropdown".equals(type)) {
                        if(!(value.get("options") instanceof List)) {
                            String msg = String.format(ERR_MSG, k, l);
                            throw new SdkException(
                                    ExitStatus.CORRUPT_IPARAM, msg);
                        }
                    }
                }
            }
        }
    }
    
}
