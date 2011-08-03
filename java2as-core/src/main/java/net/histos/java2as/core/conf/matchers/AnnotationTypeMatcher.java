package net.histos.java2as.core.conf.matchers;

import org.apache.commons.lang.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Matches classes based on the specified.
 *
 * @author cliff.meyers
 */
public class AnnotationTypeMatcher extends DefaultTypeMatcher {

	//
	// Fields
	//

	private String qualifiedAnnotationName;

	//
	// Constructors
	//

	/**
	 * @param qualifiedAnnotationName The fully-qualified annotation name to check for.
	 */
	public AnnotationTypeMatcher(String qualifiedAnnotationName) {
		this.qualifiedAnnotationName = qualifiedAnnotationName;
	}

	/**
	 * @inheritDoc
	 */
	public boolean match(Class<?> clazz) {

		if (!super.match(clazz))
			return false;

		List<Class> superclasses = ClassUtils.getAllSuperclasses(clazz);
		superclasses.add(clazz);

		for (Class<?> superclass : superclasses)
			for (Annotation annotation : superclass.getAnnotations())
				if (qualifiedAnnotationName.equals(annotation.annotationType().getName()))
					return true;

		List<Class> interfaces = ClassUtils.getAllInterfaces(clazz);

		for (Class<?> interfaceClass : interfaces)
			for (Annotation annotation : interfaceClass.getAnnotations())
				if (qualifiedAnnotationName.equals(annotation.annotationType().getName()))
					return true;



		return false;

	}

}