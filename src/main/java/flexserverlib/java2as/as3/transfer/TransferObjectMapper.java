package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.meta.JavaProperty;
import flexserverlib.java2as.core.meta.JavaTransferObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cliff.meyers
 */
public class TransferObjectMapper {

	private List<PropertyMapper<As3Property>> propertyMappers;
	private Map<JavaTransferObject, As3TransferObject> transferObjectMap;
	private List<JavaTransferObject> javaTransferObjects;
	private List<As3TransferObject> as3TransferObjects;

	//
	// Constructors
	//

	public TransferObjectMapper(List<PropertyMapper<As3Property>> propertyMappers) {
		this.propertyMappers = propertyMappers;
		this.transferObjectMap = new HashMap<JavaTransferObject, As3TransferObject>();
	}

	//
	// Public Methods
	//

	public List<As3TransferObject> performMappings(List<JavaTransferObject> javaTransferObjects) {

		this.javaTransferObjects = javaTransferObjects;
		this.as3TransferObjects = new ArrayList<As3TransferObject>();

		List<As3TransferObject> as3TransferObjects = new ArrayList<As3TransferObject>();

		for (JavaTransferObject javaTransferObject : javaTransferObjects) {

			As3TransferObject as3TransferObject = performMap(javaTransferObject);
			as3TransferObjects.add(as3TransferObject);
			transferObjectMap.put(javaTransferObject, as3TransferObject);

		}

		return as3TransferObjects;

	}

	//
	// Protected Methods
	//

	protected As3TransferObject performMap(JavaTransferObject javaTransferObject) {

		List<As3Property> as3PropertyList = new ArrayList<As3Property>();

		// map each property
		for (JavaProperty javaProperty : javaTransferObject.getProperties()) {
			boolean wasMapped = false;
			for (PropertyMapper<As3Property> mapper : propertyMappers) {
				if (mapper.canMap(javaProperty)) {
					as3PropertyList.add(mapper.performMap(javaProperty));
					wasMapped = true;
					break;
				}
			}
			if (!wasMapped)
				System.out.println("No appropriate mapper found for " + javaTransferObject.getName() + "." + javaProperty.getName());
		}

		return new As3TransferObject(javaTransferObject, as3PropertyList);

	}

}
