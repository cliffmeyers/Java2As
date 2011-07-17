package com.foo.java2as;

import flexserverlib.java2as.core.conf.TypeMatcher;

/**
 * @author cliff.meyers
 */
public class MyDefaultTypeMatcher implements TypeMatcher {

	public boolean match(Class<?> clazz) {
		String name = clazz.getName();
		return name.startsWith("com.foo.dto");
	}

}
