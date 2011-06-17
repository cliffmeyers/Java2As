package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.core.meta.DependencyKind;
import flexserverlib.java2as.core.meta.JavaProperty;
import flexserverlib.java2as.core.meta.Property;

public class As3Property implements Property<As3Type> {

	//
	// Fields
	//

	private JavaProperty property;
	private String name;
	private As3Type type;
	private boolean arrayType;
	private As3Type arrayElementType;
	private As3Dependency dependency;

	//
	// Constructors
	//

	public As3Property(JavaProperty property, As3Type type) {
		this(property, type, false, null);
	}

	public As3Property(JavaProperty property, As3Type type, boolean arrayType, As3Type arrayElementType) {
		this.property = property;
		this.name = property.getName();
		this.type = type;
		this.arrayType = arrayType;
		this.arrayElementType = arrayElementType;
		this.dependency = new As3Dependency(DependencyKind.PROPERTY, type);
	}

	//
	// Public Methods
	//

	@Override
	public String toString() {
		return name + ":" + type;
	}

	//
	// Getters and Setters
	//

	public String getName() {
		return property.getName();
	}

	public As3Type getType() {
		return type;
	}

	public boolean isArrayType() {
		return arrayType;
	}

	public As3Type getArrayElementType() {
		return arrayElementType;
	}

	public As3Dependency getDependency() {
		return dependency;
	}
	
}