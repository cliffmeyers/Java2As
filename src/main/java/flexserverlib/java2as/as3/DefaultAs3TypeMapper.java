package flexserverlib.java2as.as3;

import flexserverlib.java2as.core.conf.TypeMapper;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Maps Java types to ActionScript according to:
 * http://livedocs.adobe.com/blazeds/1/blazeds_devguide/help.html?content=serialize_data_3.html#304283
 *
 * @author cliff.meyers
 */
public class DefaultAs3TypeMapper implements TypeMapper<As3Type> {

	private Map<Class<?>, As3Type> defaultMappings;

	public DefaultAs3TypeMapper() {
		// mappings for final classes that we can check explicitly
		defaultMappings = new HashMap<Class<?>, As3Type>();
		defaultMappings.put(Object.class, As3Type.Object);
		defaultMappings.put(String.class, As3Type.String);
		defaultMappings.put(Boolean.class, As3Type.Boolean);
		defaultMappings.put(boolean.class, As3Type.Boolean);
		defaultMappings.put(Integer.class, As3Type.Number);
		defaultMappings.put(int.class, As3Type.Number);
		defaultMappings.put(Short.class, As3Type.Number);
		defaultMappings.put(short.class, As3Type.Number);
		defaultMappings.put(Byte.class, As3Type.Number);
		defaultMappings.put(byte.class, As3Type.Number);
		defaultMappings.put(Double.class, As3Type.Number);
		defaultMappings.put(double.class, As3Type.Number);
		defaultMappings.put(Long.class, As3Type.Number);
		defaultMappings.put(long.class, As3Type.Number);
		defaultMappings.put(Float.class, As3Type.Number);
		defaultMappings.put(float.class, As3Type.Number);
		defaultMappings.put(Character.class, As3Type.String);
		defaultMappings.put(char.class, As3Type.String);
		defaultMappings.put(BigInteger.class, As3Type.String);
		defaultMappings.put(BigDecimal.class, As3Type.String);
	}

	public boolean canMap(Class<?> javaType) {
		if (javaType == null) return true;
		if (javaType.isEnum()) return true;
		if (javaType.isArray()) return true;
		if (Date.class.isAssignableFrom(javaType) || Calendar.class.isAssignableFrom(javaType)) return true;
		if (Collection.class.isAssignableFrom(javaType)) return true;
		if (Map.class.isAssignableFrom(javaType) || Dictionary.class.isAssignableFrom(javaType)) return true;
		if (Document.class.isAssignableFrom(javaType)) return true;
		return defaultMappings.containsKey(javaType);
	}

	public As3Type performMap(Class<?> javaType) {
		if (javaType == null) return null;
		if (javaType.isEnum()) return As3Type.String;
		if (javaType.isArray()) {
			if (javaType.getComponentType().equals(byte.class) || javaType.getComponentType().equals(Byte.class))
				return As3Type.ByteArray;
			else if (javaType.getComponentType().equals(char.class) || javaType.getComponentType().equals(Character.class))
				return As3Type.String;
			else
				return As3Type.Array;
		}
		if (Date.class.isAssignableFrom(javaType) || Calendar.class.isAssignableFrom(javaType))
			return As3Type.Date;
		if (Collection.class.isAssignableFrom(javaType))
			return As3Type.ArrayCollection;
		if (Map.class.isAssignableFrom(javaType) || Dictionary.class.isAssignableFrom(javaType))
			return As3Type.Object;
		if (Document.class.isAssignableFrom(javaType))
			return As3Type.Xml;
		
		As3Type type = defaultMappings.get(javaType);
		if (type != null)
			return type;
		else
			return As3Type.RemoteClass;
	}
}