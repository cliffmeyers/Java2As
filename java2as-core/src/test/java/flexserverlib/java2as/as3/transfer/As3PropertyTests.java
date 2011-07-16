package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3SimpleType;
import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.transfer.test.ArrayProperties;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.meta.JavaProperty;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class As3PropertyTests {

	private static Class<?> CLASS = ArrayProperties.class;

	@Test
	public void testGetArrayType() throws SecurityException, NoSuchFieldException {

		As3Property prop;
		As3Type type;
		PropertyMapper<As3Property> mapper = new DefaultAs3PropertyMapper();

		prop = mapper.mapProperty(new JavaProperty(CLASS.getField("objectArray")));
		assertTrue("objectArray should be an array type", prop.isArrayType());
		assertEquals("objectArray ArrayType should be Object", As3SimpleType.Object, prop.getArrayElementType());

		prop = mapper.mapProperty(new JavaProperty(CLASS.getField("longArray")));
		assertTrue("longArray should be an array type", prop.isArrayType());
		assertEquals("longArray ArrayType should be Long.class", As3SimpleType.Number, prop.getArrayElementType());

		prop = mapper.mapProperty(new JavaProperty(CLASS.getField("untypedList")));
		assertTrue("untypedList should be an array type", prop.isArrayType());
		assertEquals("untypedList ArrayType should be Object", As3SimpleType.Object, prop.getArrayElementType());

		prop = mapper.mapProperty(new JavaProperty(CLASS.getField("wildcardList")));
		assertTrue("wildcardList should be an array type", prop.isArrayType());
		assertEquals("wildcardList ArrayType should be Object", As3SimpleType.Object, prop.getArrayElementType());

		prop = mapper.mapProperty(new JavaProperty(CLASS.getField("typedList")));
		assertTrue("typedList should be an array type", prop.isArrayType());
		assertEquals("typedList ArrayType should be Long", As3SimpleType.Number, prop.getArrayElementType());
		
	}
	
}