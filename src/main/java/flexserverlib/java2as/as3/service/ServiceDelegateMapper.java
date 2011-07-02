package flexserverlib.java2as.as3.service;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.transfer.As3Dependency;
import flexserverlib.java2as.as3.transfer.As3Property;
import flexserverlib.java2as.as3.transfer.As3TransferObject;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.meta.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cliff.meyers
 */
public class ServiceDelegateMapper {

	private MethodMapper methodMapper;
	private TypeMapper<As3Type> typeMapper;

	private Map<JavaService, As3ServiceDelegate> serviceMap;
	private List<As3ServiceDelegate> as3Delegates;

	//
	// Constructors
	//

	public ServiceDelegateMapper(MethodMapper methodMapper, TypeMapper<As3Type> typeMapper) {
		this.methodMapper = methodMapper;
		this.typeMapper = typeMapper;
		this.serviceMap = new HashMap<JavaService, As3ServiceDelegate>();
	}

	//
	// Public Methods
	//

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

	protected As3ServiceDelegate performMap(JavaService javaService) {

		As3ServiceDelegate as3Delegate = new As3ServiceDelegate(javaService);

		// create dependencies for polymorphics
		as3Delegate.addDependency(new As3Dependency(DependencyKind.SUPERCLASS, typeMapper.mapType(javaService.getSuperclass())));

		for (Class<?> interfaceClass : javaService.getInterfaces())
			as3Delegate.addDependency(new As3Dependency(DependencyKind.INTERFACE, typeMapper.mapType(interfaceClass)));

		// map each method
		for (JavaMethod javaMethod : javaService.getMethods()) {
			As3Method as3Method = methodMapper.mapMethod(javaMethod);
			as3Delegate.addMethod(as3Method);
		}

		return as3Delegate;

	}

}
