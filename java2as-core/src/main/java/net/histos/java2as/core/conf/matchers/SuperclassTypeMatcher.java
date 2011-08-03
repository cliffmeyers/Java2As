package net.histos.java2as.core.conf.matchers;

import net.histos.java2as.core.conf.TypeMatcher;
import org.apache.commons.lang.ClassUtils;

import java.util.List;

/**
 * Matches classes based on a specified superclass.
 * Searches up the class hierarchy for the match.
 *
 * @author cliff.meyers
 */
public class SuperclassTypeMatcher extends DefaultTypeMatcher {

	//
	// Fields
	//

	private String qualifiedClassName;

	//
	// Constructors
	//

	/**
	 * @param qualifiedClassName The fully-qualified class name to check for.
	 */
	public SuperclassTypeMatcher(String qualifiedClassName) {
		this.qualifiedClassName = qualifiedClassName;
	}

	//
	// Public Methods
	//

	/**
	 * @inheritDoc
	 */
	public boolean match(Class<?> clazz) {

		if (!super.match(clazz))
			return false;

		List<Class> superclasses = ClassUtils.getAllSuperclasses(clazz);
		superclasses.add(clazz);

		for (Class superClass : superclasses)
			if (qualifiedClassName.equals(superClass.getName()))
				return true;

		return false;

	}

}
