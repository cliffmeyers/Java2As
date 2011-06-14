package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.meta.JavaProperty;

import java.lang.annotation.Annotation;

public class JpaIdentifierAs3PropertyMapper implements TypeMapper<JavaProperty, As3Type> {
	/**
	 * Checks or javax.persistence.Id and returns true is available
	 */
	public boolean canMap(JavaProperty type) {
		for (Annotation annotation : type.getAnnotations())
			if ("javax.persistence.Id".equals(annotation.annotationType().getName()))
				return true;
		return false;
	}

	/**
	 * Returns As3Type.Object for the identifier, to support nullability
	 */
	public As3Type performMap(JavaProperty type) {
		if (!canMap(type)) return null;
		return As3Type.Object;
	}
}
