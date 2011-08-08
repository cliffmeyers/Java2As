package net.histos.java2as.core;

import net.histos.java2as.core.conf.TypeMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for generating artifacts based off a set of provided classes.
 */
public abstract class AbstractProducer {

	private Logger _log = LoggerFactory.getLogger(getClass());

	//
	// Public Methods
	//

	/**
	 * Execute generation of artifacts.
	 */
	public abstract void produce();

	//
	// Protected Methods
	//
	
	/**
	 * Finds which Java classes are suitable for generation.
	 *
	 * @param matchers List of TypeMatchers that act as filters.
	 * @param classes  List of candidates classes.
	 * @return List of classes that should have artifacts generated.
	 */
	protected List<Class<?>> findMatchingClasses(List<TypeMatcher> matchers, List<Class<?>> classes) {

		List<Class<?>> matchingClasses = new ArrayList<Class<?>>();

		for (Class<?> clazz : classes) {
			// TODO: allow interfaces and enums; handle in metadata layer
			if (clazz.isInterface() || clazz.isEnum() || clazz.isAnnotation()) {
				_log.warn("Ignoring " + clazz.getName() + "; type is an interface, enum or annotation");
				continue;
			}
			for (TypeMatcher matcher : matchers) {
				if (matcher.match(clazz) && !matchingClasses.contains(clazz)) {

					matchingClasses.add(clazz);
					break;
				}
			}
		}

		return matchingClasses;

	}

}