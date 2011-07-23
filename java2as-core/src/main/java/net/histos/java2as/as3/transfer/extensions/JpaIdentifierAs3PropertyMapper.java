package net.histos.java2as.as3.transfer.extensions;

import net.histos.java2as.as3.As3SimpleType;
import net.histos.java2as.as3.transfer.As3Property;
import net.histos.java2as.core.conf.PropertyMapper;
import net.histos.java2as.core.conf.TypeMapper;
import net.histos.java2as.core.meta.JavaProperty;

import java.lang.annotation.Annotation;

/**
 * Provides a nullable type for properties annotated as JPA Identifiers (@Id)
 */
public class JpaIdentifierAs3PropertyMapper implements PropertyMapper<As3Property> {

	/**
	 * Checks or javax.persistence.Id and returns true is available
	 */
	public boolean canMapProperty(JavaProperty type) {
		for (Annotation annotation : type.getAnnotations())
			if ("javax.persistence.Id".equals(annotation.annotationType().getName()))
				return true;
		return false;
	}

	/**
	 * Returns As3SimpleType.Object for the identifier, to support nullability
	 */
	public As3Property mapProperty(JavaProperty type) {
		return new As3Property(type, As3SimpleType.Object);
	}

	public void setTypeMapper(TypeMapper typeMapper) {
		return;
	}

}
