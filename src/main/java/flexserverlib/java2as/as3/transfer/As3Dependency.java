package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.core.meta.DependencyKind;

/**
 * @author cliff.meyers
 */
public class As3Dependency {

	//
	// Fields
	//

	private As3Type dependencyType;
	private DependencyKind dependencyKind;

	//
	// Constructors
	//

	public As3Dependency(DependencyKind dependencyKind, As3Type dependencyType) {
		this.dependencyType = dependencyType;
		this.dependencyKind = dependencyKind;
	}

	//
	// Public Methods
	//

	@Override
	public String toString() {
		return "As3Dependency{" +
				dependencyKind + ":" +
				dependencyType +
				'}';
	}


	//
	// Getters and Setters
	//

	public As3Type getDependencyType() {
		return dependencyType;
	}

	public void setDependencyType(As3Type dependencyType) {
		this.dependencyType = dependencyType;
	}

	public DependencyKind getDependencyKind() {
		return dependencyKind;
	}

	public void setDependencyKind(DependencyKind dependencyKind) {
		this.dependencyKind = dependencyKind;
	}

}
