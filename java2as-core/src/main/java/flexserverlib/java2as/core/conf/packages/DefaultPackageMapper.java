package flexserverlib.java2as.core.conf.packages;

import flexserverlib.java2as.core.conf.PackageMapper;

/**
 * Default implementation of PackageMapper that maps all package names to the original value.
 *
 * @author cliff.meyers
 */
public class DefaultPackageMapper implements PackageMapper {

	public boolean canMap(String packageName) {
		return true;
	}

	public String performMap(String packageName) {
		return packageName;
	}

}
