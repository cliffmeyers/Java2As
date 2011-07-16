package flexserverlib.java2as.as3;

import flexserverlib.java2as.as3.test.Color;
import flexserverlib.java2as.as3.transfer.test.User;
import flexserverlib.java2as.core.conf.TypeMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static org.junit.Assert.*;

public class DefaultAs3TypeMapperTests {

	private static TypeMapper<As3Type> mapper;

	@BeforeClass
	public static void setup() {
		mapper = new DefaultAs3TypeMapper();
	}

	@Test
	public void testEnumMapping() {
		assertTrue("Must map any enum type", mapper.canMapType(As3SimpleType.class));
		assertEquals("Must map any enum to String", As3SimpleType.String, mapper.mapType(Color.class));
	}

	@Test
	public void testStringMapping() {
		assertTrue("Must map String.class", mapper.canMapType(String.class));
		assertEquals("Must map String.class to String", As3SimpleType.String, mapper.mapType(String.class));
	}

	@Test
	public void testBooleanMapping() {
		assertTrue("Must map Boolean.class", mapper.canMapType(Boolean.class));
		assertTrue("Must map boolean.class", mapper.canMapType(boolean.class));
		assertEquals("Must map Boolean.class to Boolean", As3SimpleType.Boolean, mapper.mapType(Boolean.class));
		assertEquals("Must map boolean.class to Boolean", As3SimpleType.Boolean, mapper.mapType(boolean.class));
	}

	@Test
	public void testIntegerMapping() {
		assertTrue("Must map Integer.class", mapper.canMapType(Integer.class));
		assertTrue("Must map int.class", mapper.canMapType(int.class));
		assertEquals("Must map Integer.class to Number", As3SimpleType.Number, mapper.mapType(Integer.class));
		assertEquals("Must map int.class to Number", As3SimpleType.Number, mapper.mapType(int.class));
	}

	@Test
	public void testShortMapping() {
		assertTrue("Must map Short.class", mapper.canMapType(Short.class));
		assertTrue("Must map short.class", mapper.canMapType(short.class));
		assertEquals("Must map Short.class to Number", As3SimpleType.Number, mapper.mapType(Short.class));
		assertEquals("Must map short.class to Number", As3SimpleType.Number, mapper.mapType(short.class));
	}

	@Test
	public void testByteMapping() {
		assertTrue("Must map Byte.class", mapper.canMapType(Byte.class));
		assertTrue("Must map byte.class", mapper.canMapType(byte.class));
		assertEquals("Must map Byte.class to Number", As3SimpleType.Number, mapper.mapType(Byte.class));
		assertEquals("Must map byte.class to Number", As3SimpleType.Number, mapper.mapType(byte.class));
	}

	@Test
	public void testByteArrayMapping() {
		assertTrue("Must map Byte[]", mapper.canMapType(Byte[].class));
		assertTrue("Must map byte[]", mapper.canMapType(byte[].class));
		assertEquals("Must map Byte[].class to ByteArray", As3SimpleType.ByteArray, mapper.mapType(Byte[].class));
		assertEquals("Must map byte[].class to ByteArray", As3SimpleType.ByteArray, mapper.mapType(byte[].class));
	}

	@Test
	public void testDoubleMapping() {
		assertTrue("Must map Double.class", mapper.canMapType(Double.class));
		assertTrue("Must map double.class", mapper.canMapType(double.class));
		assertEquals("Must map Double.class to Number", As3SimpleType.Number, mapper.mapType(Double.class));
		assertEquals("Must map double.class to Number", As3SimpleType.Number, mapper.mapType(double.class));
	}

	@Test
	public void testLongMapping() {
		assertTrue("Must map Long.class", mapper.canMapType(Long.class));
		assertTrue("Must map long.class", mapper.canMapType(long.class));
		assertEquals("Must map Long.class to Number", As3SimpleType.Number, mapper.mapType(Long.class));
		assertEquals("Must map long.class to Number", As3SimpleType.Number, mapper.mapType(long.class));
	}

	@Test
	public void testFloatMapping() {
		assertTrue("Must map Float.class", mapper.canMapType(Float.class));
		assertTrue("Must map float.class", mapper.canMapType(float.class));
		assertEquals("Must map Float.class to Number", As3SimpleType.Number, mapper.mapType(Float.class));
		assertEquals("Must map float.class to Number", As3SimpleType.Number, mapper.mapType(float.class));
	}

	@Test
	public void testCharMapping() {
		assertTrue("Must map Character.class", mapper.canMapType(Character.class));
		assertTrue("Must map char.class", mapper.canMapType(char.class));
		assertEquals("Must map Character.class to String", As3SimpleType.String, mapper.mapType(Character.class));
		assertEquals("Must map char.class to String", As3SimpleType.String, mapper.mapType(char.class));
	}

