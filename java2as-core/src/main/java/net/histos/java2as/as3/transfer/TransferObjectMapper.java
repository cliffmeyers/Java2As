package net.histos.java2as.as3.transfer;

import net.histos.java2as.as3.As3Type;
import net.histos.java2as.as3.DefaultDependencyResolver;
import net.histos.java2as.as3.DependencyResolver;
import net.histos.java2as.core.conf.PackageMapper;
import net.histos.java2as.core.conf.PropertyMapper;
import net.histos.java2as.core.conf.TypeMapper;
import net.histos.java2as.core.meta.DependencyKind;
import net.histos.java2as.core.meta.JavaProperty;
import net.histos.java2as.core.meta.JavaTransferObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps a list of Java transfer objects into ActionScript trnansfer objects.
 *
 * @author cliff.meyers
 */
public class TransferObjectMapper {

	//
	// Fields
	//

	private TransferObjectConfiguration config;
	private PropertyMapper<As3Property> propertyMapper;
	private TypeMapper<As3Type> typeMapper;
	private PackageMapper packageMapper;
	private DependencyResolver dependencyResolver;

	//
	// Constructors
	//

	public TransferObjectMapper(TransferObjectConfiguration config) {
		this.config = config;
		this.propertyMapper = config.getPropertyMapper();
		this.typeMapper = config.getTypeMapper();
		this.packageMapper = config.getPackageMapper();
		this.dependencyResolver = new DefaultDependencyResolver();
	}

	//
	// Public Methods
	//

	/**
	 * Maps a list of Java transfer objects into ActionScript trnansfer objects.
	 *
	 * @param javaTransferObjects List of Java transfer objects to map.
	 * @return List of VActionScript transfer objects.
	 */
	public List<As3TransferObject> performMappings(List<JavaTransferObject> javaTransferObjects) {

		List<As3TransferObject> as3TransferObjects = new ArrayList<As3TransferObject>();

		for (JavaTransferObject javaTransferObject : javaTransferObjects) {
			As3TransferObject as3TransferObject = performMap(javaTransferObject);
			as3TransferObjects.add(as3TransferObject);
		}

		return as3TransferObjects;

	}

	//
	// Protected Methods
	//

	/**
	 * Performs mapping of a single object.
	 *
	 * @param javaTransferObject Java transfer object to map.
	 * @return ActionScript transfer object
	 */
	protected As3TransferObject performMap(JavaTransferObject javaTransferObject) {

		As3TransferObject as3TransferObject = new As3TransferObject(javaTransferObject);
		as3TransferObject.applyConfiguration(config.getTransferObjectBaseClass());

		// create dependencies for polymorphics
		As3Type superClassType = typeMapper.mapType(javaTransferObject.getSuperclass());
		As3Dependency superClassDependency = new As3Dependency(DependencyKind.SUPERCLASS, superClassType);
		as3TransferObject.addDependency(superClassDependency);

		for (Class<?> interfaceClass : javaTransferObject.getInterfaces()) {
			As3Type interfaceType = typeMapper.mapType(interfaceClass);
			As3Dependency interfaceDependency = new As3Dependency(DependencyKind.INTERFACE, interfaceType);
			as3TransferObject.addDependency(interfaceDependency);
		}

		// map each property
		for (JavaProperty javaProperty : javaTransferObject.getProperties()) {
			As3Property as3Property = propertyMapper.mapProperty(javaProperty);
			as3TransferObject.addProperty(as3Property);
			as3Property.enableMetadata(config.getIncludeArrayElementType());
		}

		as3TransferObject.buildMetadata(packageMapper, dependencyResolver);
		return as3TransferObject;

	}

}
