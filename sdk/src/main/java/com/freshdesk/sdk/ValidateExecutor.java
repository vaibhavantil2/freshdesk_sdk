package com.freshdesk.sdk;

import com.freshdesk.sdk.pkgvalidator.PkgValidatorUtil;
import com.freshdesk.sdk.pkgvalidator.PrePkgValidator;
import io.airlift.airline.Command;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author subhash
 */
@Command(name = "validate")
public class ValidateExecutor extends AbstractProjectExecutor {

    @Override
    public void execute() throws SdkException {
        
        List<String> errors = new ArrayList<>();
        
        // Run validations:
        for(PrePkgValidator validator: getPrePkgValidators()) {
            // Instantiate:
            validator.setPrjDir(prjDir);
            validator.setManifest(manifest);
            validator.setIParam(iparams);
            
            // Now validate:
            try {
                validator.validate();
            }
            catch(SdkException ex) {
                errors.add(ex.getMessage());
            }
        }
        
        if(!errors.isEmpty()) {
            System.out.println("Validation errors found:");
            int count = 0;
            for(String msg: errors) {
                count++;
                System.out.println("  " + count + ". " + msg);
            }
        }
        
        if(verbose && errors.isEmpty()) {
            System.out.println("Validation successful: no issues found.");
        }
        
        // This executor always exits with `0' status, even when issues are found!
    }
    
    private Set<PrePkgValidator> getPrePkgValidators() {
        try {
            return PkgValidatorUtil.getPrePkgValidators(extnType);
        }
        catch(InstantiationException | IllegalAccessException ex) {
            throw new SdkException(ExitStatus.CMD_FAILED, ex);
        }
    }
}
