package flexserverlib.java2as.core.meta;

import java.lang.annotation.Annotation;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class JavaMethodParameter {

	//
	// Fields
	//

	private String name;
	private Class<?> type;
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
