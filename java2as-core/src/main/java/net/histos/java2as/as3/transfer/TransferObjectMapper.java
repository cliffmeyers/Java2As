package net.histos.java2as.as3.transfer;

import net.histos.java2as.as3.AbstractAs3Mapper;
import net.histos.java2as.as3.As3Stereotype;
import net.histos.java2as.as3.As3Type;
import net.histos.java2as.core.conf.PropertyMapper;
import net.histos.java2as.core.meta.DependencyKind;
import net.histos.java2as.core.meta.JavaProperty;
import net.histos.java2as.core.meta.JavaTransferObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps a list of Java transfer objects into ActionScript trnansfer objects.
 *
 * @author cliff.meyers
 */
public class TransferObjectMapper extends AbstractAs3Mapper<TransferObjectConfiguration> {

	private Logger _log = LoggerFactory.getLogger(getClass());

	//
	// Fields
	//

	private PropertyMapper<As3Property> propertyMapper;

	//
	// Constructors
	//

	public TransferObjectMapper(TransferObjectConfiguration config) {
		super(config);
		this.propertyMapper = config.getPropertyMapper();
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

		List<As3Stereotype> stereotypes = new ArrayList<As3Stereotype>();
		stereotypes.addAll(as3TransferObjects);
		validateMetadata(stereotypes);

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
