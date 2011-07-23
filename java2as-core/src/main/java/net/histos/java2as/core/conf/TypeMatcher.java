package net.histos.java2as.core.conf;

/**
 * Decides whether a type should be included for generation.
 *
 * @author cliff.meyers
 */
public interface TypeMatcher {
	/**
	 * Encapsulates a rule for matching a class
	 *
	 * @param clazz The class to be tested
	 * @return Whether the class should be included
	 */
	public boolean match(Class<?> clazz);
}