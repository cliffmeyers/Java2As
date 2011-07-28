package net.histos.java2as.core.conf.matchers;

import net.histos.java2as.core.conf.TypeMatcher;
import org.apache.commons.lang.ClassUtils;

import java.util.List;

/**
 * Matches classes based on a specified interface.
 *
 * @author cliff.meyers
 */
public class InterfaceTypeMatcher implements TypeMatcher {

	//
	// Fields
	//

	private String qualifiedInterfaceName;

	//
	// Constructors
	//

	/**
	 * @param qualifiedInterfaceName The fully-qualified interface name to check for.
	 */
	public InterfaceTypeMatcher(String qualifiedInterfaceName) {
		this.qualifiedInterfaceName = qualifiedInterfaceName;
	}

	/**
	 * @inheritDoc
	 */
	public boolean match(Class<?> clazz) {

		List<Class> interfaces = ClassUtils.getAllInterfaces(clazz);

		for (Class<?> interfaceClass : interfaces)
			if (qualifiedInterfaceName.equals(interfaceClass.getName()))
				return true;

		return false;

	}

}
