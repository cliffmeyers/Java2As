package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.core.conf.AbstractConfiguration;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.conf.TypeMapper;

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
    // Fields
    //

	private TypeMapper<As3Type> typeMapper;

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
		propertyMappers = new ArrayList<PropertyMapper<As3Property>>();
		propertyMappers.add(new DefaultAs3PropertyMapper());
	}

	//
    // Public Methods
    //

	public void addPropertyMapper(PropertyMapper<As3Property> mapper) {
		propertyMappers.add(mapper);
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