	@Test
	public void testCharArrayMapping() {
		assertTrue("Must map Character[].class", mapper.canMapType(Character[].class));
		assertTrue("Must map char[].class", mapper.canMapType(char[].class));
		assertEquals("Must map Character[].class to String", As3SimpleType.String, mapper.mapType(Character[].class));
		assertEquals("Must map chart[].class to String", As3SimpleType.String, mapper.mapType(char[].class));
	}

	@Test
	public void testBigNumbersMapping() {
		assertTrue("Must map BigInteger.class", mapper.canMapType(BigInteger.class));
		assertTrue("Must map BigDecimal.class", mapper.canMapType(BigDecimal.class));
		assertEquals("Must map BigInteger.class to String", As3SimpleType.String, mapper.mapType(BigInteger.class));
		assertEquals("Must map BigDecimal.class to String", As3SimpleType.String, mapper.mapType(BigDecimal.class));
	}

	@Test
	public void testDateMapping() {
		assertTrue("Must map Calendar.class", mapper.canMapType(Calendar.class));
		assertTrue("Must map GregorianCalendar.class", mapper.canMapType(GregorianCalendar.class));
		assertTrue("Must map Date.class", mapper.canMapType(Date.class));
		assertTrue("Must map java.sql.Date.class", mapper.canMapType(java.sql.Date.class));
		assertEquals("Must map Calendar.class to Date", As3SimpleType.Date, mapper.mapType(Calendar.class));
		assertEquals("Must map GregorianCalendar.class to Date", As3SimpleType.Date, mapper.mapType(GregorianCalendar.class));
		assertEquals("Must map Date.class to Date", As3SimpleType.Date, mapper.mapType(Date.class));
		assertEquals("Must map java.sql.Date.class to Date", As3SimpleType.Date, mapper.mapType(java.sql.Date.class));
	}

	@Test
	public void testCollectionMapping() {
		assertTrue("Must map Collection.class to ArrayCollection", mapper.canMapType(Collection.class));
		assertTrue("Must map List.class to ArrayCollection", mapper.canMapType(List.class));
		assertTrue("Must map ArrayList.class to ArrayCollection", mapper.canMapType(ArrayList.class));
		assertEquals("Must map Collection.class to ArrayCollection", As3SimpleType.ArrayCollection, mapper.mapType(Collection.class));
		assertEquals("Must map List.class to ArrayCollection", As3SimpleType.ArrayCollection, mapper.mapType(List.class));
		assertEquals("Must map ArrayList.class to ArrayCollection", As3SimpleType.ArrayCollection, mapper.mapType(ArrayList.class));
	}

	@Test
	public void testArrayMapping() {
		assertTrue("Must map Long[].class", mapper.canMapType(Long[].class));
		assertTrue("Must map long[].class", mapper.canMapType(long[].class));
		assertEquals("Must map Long[].class to Array", As3SimpleType.Array, mapper.mapType(Long[].class));
		assertEquals("Must map long[].class to Array", As3SimpleType.Array, mapper.mapType(long[].class));
		assertNotSame("Must not map Byte[].class to Array", As3SimpleType.Array, mapper.mapType(Byte[].class));
		assertNotSame("Must not map byte[].class to Array", As3SimpleType.Array, mapper.mapType(byte[].class));
	}

	@Test
	public void testMapMapping() {
		assertTrue("Must map Map.class to Object", mapper.canMapType(Map.class));
		assertTrue("Must map HashMap.class to Object", mapper.canMapType(HashMap.class));
		assertEquals("Must map Map.class to Object", As3SimpleType.Object, mapper.mapType(Map.class));
		assertEquals("Must map HashMap.class to Object", As3SimpleType.Object, mapper.mapType(HashMap.class));
	}

	@Test
	public void testDictionaryMapping() {
		assertTrue("Must map Dictionary.class to Object", mapper.canMapType(Dictionary.class));
		assertTrue("Must map Hashtable.class to Object", mapper.canMapType(Hashtable.class));
		assertTrue("Must map Properties.class to Object", mapper.canMapType(Properties.class));
		assertEquals("Must map Dictionary.class to Object", As3SimpleType.Object, mapper.mapType(Dictionary.class));
		assertEquals("Must map Hashtable.class to Object", As3SimpleType.Object, mapper.mapType(Hashtable.class));
		assertEquals("Must map Properties.class to Object", As3SimpleType.Object, mapper.mapType(Properties.class));
	}

	@Test
	public void testXmlMapping() {
		assertTrue("Must map org.w3c.dom.Document to XML", mapper.canMapType(Document.class));
		assertEquals("Must map org.w3c.dom.Document to XML", As3SimpleType.Xml, mapper.mapType(Document.class));
	}

	@Test
	public void testCustomTypeMapping() {
		assertEquals("Must map User to custom type", mapper.mapType(User.class), new As3CustomType(User.class));
	}

}