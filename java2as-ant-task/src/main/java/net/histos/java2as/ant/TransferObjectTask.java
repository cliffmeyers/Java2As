package net.histos.java2as.ant;

import net.histos.java2as.as3.transfer.TransferObjectConfiguration;
import net.histos.java2as.as3.transfer.TransferObjectProducer;
import net.histos.java2as.core.conf.PropertyMapper;
import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates transfer objects
 */
public class TransferObjectTask extends GeneratorTask<TransferObjectConfiguration> {

	private Logger _log = LoggerFactory.getLogger(getClass());

	//
	// Fields
	//

	// internal infrastructure

	/**
	 * The producer that creates the files.
	 */
	protected TransferObjectProducer producer;

	// parameters supplied by task end-users

	/**
	 * List of PropertyMapper class names to be used by java2as.
	 */
	private List<AntPropertyMapper> propertyMappers = new ArrayList<AntPropertyMapper>();

	/**
	 * Directory in which to generate "custom" classes (e.g. User)
	 */
	private File customClassDir;

	/**
	 * Directory in which to generate "base" classes (e.g. UserBase)
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
		loadConfiguratonClasses();

		_log.info("Configuration classes loaded successfully!");
		config.logConfiguration();

		List<Class<?>> candidateClasses = loadCandidateClasses();

		if (candidateClasses.size() == 0) {
			_log.warn("No candidate classes were found; produce will be skipped.");
			_log.warn("This is probably due to a configuration error in compiledClassesLocations");
			return;
		}

		_log.info("Candidate classes were found for generation: " + candidateClasses.size() + " total");
		producer = new TransferObjectProducer(config, candidateClasses);
		producer.produce();

	}

	public void addConfigured(AntPropertyMapper propertyMapper) {
		propertyMappers.add(propertyMapper);
	}

	//
	// Protected Methods
	//

	protected void loadConfiguratonClasses() {

		super.loadConfigurationClasses();

		try {

			if (propertyMappers.size() > 0) {
				config.removeAllPropertyMappers();
				for (AntPropertyMapper propertyMapper : propertyMappers) {
					Class<PropertyMapper> propertyMapperClass = (Class<PropertyMapper>) Class.forName(propertyMapper.getClassName());
					config.addPropertyMapper(propertyMapperClass.newInstance());
				}
			}

		} catch (Exception e) {
			_log.error("Error during loading config classes: " + e.getMessage());
			throw new BuildException(e.getMessage(), e);
		}

	}

	//
	// Getters and Setters
	//

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