package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.core.conf.AbstractConfiguration;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.conf.TypeMatcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the different configuration properties available when generating Transfer Objects.
 *
 * @author cliff.meyers
 */
public class TransferObjectConfiguration extends AbstractConfiguration {

	//
	// Statics
	//

	private static final String N = System.getProperty("line.separator");

	//
	// Fields
	//

	/**
	 * TypeMapper to convert Java to AS3 types
	 */
	private TypeMapper<As3Type> typeMapper;

	/**
	 * PropertyMappers to convert Java properties to AS3 properties
	 */
	private List<PropertyMapper<As3Property>> propertyMappers;

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
	 * Include [ArrayElementType] metadata for Array and ArrayCollection types.
	 */
	// TODO: needs impl
	private boolean includeArrayElementType;

	/**
	 * Provide a base class which all Transfer Objects will extend.
	 */
	// TODO: needs impl
	private String transferObjectBaseClass;

	//
	// Constructors
	//

	public TransferObjectConfiguration() {
		super();
		propertyMappers = new ArrayList<PropertyMapper<As3Property>>();
		propertyMappers.add(new DefaultAs3PropertyMapper());
	}

	//
	// Public Methods
	//

	public void addPropertyMapper(PropertyMapper<As3Property> mapper) {
		propertyMappers.add(mapper);
	}

	public String[] getConfigurationSummary() {
		StringBuilder summary = new StringBuilder();
		summary.append("typeMapper=" + typeMapper.getClass().getName() + N);
		for (PropertyMapper propertyMapper : propertyMappers)
			summary.append("propertyMapper=" + propertyMapper.getClass().getName() + N);
		for (TypeMatcher matcher : matchers)
			summary.append("matcher=" + matcher.getClass().getName() + N);
		summary.append("customClassTemplate=" + (customClassTemplate != null ? customClassTemplate : "null; will use default") + N);
		summary.append("baseClassTemplate=" + (baseClassTemplate != null ? baseClassTemplate : "null; will use default") + N);
		return summary.toString().split(N);
	}

	//
	// Getters and Setters
	//

	public TypeMapper<As3Type> getTypeMapper() {
		return typeMapper;
	}

	public void setTypeMapper(TypeMapper<As3Type> typeMapper) {
		this.typeMapper = typeMapper;
	}

	public List<PropertyMapper<As3Property>> getPropertyMappers() {
		return propertyMappers;
	}

	public void setPropertyMappers(List<PropertyMapper<As3Property>> mappers) {
		this.propertyMappers = mappers;
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