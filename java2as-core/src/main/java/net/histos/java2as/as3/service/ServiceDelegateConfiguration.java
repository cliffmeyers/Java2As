package net.histos.java2as.as3.service;

import net.histos.java2as.as3.AbstractAs3Configuration;
import net.histos.java2as.as3.DefaultAs3TypeMapper;

import java.io.File;

/**
 * Encapsulates the different configuration properties available when generating Service Delegates.
 *
 * @author cliff.meyers
 */
public class ServiceDelegateConfiguration extends AbstractAs3Configuration {

	//
	// Fields
	//

	/**
	 * Implementation to map methods
	 */
	private MethodMapper methodMapper;

	/**
	 * Directory in which to generate service interfaces (e.g. IUserService)
	 */
	private File serviceInterfaceDir;

	/**
	 * Directory in which to generate service implementations (e.g. UserService implements IUserService)
	 */
	private File serviceImplDir;

	/**
	 * Provide a base class which all Service Delegates will extend.
	 */
	// TODO: needs impl
	private String serviceDelegateBaseClass;

	/**
	 * True if interfaces should be generated.
	 */
	// TODO: needs impl
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

	public MethodMapper getMethodMapper() {
		return methodMapper;
	}

	public void setMethodMapper(MethodMapper methodMapper) {
		this.methodMapper = methodMapper;
	}

	public File getServiceImplDir() {
		return serviceImplDir;
	}

	public void setServiceImplDir(File serviceImplDir) {
		this.serviceImplDir = serviceImplDir;
	}

	public File getServiceInterfaceDir() {
		return serviceInterfaceDir;
	}

	public void setServiceInterfaceDir(File serviceInterfaceDir) {
		this.serviceInterfaceDir = serviceInterfaceDir;
	}

	public String getServiceDelegateBaseClass() {
		return serviceDelegateBaseClass;
	}

	public void setServiceDelegateBaseClass(String serviceDelegateBaseClass) {
		this.serviceDelegateBaseClass = serviceDelegateBaseClass;
	}

	public boolean isGenerateInterfaces() {
		return generateInterfaces;
	}

	public void setGenerateInterfaces(boolean generateInterfaces) {
		this.generateInterfaces = generateInterfaces;
	}

}