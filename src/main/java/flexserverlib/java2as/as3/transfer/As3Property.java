package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.core.meta.JavaProperty;
import flexserverlib.java2as.core.meta.Property;

import java.util.ArrayList;
import java.util.List;

public class As3Property implements Property<As3Type> {

	//
	// Fields
	//

	private JavaProperty property;
	private String name;
	private As3Type type;
	private As3Type arrayType;
	private List<As3Dependency> dependencies;

	//
	// Constructors
	//

	public As3Property(JavaProperty property, As3Type type) {
		this(property, type, null);
	}

	public As3Property(JavaProperty property, As3Type type, As3Type arrayType) {
		this.property = property;
		this.name = property.getName();
		this.type = type;
		this.arrayType = arrayType;
		this.dependencies = new ArrayList<As3Dependency>();
	}

	//
	// Public Methods
	//

	public void addDependency(As3Dependency dependency) {
		this.dependencies.add(dependency);
	}

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
		return arrayType != null;
	}

	public As3Type getArrayType() {
		return arrayType;
	}

	public List<As3Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<As3Dependency> dependencies) {
		this.dependencies = dependencies;
	}
}