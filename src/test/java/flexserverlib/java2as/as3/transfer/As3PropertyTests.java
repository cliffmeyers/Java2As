package flexserverlib.java2as.as3.transfer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;

import org.junit.BeforeClass;
import org.junit.Test;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.meta.JavaProperty;
import flexserverlib.java2as.test.util.JavassistUtils;

public class As3PropertyTests
{
	private static Class<?> TEST_CLASS;
	
	@BeforeClass
	public static void setup() throws CannotCompileException
	{
		ClassPool pool = ClassPool.getDefault();
		CtClass testClass = pool.makeClass( As3PropertyTests.class.getName() + "TransferObject");
		testClass.addField(
			CtField.make("public Object[] objectArray;", testClass)
		);
		testClass.addField(
				CtField.make("public Long[] longArray;", testClass)
			);
		testClass.addField(
			CtField.make("public java.util.List untypedList;", testClass)
		);
		CtField field = CtField.make("public java.util.List typedList;", testClass);
		JavassistUtils.addGenericSignature( field, Long.class );
		testClass.addField( field );
		TEST_CLASS = testClass.toClass();
	}
	
	@Test
	public void testGetArrayType() throws SecurityException, NoSuchFieldException
	{
		As3Property prop;
		PropertyMapper<JavaProperty,As3Type> mapper = new DefaultAs3PropertyMapper();
		
		prop = new As3Property( new JavaProperty( TEST_CLASS.getField( "objectArray" ) ), mapper );
		assertTrue( "objectArray should be an array type", prop.isArrayType() );
		assertEquals( "objectArray ArrayType should be Object.class", null, prop.getArrayType() );
		
		prop = new As3Property( new JavaProperty( TEST_CLASS.getField( "longArray" ) ), mapper );
		assertTrue( "longArray should be an array type", prop.isArrayType() );
		assertEquals( "longArray ArrayType should be Long.class", As3Type.Number, prop.getArrayType() );
		
		prop = new As3Property( new JavaProperty( TEST_CLASS.getField( "untypedList" ) ), mapper );
		assertTrue( "untypedList should be an array type", prop.isArrayType() );
		assertEquals( "untypedList ArrayType should be null", null, prop.getArrayType() );
		
		prop = new As3Property( new JavaProperty( TEST_CLASS.getField( "typedList" ) ), mapper );
		assertTrue( "typedList should be an array type", prop.isArrayType() );
		assertEquals( "typedList ArrayType should be Long", As3Type.Number, prop.getArrayType() );
	}
}