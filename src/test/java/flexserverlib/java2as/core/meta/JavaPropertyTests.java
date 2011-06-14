package flexserverlib.java2as.core.meta;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class JavaPropertyTests {
	private static String CLASS_NAME;
	private static Class<?> CLASS;

	@BeforeClass
	public static void beforeClass() throws CannotCompileException {
		CLASS_NAME = JavaPropertyTests.class.getName() + "TransferObject";

		ClassPool pool = ClassPool.getDefault();
		CtClass testClass = pool.makeClass(CLASS_NAME);

		CLASS = testClass.toClass();
	}

	@Test
	public void testGetName() {

	}
}