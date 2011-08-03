package net.histos.java2as.core.conf.matchers;

import net.histos.java2as.core.conf.TypeMatcher;

/**
 * Basic TypeMatcher implementation that filters out undesired Java types.
 * 
 * @author cliff.meyers
 */
public class DefaultTypeMatcher implements TypeMatcher {

	public boolean match(Class<?> clazz) {
		return !clazz.isAnnotation() && !clazz.isInterface() && !clazz.isEnum();
	}

}
