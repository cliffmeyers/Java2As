package net.histos.java2as.core.conf;

import net.histos.java2as.core.conf.packages.DefaultPackageMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class that provides support for
 */
public abstract class AbstractConfiguration {

	//
	// Fields
	//

	protected List<TypeMatcher> typeMatchers;
	protected PackageMapper packageMapper;

	//
	// Constructors
	//

	public AbstractConfiguration() {
		typeMatchers = new ArrayList<TypeMatcher>();
		packageMapper = new DefaultPackageMapper();
	}

	//
	// Public Methods
	//

	public void addTypeMatcher(TypeMatcher typeMatcher) {
		typeMatchers.add(typeMatcher);
	}

	//
	// Getters and Setters
	//

	public List<TypeMatcher> getTypeMatchers() {
		return typeMatchers;
	}

	public void setTypeMatchers(List<TypeMatcher> typeMatchers) {
		this.typeMatchers = typeMatchers;
	}

	public PackageMapper getPackageMapper() {
		return packageMapper;
	}

	public void setPackageMapper(PackageMapper packageMapper) {
		this.packageMapper = packageMapper;
	}

}