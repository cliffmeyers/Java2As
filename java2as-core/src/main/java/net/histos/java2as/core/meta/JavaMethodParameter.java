package net.histos.java2as.core.meta;

import java.lang.annotation.Annotation;

/**
 * Contains metadata about a Java method parameter / argument.
 *
 * @author cliff.meyers
 */
public class JavaMethodParameter {

	//
	// Fields
	//

	/**
	 * Name of the parameter, if available.
	 */
	private String name;

	/**
	 * Type of the Java parameter.
	 */
	private Class<?> type;

	/**
	 * Annotations on the parameter.
	 */
	private Annotation[] annotations;

	//
	// Constructors
	//

	public JavaMethodParameter(String name, Class<?> type, Annotation[] annotations) {
		this.name = name;
		this.type = type;
		this.annotations = annotations;
	}

	//
	// Getters and Setters
	//

	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}

	public Annotation[] getAnnotations() {
		return annotations;
	}

}
