package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.DefaultAs3TypeMapper;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.meta.DependencyKind;
import flexserverlib.java2as.core.meta.JavaProperty;

public class DefaultAs3PropertyMapper implements PropertyMapper<As3Property> {

	private TypeMapper<As3Type> typeMapper;

	public DefaultAs3PropertyMapper() {
		typeMapper = new DefaultAs3TypeMapper();
	}

	public boolean canMap(JavaProperty prop) {
		return true;
	}

	public As3Property performMap(JavaProperty prop) {

		As3Type type = typeMapper.performMap(prop.getType());
		As3Property property = null;

		if (!prop.isArrayType()) {
			property = new As3Property(prop, type);
		} else {
			As3Type arrayType = typeMapper.performMap(prop.getArrayType());
			property = new As3Property(prop, type, arrayType);
		}

		As3Dependency dependency = null;
		
		if (type != As3Type.RemoteClass)
			dependency = new As3Dependency(DependencyKind.PROPERTY, type);
		else
			dependency = new As3Dependency(DependencyKind.PROPERTY, type, new As3Class(prop.getType()));

		property.addDependency(dependency);

		return property;
		
	}

}