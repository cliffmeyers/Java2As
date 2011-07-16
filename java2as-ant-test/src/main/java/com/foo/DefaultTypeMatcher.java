package com.foo;

import flexserverlib.java2as.core.conf.TypeMatcher;

/**
 * @author cliff.meyers
 */
public class DefaultTypeMatcher implements TypeMatcher {
	public boolean match(Class<?> clazz) {
		return true;
	}
}
