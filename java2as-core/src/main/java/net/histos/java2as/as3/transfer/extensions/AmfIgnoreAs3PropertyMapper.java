package net.histos.java2as.as3.transfer.extensions;

import net.histos.java2as.as3.transfer.As3Property;
import net.histos.java2as.core.conf.PropertyMapper;
import net.histos.java2as.core.conf.TypeMapper;
import net.histos.java2as.core.meta.JavaProperty;

import java.lang.annotation.Annotation;

/**
 * Suppresses mapping of any property that uses the Spring-BlazeDS @AmfIgnore
 * Right now only getter methods with the annotation can be ignored.
 *
 * @author cliff.meyers
 */
public class AmfIgnoreAs3PropertyMapper implements PropertyMapper<As3Property> {

	public boolean canMapProperty(JavaProperty prop) {

		for (Annotation annotation : prop.getAnnotations()) {
			String annotationClassName = annotation.annotationType().getName();
			// TODO: add support for AmfIgnoreField; this first requires that TransferObjects can be built off classes using private fields as properties
			if ("org.springframework.flex.core.io.AmfIgnore".equals(annotationClassName))
				return true;
		}

		return false;

	}

	public As3Property mapProperty(JavaProperty prop) {

		return null;

	}

	public void setTypeMapper(TypeMapper typeMapper) {
		return;
	}
}
