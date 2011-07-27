package net.histos.java2as.ant;

import net.histos.java2as.as3.As3Type;
import net.histos.java2as.as3.transfer.TransferObjectConfiguration;
import net.histos.java2as.as3.transfer.TransferObjectProducer;
import net.histos.java2as.core.conf.PropertyMapper;
import net.histos.java2as.core.conf.TypeMapper;
import net.histos.java2as.core.conf.TypeMatcher;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Generates transfer objects
 */
public class TransferObjectTask extends Task {

	private Logger _log = LoggerFactory.getLogger(getClass());

	//
	// Fields
	//

	// internal infrastructure

	/**
	 * The configuration object to supply to the producer.
	 */
	private TransferObjectConfiguration config;

	/**
	 * The producer that creates the files.
	 */
	protected TransferObjectProducer producer;

	// parameters supplied by task end-users

	/**
	 * Locations of classes that are candidates for generation.
	 */
	private List<FileSet> fileSets = new ArrayList<FileSet>();

	/**
	 * Custom TypeMapper class name to be used by java2as.
	 */
	private String typeMapper;

	/**
	 * List of PropertyMapper class names to be used by java2as.
	 */
	private List<AntPropertyMapper> propertyMappers = new ArrayList<AntPropertyMapper>();

	/**
	 * List of TypeMatcher class names to be used by java2as.
	 */
	private List<AntTypeMatcher> typeMatchers = new ArrayList<AntTypeMatcher>();

	/**
	 *
	 */
	private File customClassDir;

	/**
	 *
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
	 */
	private boolean generateManifest;

	/**
	 * Include [ArrayElementType] metadata for Array and ArrayCollection types.
	 */
	private boolean includeArrayElementType;

	/**
	 * Provide a base class which all Transfer Objects will extend.
	 */
	private String transferObjectBaseClass;

	//
	// Public Methods
	//

	@Override
	public void init() throws BuildException {
		super.init();
		config = new TransferObjectConfiguration();
	}

	@Override
	public void execute() throws BuildException {

		config.setCustomClassDir(customClassDir);
		config.setBaseClassDir(baseClassDir);
		config.setCustomClassTemplate(customClassTemplate);
		config.setBaseClassTemplate(baseClassTemplate);
		config.setGenerateManifest(generateManifest);
		config.setIncludeArrayElementType(includeArrayElementType);
		config.setTransferObjectBaseClass(transferObjectBaseClass);
		loadConfiguratonClasses(config);

		_log.info("Configuration classes loaded successfully!");
		config.logConfiguration();

		executeProduce();

	}

	public void addConfigured(AntPropertyMapper propertyMapper) {
		propertyMappers.add(propertyMapper);
	}

	public void addConfigured(AntTypeMatcher matcher) {
		typeMatchers.add(matcher);
	}

	public void addFileset(FileSet fileset) {
		fileSets.add(fileset);
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

		for (FileSet fileSet : fileSets) {
			for (String filePath : fileSet.getDirectoryScanner().getIncludedFiles()) {
				if (filePath.endsWith(DOT_EXT)) {
					String className = filePath.substring(0, filePath.length() - DOT_EXT.length());
					className = StringUtils.replace(className, SLASH, PACKAGE_DELIM);
					candidateClassNames.add(className);
				}
			}
		}

		// now let's load some classes!
		List<Class<?>> candidateClasses = new ArrayList<Class<?>>(500);

		for (String name : candidateClassNames) {
			try {
				Class<?> clazz = Class.forName(name);
				candidateClasses.add(clazz);
			} catch (ClassNotFoundException e) {
				System.out.println("Could not load candidate class: " + name + "; will be ignored");
			}
		}

		if (candidateClasses.size() == 0) {
			System.out.println("No candidate classes were found; produce will be skipped.");
			System.out.println("This is probably due to a configuration error in compiledClassesLocations");
			return;
		}

		_log.info("Candidate classes were found for generation: " + candidateClasses.size() + " total");
		producer = new TransferObjectProducer(config, candidateClasses);
		producer.produce();

	}

	protected void loadConfiguratonClasses(TransferObjectConfiguration config) {

		try {

			if (typeMapper != null) {
				Class<TypeMapper<As3Type>> typeMapperClass = (Class<TypeMapper<As3Type>>) Class.forName(typeMapper);
				config.setTypeMapper(typeMapperClass.newInstance());
			}

			if (propertyMappers.size() > 0) {
				config.removeAllPropertyMappers();
				for (AntPropertyMapper propertyMapper : propertyMappers) {
					Class<PropertyMapper> propertyMapperClass = (Class<PropertyMapper>) Class.forName(propertyMapper.getClassName());
					config.addPropertyMapper(propertyMapperClass.newInstance());
				}
			}

			for (AntTypeMatcher matcher : typeMatchers) {
				Class<TypeMatcher> typeMatcherClass = (Class<TypeMatcher>) Class.forName(matcher.getClassName());
				config.addTypeMatcher(typeMatcherClass.newInstance());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e.getMessage());
		}

	}

	//
	// Getters and Setters
	//

	public void setTypeMapper(String value) {
		typeMapper = value;
	}

	public void setCustomClassDir(File value) {
		customClassDir = value;
	}

	public void setBaseClassDir(File value) {
		baseClassDir = value;
	}

	public void setCustomClassTemplate(File customClassTemplate) {
		this.customClassTemplate = customClassTemplate;
	}

	public void setBaseClassTemplate(File baseClassTemplate) {
		this.baseClassTemplate = baseClassTemplate;
	}

	public void setGenerateManifest(boolean generateManifest) {
		this.generateManifest = generateManifest;
	}

	public void setIncludeArrayElementType(boolean includeArrayElementType) {
		this.includeArrayElementType = includeArrayElementType;
	}

	public void setTransferObjectBaseClass(String transferObjectBaseClass) {
		this.transferObjectBaseClass = transferObjectBaseClass;
	}

}