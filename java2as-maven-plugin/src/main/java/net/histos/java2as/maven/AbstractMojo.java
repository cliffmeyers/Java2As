package net.histos.java2as.maven;

import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public abstract class AbstractMojo extends org.apache.maven.plugin.AbstractMojo {

	//
	// Fields
	//

	/**
	 * ClassLoader to use for loading 3rd-party classes for mappers, matchers, etc.
	 */
	protected ClassLoader classLoader;
	
	/**
	 * The maven project.
	 *
	 * @parameter default-value="${project}"
	 * @required
	 */
	protected MavenProject project;

	/**
	 * Locations of classes that are candidates for generation.
	 *
	 * @parameter
	 * @required
	 */
	protected File[] compiledClassesLocations;

	/**
	 * Custom TypeMapper class name to be used by java2as.
	 *
	 * @parameter
	 */
	protected String typeMapper;

	//
	// Protected Methods
	//

	protected ClassLoader getClassLoader() {

		// from: http://www.amateurinmotion.com/articles/2009/11/04/creating-classpath-from-compile-scope-elements-in-maven-mojo.html
		
		synchronized (TransferObjectMojo.class) {
			if (classLoader != null)
				return classLoader;
		}

		synchronized (TransferObjectMojo.class) {
			List<URL> urls = new ArrayList<URL>();
			try {
				for (Object object : project.getCompileClasspathElements()) {
					String path = (String) object;
					urls.add(new File(path).toURL());
				}
			} catch (Exception e) {
				throw new RuntimeException("Error occurred while building ClassLoader", e);
			}
			// set the parent ClassLoader to the thread's context class loader otherwise ClassCastExceptions will occur in loadConfiguratonClasses
			classLoader = new URLClassLoader(urls.toArray(new URL[]{}), Thread.currentThread().getContextClassLoader());
			//Thread.currentThread().setContextClassLoader(classLoader); // if needed
			return classLoader;
		}
		
	}
}
