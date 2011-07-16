package flexserverlib.java2as.ant;

/**
 * Allows the TypeMatcher class name to be specified in Ant build script.
 *
 * Usage:
 * <typedef name="typeMatcher" classname="flexserverlib.java2as.ant.AntTypeMatcher" classpathref="classpath.all" loaderref="classes.all"/>
 *
 * @author cliff.meyers
 */
public class AntTypeMatcher {

	private String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
