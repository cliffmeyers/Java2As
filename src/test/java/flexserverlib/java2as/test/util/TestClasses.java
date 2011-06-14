package flexserverlib.java2as.test.util;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;

public class TestClasses {
	private static Class<?> DEFAULT_TEST_CLASS;

	public static Class<?> getDefaultTestClass() {
		if (DEFAULT_TEST_CLASS == null) {
			ClassPool pool = ClassPool.getDefault();
			CtClass clazz = pool.makeClass(TestClasses.class.getName() + "DefaultTestClass");
			JavassistUtils.makeProperty(clazz, "name", String.class);
			try {
				DEFAULT_TEST_CLASS = clazz.toClass();
			} catch (CannotCompileException e) {
				throw new RuntimeException(e);
			}
		}
		return DEFAULT_TEST_CLASS;
	}
}