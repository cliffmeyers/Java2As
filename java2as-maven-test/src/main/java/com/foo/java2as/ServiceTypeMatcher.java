package com.foo.java2as;

import net.histos.java2as.core.conf.TypeMatcher;

/**
 * @author cliff.meyers
 */
public class ServiceTypeMatcher implements TypeMatcher {

	public boolean match(Class<?> clazz) {
		return clazz.getName().endsWith("Service");
	}

}
