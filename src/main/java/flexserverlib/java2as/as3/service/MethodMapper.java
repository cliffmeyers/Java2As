package flexserverlib.java2as.as3.service;

import flexserverlib.java2as.core.meta.JavaMethod;

/**
 * Description
 *
 * @author cliff.meyers
 */
public interface MethodMapper {

    As3Method mapMethod(JavaMethod method);
    
}
