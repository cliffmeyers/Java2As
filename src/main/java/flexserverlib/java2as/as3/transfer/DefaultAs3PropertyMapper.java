package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.DefaultAs3TypeMapper;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.meta.JavaProperty;

public class DefaultAs3PropertyMapper implements PropertyMapper<As3Property> {

	//
	// Fields
	//

	private TypeMapper<As3Type> typeMapper;

	//
	// Constructor
	//

	public DefaultAs3PropertyMapper() {
		typeMapper = new DefaultAs3TypeMapper();
	}

	//
	// Public Methods
	//

	public boolean canMapProperty(JavaProperty prop) {
		return true;
	}

	public As3Property mapProperty(JavaProperty prop) {

		As3Type type = typeMapper.mapType(prop.getType());
		As3Property property = null;

		if (!prop.isArrayType()) {
			property = new As3Property(prop, type);
		} else {
			As3Type arrayType = typeMapper.mapType(prop.getArrayElementType());
			property = new As3Property(prop, type, true, arrayType);
		}

		return property;
		
	}

	//
	// Getters and Setters
	//

	public void setTypeMapper(TypeMapper typeMapper) {
		this.typeMapper = typeMapper;
	}
}