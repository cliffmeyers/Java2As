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

	private String packageName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
