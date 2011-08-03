package net.histos.java2as.test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * @author cliff.meyers
 */
public class ReflectUtils {

	public static PropertyDescriptor getProperty(Class<?> clazz, String propertyName) {

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor prop : props)
				if (propertyName.equals(prop.getName()))
					return prop;
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}

		throw new RuntimeException("Property not found: " + propertyName);

	}

}
