package net.histos.java2as.core.meta.parameters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Extracts parameter names from a method.
 * Checks for the existence of Paranamer and uses it if available on classpath.
 * If not available, simply returns arg0, arg1, etc.
 *
 * @author cliff.meyers
 */
public class ParameterNameExtractor {

	private static Logger _log = LoggerFactory.getLogger(ParameterNameExtractor.class);

	private static boolean PARANAMER_CLASSPATH_AVAILABILITY_CHECKED;
	private static boolean PARANAMER_AVAILABLE;

	static {
		if (!PARANAMER_CLASSPATH_AVAILABILITY_CHECKED) {
			PARANAMER_CLASSPATH_AVAILABILITY_CHECKED = true;
			try {
				Class.forName("com.thoughtworks.paranamer.Paranamer");
				PARANAMER_AVAILABLE = true;
				_log.info("Paranamer found on classpath; will attempt to extract method parameter names.");
			} catch (ClassNotFoundException e) {
				PARANAMER_AVAILABLE = false;
				_log.warn("Paranamer not found on classpath; will skip method parameter name extraction.");
			}
		}
	}

	/**
	 * Extracts parameter names from a method.
	 *
	 * @param method Method
	 * @return Array of parameter names
	 */
	public static String[] extractParameterNames(Method method) {

		Class<?>[] types = method.getParameterTypes();
		String[] names = new String[0];

		if (PARANAMER_AVAILABLE)
			names = ParanamerHelper.extractParameterNames(method);

		// recover gracefully by using "arg0", "arg1" instead
		if (names.length == 0) {
			names = new String[types.length];
			for (int i = 0; i < types.length; i++)
				names[i] = "arg" + i;
		}

		return names;

	}

}
