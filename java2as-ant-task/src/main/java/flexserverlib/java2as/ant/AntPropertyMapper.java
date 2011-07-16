package flexserverlib.java2as.ant;

/**
 * Allows the PropertyMapper class name to be specified in Ant build script.
 *
 * Usage:
 * <typedef name="propertyMapper" classname="flexserverlib.java2as.ant.AntPropertyMapper" classpathref="classpath.all" loaderref="classes.all"/>
 *
 * @author cliff.meyers
 */
public class AntPropertyMapper {

	private String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
