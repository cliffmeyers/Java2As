package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.meta.JavaProperty;
import freemarker.core.ReturnInstruction;

import java.lang.annotation.Annotation;

public class JpaIdentifierAs3PropertyMapper implements PropertyMapper<As3Property> {

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
	public As3Property performMap(JavaProperty type) {
		if (!canMap(type)) return null;
		return new As3Property(type, As3Type.Object);
	}
	
}
