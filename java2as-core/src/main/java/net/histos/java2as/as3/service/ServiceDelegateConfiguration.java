package net.histos.java2as.as3.service;

import net.histos.java2as.as3.AbstractAs3Configuration;
import net.histos.java2as.as3.DefaultAs3TypeMapper;
import net.histos.java2as.core.conf.PropertyMapper;
import net.histos.java2as.core.conf.TypeMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Encapsulates the different configuration properties available when generating Service Delegates.
 *
 * @author cliff.meyers
 */
public class ServiceDelegateConfiguration extends AbstractAs3Configuration {

	private Logger _log = LoggerFactory.getLogger(getClass());

	//
	// Fields
	//

	/**
	 * Implementation to map methods
	 */
	private MethodMapper methodMapper;

	/**
	 * Directory in which to generate service implementations (e.g. UserService implements IUserService)
	 */
	private File serviceImplDir;

	/**
	 * Custom Freemarker template to use for generation of service impl classes.
	 */
	private File serviceImplTemplate;

	/**
	 * Provide a base class which all Service Delegates will extend.
	 */
	private String serviceDelegateBaseClass;

	//
	// Constructors
	//

	public ServiceDelegateConfiguration() {
		typeMapper = new DefaultAs3TypeMapper();
		methodMapper = new DefaultMethodMapper(typeMapper);
	}

	//
	// Public Methods
	//

	/**
	 * Prints configuration summary to INFO
	 */
	public void logConfiguration() {

		_log.info("typeMapper=" + typeMapper.getClass().getName());
		_log.info("methodMapper=" + methodMapper.getClass().getName());
		for (TypeMatcher matcher : typeMatchers)
			_log.info("typeMatcher=" + matcher.getClass().getName());

		_log.info("serviceImplDir=" + serviceImplDir);
		_log.info("serviceImplTemplate=" + serviceImplTemplate);
		_log.info("serviceDelegateBaseClass=" + serviceDelegateBaseClass);

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

	public File getServiceImplTemplate() {
		return serviceImplTemplate;
	}

	public void setServiceImplTemplate(File serviceImplTemplate) {
		this.serviceImplTemplate = serviceImplTemplate;
	}

	public String getServiceDelegateBaseClass() {
		return serviceDelegateBaseClass;
	}

	public void setServiceDelegateBaseClass(String serviceDelegateBaseClass) {
		this.serviceDelegateBaseClass = serviceDelegateBaseClass;
	}

}