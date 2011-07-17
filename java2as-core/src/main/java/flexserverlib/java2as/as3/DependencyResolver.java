package flexserverlib.java2as.as3;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.transfer.As3Dependency;
import flexserverlib.java2as.core.meta.DependencyKind;

/**
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
	 * @param dependency
	 * @return
	 */
	String resolveQualifiedName(As3Dependency dependency);

	/**
	 * Resolves the simple name of the dependency.
	 *
	 * @param dependency
	 * @return
	 */
	String resolveSimpleName(As3Dependency dependency);

}
