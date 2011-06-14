package flexserverlib.java2as.test.util;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class JavassistUtilsTests {
	private static String CLASS_NAME;
	private static CtClass CTCLASS;

	@BeforeClass
	public static void beforeClass() throws CannotCompileException {
		CLASS_NAME = JavassistUtilsTests.class.getName() + "TransferObject";
		ClassPool pool = ClassPool.getDefault();
		CTCLASS = pool.makeClass(CLASS_NAME);
	}

	@Test
	public void testMakeProperty() throws SecurityException, NoSuchMethodException, CannotCompileException, NoSuchFieldException {
		JavassistUtils.makeProperty(CTCLASS, "name", String.class);
		Class<?> clazz = CTCLASS.toClass();
		assertNotNull("clazz must have method called getName", clazz.getDeclaredMethod("getName"));
		assertNotNull("clazz must have method called setName", clazz.getDeclaredMethod("setName", String.class));
		assertNotNull("clazz must have field called name", clazz.getDeclaredField("name"));
	}
}
