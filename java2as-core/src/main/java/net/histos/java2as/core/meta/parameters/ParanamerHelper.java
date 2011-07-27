package net.histos.java2as.core.meta.parameters;

import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Extracts parameter names from a method using Paranamer.
 * This class is a wrapper around Paranamer APIs so it can be treated as an optional dependency.
 *
 * @author cliff.meyers
 */
public class ParanamerHelper {

	private static Logger _log = LoggerFactory.getLogger(ParanamerHelper.class);

	//
	// Statics
	//

	private static Set<String> CLASS_WARNINGS = new HashSet<String>();

	/**
	 * Extracts parameter names from a method.
	 *
	 * @param method Method
	 * @return Array of parameter names, or an empty array if not available.
	 */
	public static String[] extractParameterNames(Method method) {

		Class<?>[] types = method.getParameterTypes();
		String[] names = new String[types.length];

		// attempt to extract method parameter names using Paranamer
		// NOTE: even if Paranamer is available on classpath, "names" will be empty unless the compiled code was post-processed using the tool
		Paranamer paranamer = new CachingParanamer();
		names = paranamer.lookupParameterNames(method);

		if (names.length == 0) {
			String serviceClassName = method.getDeclaringClass().getName();

			if (!CLASS_WARNINGS.contains(serviceClassName)) {
				CLASS_WARNINGS.add(serviceClassName);
				_log.warn("Parameter name information could not be found for service class: " + serviceClassName);
			}
		}

		return names;

	}

}
