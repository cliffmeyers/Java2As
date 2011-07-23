package net.histos.java2as.core;

import net.histos.java2as.core.conf.TypeMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for generating artifacts based off a set of provided classes.
 */
public abstract class AbstractProducer {

	/**
	 * Execute generation of artifacts.
	 */
	public abstract void produce();

	/**
	 * Finds which Java classes are suitable for generation.
	 *
	 * @param matchers List of TypeMatchers that act as filters.
	 * @param classes List of candidates classes.
	 * @return List of classes that should have artifacts generated.
	 */
	protected List<Class<?>> findMatchingClasses(List<TypeMatcher> matchers, List<Class<?>> classes) {

		if (matchers.size() == 0) return classes;

		List<Class<?>> matchingClasses = new ArrayList<Class<?>>();

		for (Class<?> clazz : classes) {
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