package net.histos.java2as.core.meta;

import net.histos.java2as.as3.As3Type;

/**
 * Models a dependency from one class to another.
 *
 * @author cliff.meyers
 */
public interface Dependency<TYPE> {

	/**
	 * @return Fully-qualified name
	 */
	String getQualifiedName();

	/**
	 * @return Short version of name (no package)
	 */
	String getSimpleName();

	/**
	 * @return Type of dependency
	 */
	TYPE getDependencyType();

	/**
	 * @return Kind of dependency, e.g. superclass, interface, property, etc.
	 */
	DependencyKind getDependencyKind();

	/**
	 * @return True if the dependency requires an import; false if dependency is a built-in that is always available.
	 */
	boolean requiresImport();
	
}
