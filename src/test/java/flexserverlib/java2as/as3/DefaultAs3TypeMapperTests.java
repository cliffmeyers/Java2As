package flexserverlib.java2as.as3;

import flexserverlib.java2as.core.conf.TypeMapper;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static org.junit.Assert.*;

public class DefaultAs3TypeMapperTests {
	private static TypeMapper<Class<?>, As3Type> mapper;

	@BeforeClass
	public static void setup() {
		mapper = new DefaultAs3TypeMapper();
	}

	@Test
	public void testEnumMapping() {
		// use our own As3Type enum for testing purposes
		assertTrue("Must map any enum type", mapper.canMap(As3Type.class));
		assertEquals("Must map any enum to String", As3Type.String, mapper.performMap(As3Type.class));
	}

	@Test
	public void testStringMapping() {
		assertTrue("Must map String.class", mapper.canMap(String.class));
		assertEquals("Must map String.class to String", As3Type.String, mapper.performMap(String.class));
	}

	@Test
	public void testBooleanMapping() {
		assertTrue("Must map Boolean.class", mapper.canMap(Boolean.class));
		assertTrue("Must map boolean.class", mapper.canMap(boolean.class));
		assertEquals("Must map Boolean.class to Boolean", As3Type.Boolean, mapper.performMap(Boolean.class));
		assertEquals("Must map boolean.class to Boolean", As3Type.Boolean, mapper.performMap(boolean.class));
	}

	@Test
	public void testIntegerMapping() {
		assertTrue("Must map Integer.class", mapper.canMap(Integer.class));
		assertTrue("Must map int.class", mapper.canMap(int.class));
		assertEquals("Must map Integer.class to Number", As3Type.Number, mapper.performMap(Integer.class));
		assertEquals("Must map int.class to Number", As3Type.Number, mapper.performMap(int.class));
	}

	@Test
	public void testShortMapping() {
		assertTrue("Must map Short.class", mapper.canMap(Short.class));
		assertTrue("Must map short.class", mapper.canMap(short.class));
		assertEquals("Must map Short.class to Number", As3Type.Number, mapper.performMap(Short.class));
		assertEquals("Must map short.class to Number", As3Type.Number, mapper.performMap(short.class));
	}

	@Test
	public void testByteMapping() {
		assertTrue("Must map Byte.class", mapper.canMap(Byte.class));
		assertTrue("Must map byte.class", mapper.canMap(byte.class));
		assertEquals("Must map Byte.class to Number", As3Type.Number, mapper.performMap(Byte.class));
		assertEquals("Must map byte.class to Number", As3Type.Number, mapper.performMap(byte.class));
	}

	@Test
	public void testByteArrayMapping() {
		assertTrue("Must map Byte[]", mapper.canMap(Byte[].class));
		assertTrue("Must map byte[]", mapper.canMap(byte[].class));
		assertEquals("Must map Byte[].class to ByteArray", As3Type.ByteArray, mapper.performMap(Byte[].class));
		assertEquals("Must map byte[].class to ByteArray", As3Type.ByteArray, mapper.performMap(byte[].class));
	}

	@Test
	public void testDoubleMapping() {
		assertTrue("Must map Double.class", mapper.canMap(Double.class));
		assertTrue("Must map double.class", mapper.canMap(double.class));
		assertEquals("Must map Double.class to Number", As3Type.Number, mapper.performMap(Double.class));
		assertEquals("Must map double.class to Number", As3Type.Number, mapper.performMap(double.class));
	}

	@Test
	public void testLongMapping() {
		assertTrue("Must map Long.class", mapper.canMap(Long.class));
		assertTrue("Must map long.class", mapper.canMap(long.class));
		assertEquals("Must map Long.class to Number", As3Type.Number, mapper.performMap(Long.class));
		assertEquals("Must map long.class to Number", As3Type.Number, mapper.performMap(long.class));
	}

	@Test
	public void testFloatMapping() {
		assertTrue("Must map Float.class", mapper.canMap(Float.class));
		assertTrue("Must map float.class", mapper.canMap(float.class));
		assertEquals("Must map Float.class to Number", As3Type.Number, mapper.performMap(Float.class));
		assertEquals("Must map float.class to Number", As3Type.Number, mapper.performMap(float.class));
	}

	@Test
	public void testCharMapping() {
		assertTrue("Must map Character.class", mapper.canMap(Character.class));
		assertTrue("Must map char.class", mapper.canMap(char.class));
		assertEquals("Must map Character.class to String", As3Type.String, mapper.performMap(Character.class));
		assertEquals("Must map char.class to String", As3Type.String, mapper.performMap(char.class));
	}

