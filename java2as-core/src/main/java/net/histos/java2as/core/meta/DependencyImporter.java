package net.histos.java2as.core.meta;

/**
 * Determines whether a dependency is required in the context of another class.
 * Also makes the qualified and simple names for the dependency type available.
 *
 * @author cliff.meyers
 */
public interface DependencyImporter<TYPE> {

	/**
	 * Determines whether the dependency should be included or excluded.
	 *
	 * @param packageName
	 * @param dependency
	 * @return True if the dependency should be included.
	 */
	boolean shouldResolve(String packageName, TYPE dependency);

	/**
	 * Resolves the qualified name of the dependency (suitable for use in an import block).
	 *
	 * @param dependency Dependency to map.
	 * @return Qualified type name.
	 */
	String resolveQualifiedName(TYPE dependency);

	/**
	 * Resolves the simple name of the dependency (suitable for use in field or method declarations).
	 *
	 * @param dependency Dependency to map.
	 * @return Simple type name.
	 */
	String resolveSimpleName(TYPE dependency);
	
}
