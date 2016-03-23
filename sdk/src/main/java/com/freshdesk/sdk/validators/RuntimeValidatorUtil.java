package com.freshdesk.sdk.validators;

import com.freshdesk.sdk.ExtnType;
import org.reflections.Reflections;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by raghu on 15/03/16.
 */
public final class RuntimeValidatorUtil {

    private static final String PLG_RUN_VALIDATORS = "com.freshdesk.sdk.plug.validators";
    private static final String APP_RUN_VALIDATORS = "com.freshdesk.sdk.app.validators";

    private static Set<Class<?>> getValidators(ExtnType type,
                                               Class annotationClass)
        throws InstantiationException, IllegalAccessException {
        Reflections reflections;
        switch(type) {
            case PLUG:
                reflections = new Reflections(PLG_RUN_VALIDATORS);
                break;
            case APP:
                reflections = new Reflections(APP_RUN_VALIDATORS);
                break;
            default:
                return Collections.emptySet();
        }
        return reflections.getTypesAnnotatedWith(annotationClass);
    }

    public static Set<RunValidator> getRuntimeValidators(ExtnType type)
        throws InstantiationException, IllegalAccessException {
        final Set<RunValidator> out = new HashSet<>();
        for(Class<?> clazz: getValidators(type, RuntimeValidator.class)) {
            RunValidator obj = (RunValidator) clazz.newInstance();
            out.add(obj);
        }
        return out;
    }
}
