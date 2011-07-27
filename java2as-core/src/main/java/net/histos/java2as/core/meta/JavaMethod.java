package net.histos.java2as.core.meta;

import net.histos.java2as.core.meta.parameters.ParameterNameExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Wraps a java.lang.reflect.Method instance to provide additional metadata.
 */
public class JavaMethod {

	private Logger _log = LoggerFactory.getLogger(getClass());

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

			Class<?>[] types = method.getParameterTypes();
			String[] names = ParameterNameExtractor.extractParameterNames(method);
			Annotation[][] annotations = method.getParameterAnnotations();

			for (int i = 0; i < types.length; i++) {
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
