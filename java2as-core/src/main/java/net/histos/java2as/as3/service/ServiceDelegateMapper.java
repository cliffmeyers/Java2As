package net.histos.java2as.as3.service;

import net.histos.java2as.as3.AbstractAs3Mapper;
import net.histos.java2as.as3.As3Dependency;
import net.histos.java2as.as3.As3Type;
import net.histos.java2as.core.meta.DependencyKind;
import net.histos.java2as.core.meta.JavaMethod;
import net.histos.java2as.core.meta.JavaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps a list of Java services into ActionScript service delegates.
 *
 * @author cliff.meyers
 */
public class ServiceDelegateMapper extends AbstractAs3Mapper<ServiceDelegateConfiguration> {

	//
	// Fields
	//

	private MethodMapper methodMapper;
	private Map<JavaService, As3ServiceDelegate> serviceMap;
	private List<As3ServiceDelegate> as3Delegates;

	//
	// Constructors
	//

	public ServiceDelegateMapper(ServiceDelegateConfiguration config) {
		super(config);
		this.methodMapper = config.getMethodMapper();
		this.serviceMap = new HashMap<JavaService, As3ServiceDelegate>();
	}

	//
	// Public Methods
	//

	/**
	 * Maps a list of Java services into ActionScript service delegates.
	 *
	 * @param javaServices List of Java services to map.
	 * @return List of ActionScript service delegates.
	 */
	public List<As3ServiceDelegate> performMappings(List<JavaService> javaServices) {

		this.as3Delegates = new ArrayList<As3ServiceDelegate>();

		for (JavaService javaService : javaServices) {
			As3ServiceDelegate as3Delegate = performMap(javaService);
			as3Delegates.add(as3Delegate);
			serviceMap.put(javaService, as3Delegate);
		}

		return as3Delegates;

	}

	//
	// Protected Methods
	//

	/**
	 * Performs mapping of a single object.
	 *
	 * @param javaService Java service to map.
	 * @return ActionScript service delegate
	 */
	protected As3ServiceDelegate performMap(JavaService javaService) {

		As3ServiceDelegate as3Delegate = new As3ServiceDelegate(javaService);
		as3Delegate.applyConfiguration(config.getServiceDelegateBaseClass());

		if (as3Delegate.isImplementation()) {
			// create dependencies for polymorphics
			As3Type superClassType = typeMapper.mapType(javaService.getSuperclass());
			As3Dependency superClassDependency = new As3Dependency(DependencyKind.SUPERCLASS, superClassType);
			as3Delegate.addDependency(superClassDependency);

			for (Class<?> interfaceClass : javaService.getInterfaces()) {
				As3Type interfaceType = typeMapper.mapType(interfaceClass);
				As3Dependency interfaceDependency = new As3Dependency(DependencyKind.INTERFACE, interfaceType);
				as3Delegate.addDependency(interfaceDependency);
			}
		}

		// map each method
		for (JavaMethod javaMethod : javaService.getMethods()) {
			As3Method as3Method = methodMapper.mapMethod(javaMethod);
			as3Delegate.addMethod(as3Method);
		}

		as3Delegate.buildMetadata(packageMapper, dependencyImporter);
		return as3Delegate;

	}

}
