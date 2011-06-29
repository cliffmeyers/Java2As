package flexserverlib.java2as.as3.service;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.transfer.As3Property;
import flexserverlib.java2as.as3.transfer.DefaultAs3PropertyMapper;
import flexserverlib.java2as.core.conf.AbstractConfiguration;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.conf.TypeMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the different configuration properties available when generating Service Delegates.
 *
 * @author cliff.meyers
 */
public class ServiceDelegateConfiguration extends AbstractConfiguration {

	//
    // Fields
    //

	private TypeMapper<As3Type> typeMapper;

	private List<PropertyMapper<As3Property>> propertyMappers;

	/**
	 * Provide a base class which all Service Delegates will extend.
	 */
	private String serviceDelegateBaseClass;

	//
    // Constructors
    //

	public ServiceDelegateConfiguration() {
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

	public String getServiceDelegateBaseClass() {
		return serviceDelegateBaseClass;
	}

	public void setServiceDelegateBaseClass(String serviceDelegateBaseClass) {
		this.serviceDelegateBaseClass = serviceDelegateBaseClass;
	}
}