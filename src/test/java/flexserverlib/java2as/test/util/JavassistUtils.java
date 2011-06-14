package flexserverlib.java2as.test.util;

import javassist.*;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.SignatureAttribute;

public class JavassistUtils {
	public static void makeProperty(CtClass clazz, String name, Class<?> type) {
		try {
			String declaration;

			declaration = String.format("private %s %s;", type.getName(), name);
			clazz.addField(
					CtField.make(declaration, clazz)
			);
			String getter = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
			declaration = String.format("public %s %s() { return %s; }", type.getName(), getter, name);
			clazz.addMethod(
					CtMethod.make(declaration, clazz)
			);
			String setter = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
			declaration = String.format("public void %s( %s value ) { this.%s = value; }", setter, type.getName(), name, name);
			clazz.addMethod(
					CtMethod.make(declaration, clazz)
			);
		} catch (CannotCompileException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Adds generic type info to a dynamic field declaration
	 *
	 * @param field The field
	 * @param clazz Class to add as a generic parameter
	 * @return The updated original field
	 */
	public static void addGenericSignature(CtField field, Class<?> clazz) {
		try {
			FieldInfo fieldInfo = field.getFieldInfo();
			String fieldType = field.getType().getName().replace(".", "/");
			String genericType = clazz.getCanonicalName().replace(".", "/");
			SignatureAttribute attr = new SignatureAttribute(
					fieldInfo.getConstPool(),
					String.format("L%s<L%s;>;", fieldType, genericType)
			);
			fieldInfo.addAttribute(attr);
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds generic type info to a dynamic method declaration's return type
	 *
	 * @param method The method
	 * @param clazz  Class to add as a generic parameter
	 * @return The updated original method
	 */
	public static void addGenericSignature(CtMethod method, Class<?> clazz) {
		try {
			MethodInfo methodInfo = method.getMethodInfo();
			String fieldType = method.getReturnType().getName().replace(".", "/");
			String genericType = clazz.getCanonicalName().replace(".", "/");
			SignatureAttribute attr = new SignatureAttribute(
					methodInfo.getConstPool(),
					String.format("()L%s<L%s;>;", fieldType, genericType)
			);
			methodInfo.addAttribute(attr);
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}