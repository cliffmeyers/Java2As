package net.histos.java2as.core.meta;

import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JavaMethod {

	private static boolean PARAMETER_NAMES_ALREADY_MISSING = false;

	//
	// Fields
	//

	private Method method;
	private List<JavaMethodParameter> parameters;
	private Class<?> returnType;

	//
	// Constructors
	//

	public JavaMethod(Method method) {

		this.method = method;
		this.parameters = new ArrayList<JavaMethodParameter>();

		if (method.getParameterTypes().length > 0) {
			// attempt to extract method parameter names using Paranamer
			// "names" will be empty unless the compiled code was post-processed using the tool
			Paranamer paranamer = new CachingParanamer();
			String[] names = paranamer.lookupParameterNames(method);
			Class<?>[] types = method.getParameterTypes();
			Annotation[][] annotations = method.getParameterAnnotations();

			if (names.length == 0) {
				// only display this warning once
				if (!PARAMETER_NAMES_ALREADY_MISSING) {
					PARAMETER_NAMES_ALREADY_MISSING = true;
					System.out.println("WARNING!");
					System.out.println("Parameter name information could not be found.");
					System.out.println("Use Paranamer to post-process your classes to enable this feature.");
				}
				// recover gracefully by using "arg0", "arg1" instead
				names = new String[types.length];
				for (int i = 0; i < types.length; i++)
					names[i] = "arg" + i;
			}

			for (int i = 0; i < method.getParameterTypes().length; i++) {
				JavaMethodParameter parameter = new JavaMethodParameter(names[i], types[i], annotations[i]);
				parameters.add(parameter);
			}
		}

		this.returnType = method.getReturnType();

	}

	//
	// Public Methods
	//

	@Override
	public String toString() {
		return "JavaMethod{" +
				"method=" + method.getName() +
				'}';
	}

	//
	// Getters and Setters
	//

	public String getName() {
		return method.getName();
	}

	public List<JavaMethodParameter> getParameters() {
		return parameters;
	}

	public Class<?> getReturnType() {
		return returnType;
	}
}
