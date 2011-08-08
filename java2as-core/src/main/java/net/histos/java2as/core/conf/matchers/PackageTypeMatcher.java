package net.histos.java2as.core.conf.matchers;

import net.histos.java2as.core.conf.TypeMatcher;

/**
 * Matches classes based on a package.
 *
 * @author cliff.meyers
 */
public class PackageTypeMatcher implements TypeMatcher {

	//
	// Fields
	//

	private String packageName;

	//
	// Constructors
	//

	public PackageTypeMatcher(String packageName) {
		this.packageName = packageName;
	}

	//
	// Public Methods
	//

	public boolean match(Class<?> clazz) {
		return clazz.getName().startsWith(packageName);
	}
	
}
