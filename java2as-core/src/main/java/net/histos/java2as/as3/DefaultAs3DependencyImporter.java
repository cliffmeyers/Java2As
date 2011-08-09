package net.histos.java2as.as3;

/**
 * Default implementation of dependency importer.
 * Delegates to the As3Dependency.requiresImport and also ignores dependencies in the same package or the "java" package.
 *
 * @author cliff.meyers
 */
public class DefaultAs3DependencyImporter implements As3DependencyImporter {

	public boolean shouldResolve(String packageName, As3Dependency dependency) {

		if (!dependency.requiresImport())
			return false;

		String name = dependency.getQualifiedName();

		if (name.startsWith(packageName))
			return false;
		else if (name.startsWith("java"))
			return false;

		return true;

	}

	public String resolveQualifiedName(As3Dependency dependency) {
		return dependency.getQualifiedName();
	}

	public String resolveSimpleName(As3Dependency dependency) {
		return dependency.getSimpleName();
	}

}
