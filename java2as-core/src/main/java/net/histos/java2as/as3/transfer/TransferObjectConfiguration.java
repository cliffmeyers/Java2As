package net.histos.java2as.as3.transfer;

import net.histos.java2as.as3.AbstractAs3Configuration;
import net.histos.java2as.core.conf.CompositePropertyMapper;
import net.histos.java2as.core.conf.PropertyMapper;
import net.histos.java2as.core.conf.TypeMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Encapsulates the different configuration properties available when generating Transfer Objects.
 *
 * @author cliff.meyers
 */
public class TransferObjectConfiguration extends AbstractAs3Configuration {

	private Logger _log = LoggerFactory.getLogger(getClass());

	//
	// Statics
	//

	private static final String N = System.getProperty("line.separator");

	//
	// Fields
	//

	/**
	 * Default PropertyMapper that can hold other user-defined PropertyMappers.
	 */
	private CompositePropertyMapper<As3Property> propertyMapper;

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
	 */
	private File customClassTemplate;

	/**
	 * Freemarker template to use for generation of base classes.
	 */
	private File baseClassTemplate;

	/**
	 * Generate the "manifest" files which links to all transfer objects.
	 * Useful if you want to include the manifest in your main application to force compile all transfer objects.
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
	// Constructors
	//

	public TransferObjectConfiguration() {
		super();
		propertyMapper = new CompositePropertyMapper<As3Property>();
		propertyMapper.addPropertyMapper(new DefaultAs3PropertyMapper());
	}

	//
	// Public Methods
	//

	/**
	 * Adds a new PropertyMapper to the end of the list (lowest priority)
	 *
	 * @param mapper PropertyMapper to add
	 */
	public void addPropertyMapper(PropertyMapper<As3Property> mapper) {
		propertyMapper.addPropertyMapper(mapper);
	}

	/**
	 * Remove all registered PropertyMappers.
	 */
	public void removeAllPropertyMappers() {
		propertyMapper.removeAllMappers();
	}

	/**
	 * Prints configuration summary to INFO
	 */
	public void logConfiguration() {

		_log.info("typeMapper=" + typeMapper.getClass().getName());
		for (PropertyMapper childPropertyMapper : propertyMapper.getMappers())
			_log.info("propertyMapper=" + childPropertyMapper.getClass().getName());
		_log.info("packageMapper=" + packageMapper.getClass().getName());
		for (TypeMatcher matcher : typeMatchers)
			_log.info("typeMatcher=" + matcher.getClass().getName());

		_log.info("customClassDir=" + customClassDir);
		_log.info("baseClassDir=" + baseClassDir);
		_log.info("customClassTemplate=" + (customClassTemplate != null ? customClassTemplate : "null; will use default"));
		_log.info("baseClassTemplate=" + (baseClassTemplate != null ? baseClassTemplate : "null; will use default"));

	}

	//
	// Getters and Setters
	//

	public CompositePropertyMapper<As3Property> getPropertyMapper() {
		return propertyMapper;
	}

	public void setPropertyMapper(CompositePropertyMapper<As3Property> propertyMapper) {
		this.propertyMapper = propertyMapper;
	}

	public File getBaseClassDir() {
		return baseClassDir;
	}

	public void setBaseClassDir(File baseClassDir) {
		this.baseClassDir = baseClassDir;
	}

	public File getCustomClassDir() {
		return customClassDir;
	}

	public void setCustomClassDir(File customClassDir) {
		this.customClassDir = customClassDir;
	}

	public File getBaseClassTemplate() {
		return baseClassTemplate;
	}

	public void setBaseClassTemplate(File baseClassTemplate) {
		this.baseClassTemplate = baseClassTemplate;
	}

	public File getCustomClassTemplate() {
		return customClassTemplate;
	}

	public void setCustomClassTemplate(File customClassTemplate) {
		this.customClassTemplate = customClassTemplate;
	}

	public boolean isGenerateManifest() {
		return generateManifest;
	}

	public void setGenerateManifest(boolean generateManifest) {
		this.generateManifest = generateManifest;
	}

	public boolean getIncludeArrayElementType() {
		return includeArrayElementType;
	}

	public void setIncludeArrayElementType(boolean includeArrayElementType) {
		this.includeArrayElementType = includeArrayElementType;
	}

	public String getTransferObjectBaseClass() {
		return transferObjectBaseClass;
	}

	public void setTransferObjectBaseClass(String transferObjectBaseClass) {
		this.transferObjectBaseClass = transferObjectBaseClass;
	}
	
}