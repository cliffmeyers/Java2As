package flexserverlib.java2as.core.meta;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class JavaServiceDelegate {

    private Class<?> clazz;
	private List<JavaMethod> methods;

    public JavaServiceDelegate(Class<?> clazz) {
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
		return "JavaServiceDelegate{" + clazz + '}';
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

}
