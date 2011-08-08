package net.histos.java2as.ant;

/**
 * Allows the package name used by PackageTypeMatcher to be specified in Ant build script.
 * <p/>
 * Usage:
 * &lt;typedef name="packageMatcher" classname="net.histos.java2as.ant.AntPackageMatcher" classpathref="classpath.all" loaderref="classes.all"/&gt;
 *
 * @author cliff.meyers
 */
public class AntPackageMatcher {

	private String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
