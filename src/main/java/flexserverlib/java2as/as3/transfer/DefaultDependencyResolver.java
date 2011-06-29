package flexserverlib.java2as.as3.transfer;

/**
 * 
 *
 * @author cliff.meyers
 */
public class DefaultDependencyResolver implements DependencyResolver {

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
