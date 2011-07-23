package net.histos.java2as.as3;

import net.histos.java2as.as3.transfer.As3Dependency;

/**
 * Determines whether an ActionScript dependency is required in the context of an ActionScript class.
 * Also makes the qualified and simple names for the dependency type available.
 *
 * @author cliff.meyers
 */
public interface DependencyResolver {

	/**
	 * Determines whether the dependency should be included or excluded.
	 *
	 * @param packageName
	 * @param dependency
	 * @return True if the dependency should be included.
	 */
	boolean shouldResolve(String packageName, As3Dependency dependency);

	/**
	 * Resolves the qualified name of the dependency (suitable for use in an import block).
	 *
	 * @param dependency Dependency to map.
	 * @return Qualified type name.
	 */
	String resolveQualifiedName(As3Dependency dependency);

	/**
	 * Resolves the simple name of the dependency (suitable for use in field or method declarations).
	 *
	 * @param dependency Dependency to map.
	 * @return Simple type name.
	 */
	String resolveSimpleName(As3Dependency dependency);

}
