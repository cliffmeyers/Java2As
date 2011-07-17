package flexserverlib.java2as.maven;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.transfer.TransferObjectConfiguration;
import flexserverlib.java2as.as3.transfer.TransferObjectProducer;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.conf.TypeMatcher;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Generates transfer objects.
 *
 * @goal generate
 * @phase process-classes
 */
public class TransferObjectMojo extends AbstractMojo {

	//
	// Fields
	//

	// internal infrastructure

	/**
	 * ClassLoader to use for loading 3rd-party classes for mappers, matchers, etc.
	 */
	protected ClassLoader classLoader;

	/**
	 * The configuration object to supply to the producer.
	 */
	protected TransferObjectConfiguration config;

	/**
	 * The producer that creates the files.
	 */
	protected TransferObjectProducer producer;

	/**
	 * The maven project.
	 *
	 * @parameter default-value="${project}"
	 * @required
	 */
	protected MavenProject project;

	// parameters supplied by plugin end-users

	/**
	 * Locations of classes that are candidates for generation.
	 *
	 * @parameter
	 * @required
	 */
	private File[] compiledClassesLocations;

	/**
	 * Custom TypeMapper class name to be used by java2as.
	 *
	 * @parameter
	 */
	private String typeMapper;

	/**
	 * List of PropertyMapper class names to be used by java2as.
	 *
	 * @parameter
	 */
	private String[] propertyMappers = new String[]{};

	/**
	 * List of TypeMatcher class names to be used by java2as.
	 *
	 * @parameter
	 */
	private String[] typeMatchers = new String[]{};

	/**
	 * Location to write base classes.
	 *
	 * @parameter default-value="${project.build.directory}/java2as"
	 * @required
	 */
	private File baseClassDir;

	/**
	 * Location to write custom classes.
	 *
	 * @parameter default-value="${project.build.directory}/java2as"
	 * @required
	 */
	private File customClassDir;

	//
	// Public Methods
	//

	public void execute() throws MojoExecutionException {

		getLog().info("baseClassDir=" + baseClassDir.getAbsolutePath());
		getLog().info("customClassDir=" + customClassDir.getAbsolutePath());
		getLog().info("typeMapper=" + typeMapper);
		for (String mapper : propertyMappers)
			getLog().info("propertyMapper=" + mapper);
		for (String matcher : typeMatchers)
			getLog().info("typeMatcher=" + matcher);

		config = new TransferObjectConfiguration();
		config.setBaseClassDir(baseClassDir);
		config.setCustomClassDir(customClassDir);
		loadConfiguratonClasses(config);

		executeProduce();
	}

	//
	// Protected Methods
	//

	protected void executeProduce() {

		final String SLASH = File.separator;
		final String EXT = "class";
		final String DOT_EXT = "." + EXT;
		final String PACKAGE_DELIM = ".";

		List<String> candidateClassNames = new LinkedList<String>();

		for (File location : compiledClassesLocations) {
			if (!location.isDirectory())
				throw new IllegalArgumentException("Only directories can be supplied as a compiledClassLocation; error for " + location.getAbsolutePath());

			String sourceRootPath = location.getAbsolutePath();
			for (File file : FileUtils.listFiles(location, new String[]{EXT}, true)) {
				String filePath = file.getAbsolutePath();
				String packageFragment = filePath.substring(sourceRootPath.length(), filePath.length() - DOT_EXT.length());
				String className = StringUtils.replace(packageFragment, SLASH, PACKAGE_DELIM);
				if (className.startsWith(PACKAGE_DELIM))
					className = className.substring(PACKAGE_DELIM.length());
				candidateClassNames.add(className);
			}
		}

		// now let's load some classes!
		List<Class<?>> candidateClasses = new ArrayList<Class<?>>(500);

		ClassLoader loader = getClassLoader();
		for (String name : candidateClassNames) {
			try {
				Class<?> clazz = loader.loadClass(name);
				candidateClasses.add(clazz);
			} catch (ClassNotFoundException e) {
				getLog().warn("Could not load candidate class: " + name + "; will be ignored");
			}
		}

		if (candidateClasses.size() == 0) {
			getLog().warn("No candidate classes were found; produce will be skipped.");
			getLog().warn("This is probably due to a configuration error in compiledClassesLocations");
			return;
		}

		getLog().info("Candidate classes were found for generation: " + candidateClasses.size() + " total");
		producer = new TransferObjectProducer(config, candidateClasses);
		producer.produce();

	}

	protected void loadConfiguratonClasses(TransferObjectConfiguration config) throws MojoExecutionException {

		try {

			ClassLoader loader = getClassLoader();

			Class<TypeMapper<As3Type>> typeMapperClass = (Class<TypeMapper<As3Type>>) loader.loadClass(typeMapper);
			config.setTypeMapper(typeMapperClass.newInstance());

			for (String propertyMapper : propertyMappers) {
				Class<PropertyMapper> propertyMapperClass = (Class<PropertyMapper>) loader.loadClass(propertyMapper);
				config.addPropertyMapper(propertyMapperClass.newInstance());
			}

			for (String matcher : typeMatchers) {
				Class<TypeMatcher> typeMatcherClass = (Class<TypeMatcher>) loader.loadClass(matcher);
				config.addMatcher(typeMatcherClass.newInstance());
			}

			getLog().info("Configuration classes loaded successfully!");
			for (String line : config.getConfigurationSummary())
				getLog().info(line);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new MojoExecutionException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new MojoExecutionException(e.getMessage());
		}

	}

	// from: http://www.amateurinmotion.com/articles/2009/11/04/creating-classpath-from-compile-scope-elements-in-maven-mojo.html

	protected ClassLoader getClassLoader() {

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
