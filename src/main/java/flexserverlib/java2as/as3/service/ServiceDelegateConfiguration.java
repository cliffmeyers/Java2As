package flexserverlib.java2as.as3.service;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.DefaultAs3TypeMapper;
import flexserverlib.java2as.core.conf.AbstractConfiguration;
import flexserverlib.java2as.core.conf.TypeMapper;

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

	private MethodMapper methodMapper;

	/**
	 * Provide a base class which all Service Delegates will extend.
	 */
	private String serviceDelegateBaseClass;

    private boolean generateInterfaces;

	//
    // Constructors
    //

	public ServiceDelegateConfiguration() {
		typeMapper = new DefaultAs3TypeMapper();
        methodMapper = new DefaultMethodMapper(typeMapper);
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

    public MethodMapper getMethodMapper() {
        return methodMapper;
    }

    public void setMethodMapper(MethodMapper methodMapper) {
        this.methodMapper = methodMapper;
    }

    public String getServiceDelegateBaseClass() {
		return serviceDelegateBaseClass;
	}

	public void setServiceDelegateBaseClass(String serviceDelegateBaseClass) {
		this.serviceDelegateBaseClass = serviceDelegateBaseClass;
	}
}