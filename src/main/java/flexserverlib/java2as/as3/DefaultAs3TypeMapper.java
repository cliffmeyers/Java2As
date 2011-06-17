package flexserverlib.java2as.as3;

import flexserverlib.java2as.core.conf.TypeMapper;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author cliff.meyers
 */
public class DefaultAs3TypeMapper implements TypeMapper<As3Type> {

	private Map<Class<?>, As3SimpleType> defaultMappings;

	public DefaultAs3TypeMapper() {
		// mappings for classes that we can check explicitly
		defaultMappings = new HashMap<Class<?>, As3SimpleType>();
		defaultMappings.put(Object.class, As3SimpleType.Object);
		defaultMappings.put(String.class, As3SimpleType.String);
		defaultMappings.put(Boolean.class, As3SimpleType.Boolean);
		defaultMappings.put(boolean.class, As3SimpleType.Boolean);
		defaultMappings.put(Integer.class, As3SimpleType.Number);
		defaultMappings.put(int.class, As3SimpleType.Number);
		defaultMappings.put(Short.class, As3SimpleType.Number);
		defaultMappings.put(short.class, As3SimpleType.Number);
		defaultMappings.put(Byte.class, As3SimpleType.Number);
		defaultMappings.put(byte.class, As3SimpleType.Number);
		defaultMappings.put(Double.class, As3SimpleType.Number);
		defaultMappings.put(double.class, As3SimpleType.Number);
		defaultMappings.put(Long.class, As3SimpleType.Number);
		defaultMappings.put(long.class, As3SimpleType.Number);
		defaultMappings.put(Float.class, As3SimpleType.Number);
		defaultMappings.put(float.class, As3SimpleType.Number);
		defaultMappings.put(Character.class, As3SimpleType.String);
		defaultMappings.put(char.class, As3SimpleType.String);
		defaultMappings.put(BigInteger.class, As3SimpleType.String);
		defaultMappings.put(BigDecimal.class, As3SimpleType.String);
	}

	public boolean canMapType(Class<?> javaType) {
		return true;
	}

	public As3Type mapType(Class<?> javaType) {
		if (javaType == null) return null;
		if (javaType.isEnum()) return As3SimpleType.String;
		if (javaType.isArray()) {
			if (javaType.getComponentType().equals(byte.class) || javaType.getComponentType().equals(Byte.class))
				return As3SimpleType.ByteArray;
			else if (javaType.getComponentType().equals(char.class) || javaType.getComponentType().equals(Character.class))
				return As3SimpleType.String;
			else
				return As3SimpleType.Array;
		}
		if (Date.class.isAssignableFrom(javaType) || Calendar.class.isAssignableFrom(javaType))
			return As3SimpleType.Date;
		if (Collection.class.isAssignableFrom(javaType))
			return As3SimpleType.ArrayCollection;
		if (Map.class.isAssignableFrom(javaType) || Dictionary.class.isAssignableFrom(javaType))
			return As3SimpleType.Object;
		if (Document.class.isAssignableFrom(javaType))
			return As3SimpleType.Xml;

		if (defaultMappings.containsKey(javaType))
			return defaultMappings.get(javaType);

		return new As3CustomType(javaType);
	}

}
