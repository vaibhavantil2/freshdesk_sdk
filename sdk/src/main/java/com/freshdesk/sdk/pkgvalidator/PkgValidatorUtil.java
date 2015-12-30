package com.freshdesk.sdk.pkgvalidator;

import com.freshdesk.sdk.ExtnType;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.reflections.Reflections;

/**
 *
 * @author subhash
 */
public final class PkgValidatorUtil {
    private PkgValidatorUtil() {}
    
    private static final String PKG_PLG_VALIDATORS = "com.freshdesk.sdk.plug.pkgvalidator";
    private static final String PKG_APP_VALIDATORS = "com.freshdesk.sdk.app.pkgvalidator";
    
    private static Set<Class<?>> getValidators(ExtnType type,
            Class annotationClass)
            throws InstantiationException, IllegalAccessException {
        Reflections reflections;
        switch(type) {
            case PLUG:
                reflections = new Reflections(PKG_PLG_VALIDATORS);
                break;
            case APP:
                reflections = new Reflections(PKG_APP_VALIDATORS);
                break;
            default:
                return Collections.emptySet();
        }
        return reflections.getTypesAnnotatedWith(annotationClass);
    }
    
    public static Set<PrePkgValidator> getPrePkgValidators(ExtnType type)
            throws InstantiationException, IllegalAccessException {
        final Set<PrePkgValidator> out = new HashSet<>();
        for(Class<?> clazz: getValidators(type, PrePackageValidator.class)) {
            PrePkgValidator obj = (PrePkgValidator) clazz.newInstance();
            out.add(obj);
        }
        return out;
    }
    
    public static Set<PostPkgValidator> getPostPkgValidators(ExtnType type)
            throws InstantiationException, IllegalAccessException {
        final Set<PostPkgValidator> out = new HashSet<>();
        for(Class<?> clazz: getValidators(type, PostPackageValidator.class)) {
            PostPkgValidator obj = (PostPkgValidator) clazz.newInstance();
            out.add(obj);
        }
        return out;
    }
}
