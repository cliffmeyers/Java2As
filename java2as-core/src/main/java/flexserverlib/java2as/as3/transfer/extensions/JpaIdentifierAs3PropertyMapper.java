package flexserverlib.java2as.as3.transfer.extensions;

import flexserverlib.java2as.as3.As3SimpleType;
import flexserverlib.java2as.as3.transfer.As3Property;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.meta.JavaProperty;

import java.lang.annotation.Annotation;

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