	@Test
	public void testCharArrayMapping() {
		assertTrue("Must map Character[].class", mapper.canMap(Character[].class));
		assertTrue("Must map char[].class", mapper.canMap(char[].class));
		assertEquals("Must map Character[].class to String", As3Type.String, mapper.performMap(Character[].class));
		assertEquals("Must map chart[].class to String", As3Type.String, mapper.performMap(char[].class));
	}

	@Test
	public void testBigNumbersMapping() {
		assertTrue("Must map BigInteger.class", mapper.canMap(BigInteger.class));
		assertTrue("Must map BigDecimal.class", mapper.canMap(BigDecimal.class));
		assertEquals("Must map BigInteger.class to String", As3Type.String, mapper.performMap(BigInteger.class));
		assertEquals("Must map BigDecimal.class to String", As3Type.String, mapper.performMap(BigDecimal.class));
	}

	@Test
	public void testDateMapping() {
		assertTrue("Must map Calendar.class", mapper.canMap(Calendar.class));
		assertTrue("Must map GregorianCalendar.class", mapper.canMap(GregorianCalendar.class));
		assertTrue("Must map Date.class", mapper.canMap(Date.class));
		assertTrue("Must map java.sql.Date.class", mapper.canMap(java.sql.Date.class));
		assertEquals("Must map Calendar.class to Date", As3Type.Date, mapper.performMap(Calendar.class));
		assertEquals("Must map GregorianCalendar.class to Date", As3Type.Date, mapper.performMap(GregorianCalendar.class));
		assertEquals("Must map Date.class to Date", As3Type.Date, mapper.performMap(Date.class));
		assertEquals("Must map java.sql.Date.class to Date", As3Type.Date, mapper.performMap(java.sql.Date.class));
	}

	@Test
	public void testCollectionMapping() {
		assertTrue("Must map Collection.class to ArrayCollection", mapper.canMap(Collection.class));
		assertTrue("Must map List.class to ArrayCollection", mapper.canMap(List.class));
		assertTrue("Must map ArrayList.class to ArrayCollection", mapper.canMap(ArrayList.class));
		assertEquals("Must map Collection.class to ArrayCollection", As3Type.ArrayCollection, mapper.performMap(Collection.class));
		assertEquals("Must map List.class to ArrayCollection", As3Type.ArrayCollection, mapper.performMap(List.class));
		assertEquals("Must map ArrayList.class to ArrayCollection", As3Type.ArrayCollection, mapper.performMap(ArrayList.class));
	}

	@Test
	public void testArrayMapping() {
		assertTrue("Must map Long[].class", mapper.canMap(Long[].class));
		assertTrue("Must map long[].class", mapper.canMap(long[].class));
		assertEquals("Must map Long[].class to Array", As3Type.Array, mapper.performMap(Long[].class));
		assertEquals("Must map long[].class to Array", As3Type.Array, mapper.performMap(long[].class));
		assertNotSame("Must not map Byte[].class to Array", As3Type.Array, mapper.performMap(Byte[].class));
		assertNotSame("Must not map byte[].class to Array", As3Type.Array, mapper.performMap(byte[].class));
	}

	@Test
	public void testMapMapping() {
		assertTrue("Must map Map.class to Object", mapper.canMap(Map.class));
		assertTrue("Must map HashMap.class to Object", mapper.canMap(HashMap.class));
		assertEquals("Must map Map.class to Object", As3Type.Object, mapper.performMap(Map.class));
		assertEquals("Must map HashMap.class to Object", As3Type.Object, mapper.performMap(HashMap.class));
	}

	@Test
	public void testDictionaryMapping() {
		assertTrue("Must map Dictionary.class to Object", mapper.canMap(Dictionary.class));
		assertTrue("Must map Hashtable.class to Object", mapper.canMap(Hashtable.class));
		assertTrue("Must map Properties.class to Object", mapper.canMap(Properties.class));
		assertEquals("Must map Dictionary.class to Object", As3Type.Object, mapper.performMap(Dictionary.class));
		assertEquals("Must map Hashtable.class to Object", As3Type.Object, mapper.performMap(Hashtable.class));
		assertEquals("Must map Properties.class to Object", As3Type.Object, mapper.performMap(Properties.class));
	}

	@Test
	public void testXmlMapping() {
		assertTrue("Must map org.w3c.dom.Document to XML", mapper.canMap(Document.class));
		assertEquals("Must map org.w3c.dom.Document to XML", As3Type.Xml, mapper.performMap(Document.class));
	}

	@Test
	public void testRemoteClassMapping() throws CannotCompileException {
		ClassPool pool = ClassPool.getDefault();
		String className = getClass().getName() + "TransferObject";
		CtClass clazz = pool.makeClass(className);
		Class<?> testClass = clazz.toClass();

		assertFalse("Must map Person to nothing", mapper.canMap(testClass));
		assertNull("Must map Person to nothing", mapper.performMap(testClass));
	}

}