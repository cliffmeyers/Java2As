package net.histos.java2as.maven;

import net.histos.java2as.as3.As3Type;
import net.histos.java2as.as3.service.MethodMapper;
import net.histos.java2as.as3.service.ServiceDelegateConfiguration;
import net.histos.java2as.as3.service.ServiceDelegateProducer;
import net.histos.java2as.core.conf.TypeMapper;
import net.histos.java2as.core.conf.TypeMatcher;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Generates service delegates.
 *
 * @goal generate-services
 * @phase process-classes
 *
 * @author cliff.meyers
 */
public class ServiceDelegateMojo extends AbstractMojo {

	private Logger _log = LoggerFactory.getLogger(getClass());

	//
	// Fields
	//

	// internal infrastructure

	/**
	 * The configuration object to supply to the producer.
	 */
	protected ServiceDelegateConfiguration config;

	/**
	 * The producer that creates the files.
	 */
	protected ServiceDelegateProducer producer;

	// user-supplied config params

	/**
	 * Custom MethodMapper class name to be used by java2as.
	 *
	 * @parameter
	 */
	private String methodMapper;

	/**
	 * Location to write generated service classes
	 *
	 * @parameter default-value="${project.build.directory}/java2as"
	 * @required
	 */
	private File serviceImplDir;

	/**
	 * Custom Freemarker template to use for generation services.
	 *
	 * @parameter
	 */
	private File serviceImplTemplate;

	/**
	 * Provide a base class which all Service Delegates will extend.
	 *
	 * @parameter
	 */
	private String serviceDelegateBaseClass;

	//
	// Public Methods
	//

	public void execute() throws MojoExecutionException, MojoFailureException {

		super.execute();

		config = new ServiceDelegateConfiguration();
		config.setServiceImplDir(serviceImplDir);
		config.setServiceImplTemplate(serviceImplTemplate);
		config.setServiceDelegateBaseClass(serviceDelegateBaseClass);
		loadConfiguratonClasses();

		_log.info("Configuration classes loaded successfully!");
		config.logConfiguration();

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

			// convert full path to class file to fully-qualified Java class name
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
				_log.warn("Could not load candidate class: " + name + "; will be ignored");
			}
		}

		if (candidateClasses.size() == 0) {
			_log.warn("No candidate classes were found; produce will be skipped.");
			_log.warn("This is probably due to a configuration error in compiledClassesLocations");
			return;
		}

		_log.info("Candidate classes were found for generation: " + candidateClasses.size() + " total");
		producer = new ServiceDelegateProducer(config, candidateClasses);
		producer.produce();

	}

	protected void loadConfiguratonClasses() throws MojoExecutionException {

		super.loadConfigurationClasses();
		
		try {

			ClassLoader loader = getClassLoader();

			if (methodMapper != null) {
				Class<MethodMapper> methodMapperClass = (Class<MethodMapper>) loader.loadClass(methodMapper);
				config.setMethodMapper(methodMapperClass.newInstance());
			}

		} catch (Exception e) {
			_log.error(e.getMessage());
			throw new MojoExecutionException(e.getMessage());
		}

	}


}
