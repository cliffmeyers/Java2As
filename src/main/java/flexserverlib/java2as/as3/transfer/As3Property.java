package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.meta.JavaProperty;
import flexserverlib.java2as.core.meta.Property;

import java.util.ArrayList;
import java.util.List;

public class As3Property implements Property<As3Type> {

	private JavaProperty property;
	private List<PropertyMapper<JavaProperty, As3Type>> mappers;

	public As3Property(JavaProperty property, List<PropertyMapper<JavaProperty, As3Type>> mappers) {
		this.property = property;
		this.mappers = mappers;
	}

	public As3Property(JavaProperty property, PropertyMapper<JavaProperty, As3Type> mapper) {
		this.property = property;
		this.mappers = new ArrayList<PropertyMapper<JavaProperty, As3Type>>();
		this.mappers.add(mapper);
	}

	public String getName() {
		return property.getName();
	}

	public As3Type getType() {
		return performMap(property);
	}

	public As3Type getArrayType() {
		return performMap(property.getArrayType());
	}

	public boolean isArrayType() {
		As3Type type = getType();
		return type == As3Type.Array || type == As3Type.ArrayCollection;
	}

	protected As3Type performMap(JavaProperty prop) {
		for (PropertyMapper<JavaProperty, As3Type> mapper : mappers) {
			if (mapper.canMap(prop))
				return mapper.performMap(prop);
		}
		return null;
	}

	protected As3Type performMap(Class<?> type) {
		for (PropertyMapper<JavaProperty, As3Type> mapper : mappers) {
			if (mapper.canMapType(type))
				return mapper.performMapType(type);
		}
		return null;
	}
}