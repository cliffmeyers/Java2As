package net.histos.java2as.maven;

import net.histos.java2as.as3.As3Type;
import net.histos.java2as.as3.transfer.TransferObjectConfiguration;
import net.histos.java2as.as3.transfer.TransferObjectProducer;
import net.histos.java2as.core.conf.*;
import net.histos.java2as.core.conf.packages.PackageMapperRule;
import net.histos.java2as.core.conf.packages.RuleBasedPackageMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

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
public class TransferObjectMojo extends net.histos.java2as.maven.AbstractMojo {

	private Logger _log = LoggerFactory.getLogger(getClass());

	//
	// Fields
	//

	// internal infrastructure

	/**
	 * The configuration object to supply to the producer.
	 */
	protected TransferObjectConfiguration config;

	/**
	 * The producer that creates the files.
	 */
	protected TransferObjectProducer producer;

	// parameters supplied by plugin end-users

	/**
	 * List of PropertyMapper class names to be used by java2as.
	 *
	 * @parameter
	 */
	private String[] propertyMappers = new String[]{};

	/**
	 * Custom PackageMapper class name to be used by java2as.
	 *
	 * @parameter
	 */
	private String packageMapper;

	/**
	 * List of PackageMapperRule class names to be used by java2as.
	 *
	 * @parameter
	 */
	private PackageMapperRule[] packageMapperRules = new PackageMapperRule[]{};

	/**
	 * List of TypeMatcher class names to be used by java2as.
	 *
	 * @parameter
	 */
	private String[] typeMatchers = new String[]{};

	/**
	 * Location to write custom classes.
	 *
	 * @parameter default-value="${project.build.directory}/java2as"
	 * @required
	 */
	private File customClassDir;

	/**
	 * Location to write base classes.
	 *
	 * @parameter default-value="${project.build.directory}/java2as"
	 * @required
	 */
	private File baseClassDir;

	/**
	 * Freemarker template to use for generation of custom classes.
	 *
	 * @parameter
	 */
	private File customClassTemplate;

	/**
	 * Freemarker template to use for generation of base classes.
	 *
	 * @parameter
	 */
	private File baseClassTemplate;

	/**
	 * Enable generation of the transfer object "manifest" file.
	 *
	 * @parameter
	 */
	private boolean generateManifest;

	//
	// Public Methods
	//

	public void execute() throws MojoExecutionException {

		StaticLoggerBinder.getSingleton().setMavenLog(this.getLog());

		config = new TransferObjectConfiguration();
		config.setBaseClassDir(baseClassDir);
		config.setCustomClassDir(customClassDir);
		config.setBaseClassTemplate(baseClassTemplate);
		config.setCustomClassTemplate(customClassTemplate);
		config.setGenerateManifest(generateManifest);
		loadConfiguratonClasses(config);

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
		producer = new TransferObjectProducer(config, candidateClasses);
		producer.produce();

	}

	protected void loadConfiguratonClasses(TransferObjectConfiguration config) throws MojoExecutionException {

		try {

			ClassLoader loader = getClassLoader();

			if (typeMapper != null) {
				Class<TypeMapper<As3Type>> typeMapperClass = (Class<TypeMapper<As3Type>>) loader.loadClass(typeMapper);
				config.setTypeMapper(typeMapperClass.newInstance());
			}

			if (propertyMappers.length > 0) {
				config.removeAllPropertyMappers();
				for (String propertyMapper : propertyMappers) {
					Class<PropertyMapper> propertyMapperClass = (Class<PropertyMapper>) loader.loadClass(propertyMapper);
					config.addPropertyMapper(propertyMapperClass.newInstance());
				}
			}

			if (packageMapper != null) {
				Class<PackageMapper> packageMapperClass = (Class<PackageMapper>) loader.loadClass(packageMapper);
				config.setPackageMapper(packageMapperClass.newInstance());
			}

			if (packageMapperRules.length > 0) {
				if (config.getPackageMapper() instanceof RuleBasedPackageMapper) {
					RuleBasedPackageMapper ruleBasedPackageMapper = (RuleBasedPackageMapper) config.getPackageMapper();
					for (PackageMapperRule packageMapperRule : packageMapperRules)
						ruleBasedPackageMapper.addMapperRule(packageMapperRule);
				} else {
					_log.warn("PackageMapper is not an instance of RuleBasedPackageMapper; packageMapperRules have been ignored");
				}
			}

			for (String matcher : typeMatchers) {
				Class<TypeMatcher> typeMatcherClass = (Class<TypeMatcher>) loader.loadClass(matcher);
				config.addTypeMatcher(typeMatcherClass.newInstance());
			}

		} catch (Exception e) {
			_log.error(e.getMessage());
			throw new MojoExecutionException(e.getMessage());
		}

	}


}
