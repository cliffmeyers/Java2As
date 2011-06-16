package flexserverlib.java2as.as3.transfer;

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

		prop = mapper.performMap(new JavaProperty(CLASS.getField("objectArray")));
		assertTrue("objectArray should be an array type", prop.isArrayType());
		assertEquals("objectArray ArrayType should be Object", As3Type.Object, prop.getArrayType());

		prop = mapper.performMap(new JavaProperty(CLASS.getField("longArray")));
		assertTrue("longArray should be an array type", prop.isArrayType());
		assertEquals("longArray ArrayType should be Long.class", As3Type.Number, prop.getArrayType());

		prop = mapper.performMap(new JavaProperty(CLASS.getField("untypedList")));
		assertTrue("untypedList should be an array type", prop.isArrayType());
		assertEquals("untypedList ArrayType should be Object", As3Type.Object, prop.getArrayType());

		prop = mapper.performMap(new JavaProperty(CLASS.getField("typedList")));
		assertTrue("typedList should be an array type", prop.isArrayType());
		assertEquals("typedList ArrayType should be Long", As3Type.Number, prop.getArrayType());
		
	}
	
}