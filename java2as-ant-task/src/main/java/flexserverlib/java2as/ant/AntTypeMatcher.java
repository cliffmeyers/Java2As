package flexserverlib.java2as.ant;

/**
 * Allows the TypeMatcher class name to be specified in Ant build script.
 * <p/>
 * Usage:
 * &lt;typedef name="typeMatcher" classname="flexserverlib.java2as.ant.AntTypeMatcher" classpathref="classpath.all" loaderref="classes.all"/&gt;
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
