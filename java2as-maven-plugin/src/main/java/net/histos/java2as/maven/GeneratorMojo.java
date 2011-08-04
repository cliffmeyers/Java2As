package net.histos.java2as.maven;

import net.histos.java2as.as3.AbstractAs3Configuration;
import net.histos.java2as.as3.As3Type;
import net.histos.java2as.core.conf.TypeMapper;
import net.histos.java2as.core.conf.TypeMatcher;
import net.histos.java2as.core.conf.matchers.AnnotationTypeMatcher;
import net.histos.java2as.core.conf.matchers.DefaultTypeMatcher;
import net.histos.java2as.core.conf.matchers.InterfaceTypeMatcher;
import net.histos.java2as.core.conf.matchers.SuperclassTypeMatcher;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

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
public abstract class GeneratorMojo<C extends AbstractAs3Configuration> extends AbstractMojo {

	private Logger _log = LoggerFactory.getLogger(getClass());

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
	 * The configuration object to supply to the producer.
	 */
	protected C config;

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

	/**
	 * List of TypeMatcher class names to be used by java2as.
	 *
	 * @parameter
	 */
	protected String[] typeMatchers = new String[]{};

	/**
	 * Name of the superclass to match on using a default TypeMatcher.
	 *
	 * @parameter
	 * @see net.histos.java2as.core.conf.matchers.SuperclassTypeMatcher
	 */
	protected String superclassName;

	/**
	 * Name of the interface to match on using a default TypeMatcher.
	 *
	 * @parameter
	 * @see net.histos.java2as.core.conf.matchers.InterfaceTypeMatcher
	 */
	protected String interfaceName;

	/**
	 * Name of the annotation to match on using a default TypeMatcher.
	 *
	 * @parameter
	 * @see net.histos.java2as.core.conf.matchers.AnnotationTypeMatcher
	 */
	protected String annotationName;

	//
	// Public Methods
	//

	public void execute() throws MojoExecutionException, MojoFailureException {
		StaticLoggerBinder.getSingleton().setMavenLog(this.getLog());
	}

	//
	// Protected Methods
	//

	protected void loadConfigurationClasses() throws MojoExecutionException {

		ClassLoader loader = getClassLoader();

		try {

			if (typeMapper != null) {
				Class<TypeMapper<As3Type>> typeMapperClass = (Class<TypeMapper<As3Type>>) loader.loadClass(typeMapper);
				config.setTypeMapper(typeMapperClass.newInstance());
			}

			loadTypeMatchers();

		} catch (ClassNotFoundException cnfe) {
			_log.error("Error occurred while loading configuration classes, could not load class: " + cnfe.getMessage());
			throw new MojoExecutionException("Error occurred while loading configuration classes", cnfe);
		} catch (Exception e) {
			_log.error("Error occurred while loading configuration classes: " + e.getMessage());
			throw new MojoExecutionException("Error occurred while loading configuration classes", e);
		}
	}

	protected void loadTypeMatchers() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

		ClassLoader loader = getClassLoader();

		// use the custom type matchers, if specified
		if (typeMatchers != null && !ArrayUtils.isEmpty(typeMatchers)) {
			for (String matcher : typeMatchers) {
				Class<TypeMatcher> typeMatcherClass = (Class<TypeMatcher>) loader.loadClass(matcher);
				config.addTypeMatcher(typeMatcherClass.newInstance());
			}
		} else {
			// otherwise fall back to the simple properties and default type matchers

			// support multiple default type matchers, even though this is an unlikely use case
			if (!StringUtils.isEmpty(superclassName))
				config.addTypeMatcher(new SuperclassTypeMatcher(superclassName));

			if (!StringUtils.isEmpty(interfaceName))
				config.addTypeMatcher(new InterfaceTypeMatcher(interfaceName));

			if (!StringUtils.isEmpty(annotationName))
				config.addTypeMatcher(new AnnotationTypeMatcher(annotationName));
		}

		// if all else fails, use the reasonable default
		if (config.getTypeMatchers().size() == 0)
			config.addTypeMatcher(new DefaultTypeMatcher());

	}

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
