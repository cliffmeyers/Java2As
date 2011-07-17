package flexserverlib.java2as.core.meta;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class JavaService {

	//
	// Fields
	//

	private Class<?> clazz;
	private List<JavaMethod> methods;

	//
	// Constructors
	//

	public JavaService(Class<?> clazz) {

		this.clazz = clazz;
		this.methods = new ArrayList<JavaMethod>();

		for (Method method : clazz.getMethods()) {
			JavaMethod javaMethod = new JavaMethod(method);
			methods.add(javaMethod);
		}

	}

	//
	// Public Methods
	//

	@Override
	public String toString() {
		return "JavaService{" + clazz + '}';
	}


	//
	// Getters and Setters
	//

	public String getPackageName() {
		return clazz.getPackage().getName();
	}

	public String getName() {
		return clazz.getName();
	}

	public String getSimpleName() {
		return clazz.getSimpleName();
	}

	public boolean hasSuperclass() {
		return !clazz.getSuperclass().equals(Object.class);
	}

	public Class<?> getSuperclass() {
		return clazz.getSuperclass();
	}

	public boolean hasInterfaces() {
		return clazz.getInterfaces().length > 0;
	}

	public Class<?>[] getInterfaces() {
		return clazz.getInterfaces();
	}

	public List<JavaMethod> getMethods() {
		return methods;
	}

	public boolean isImplementation() {
		return !clazz.isInterface();
	}

}
