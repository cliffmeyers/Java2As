package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.core.meta.JavaTransferObject;

import java.util.ArrayList;
import java.util.List;

public class As3TransferObject {

	private JavaTransferObject transferObject;
	private List<As3Property> properties;
	private List<As3Dependency> dependencies;


	public As3TransferObject(JavaTransferObject transferObject) {
		this.transferObject = transferObject;
		this.properties = new ArrayList<As3Property>();
		this.dependencies = new ArrayList<As3Dependency>();
	}

	//
	// Public Methods
	//

	public void addProperty(As3Property property) {
		this.properties.add(property);
		addDependency(property.getDependency());
	}

	public void addDependency(As3Dependency dependency) {
		this.dependencies.add(dependency);
	}

	//
	// Protected Methods
	//

	protected void buildMetadata() {

		String qualifiedName = transferObject.getName();
		String packageName = transferObject.getPackageName();
		String simpleName = transferObject.getSimpleName();

	}

	//
	// Getters and Setters
	//

	public String getPackageName() {
		return transferObject.getPackageName();
	}

	public String getName() {
		return transferObject.getName();
	}

	public String getSimpleName() {
		return transferObject.getSimpleName();
	}

	public boolean isPolymorphic() {
		return hasSuperclass() || hasInterfaces();
	}

	public boolean hasSuperclass() {
		return transferObject.hasSuperclass();
	}

	// TODO: need to handle conversion here
	public String getSuperclass() {
		return transferObject.getSuperclass().getSimpleName();
	}

	public boolean hasInterfaces() {
		return transferObject.hasInterfaces();
	}

	public String getInterfaces() {
		List<String> interfaces = new ArrayList<String>();
		for (Class<?> clazz : transferObject.getInterfaces()) {
			interfaces.add(clazz.getSimpleName());
		}
		return interfaces.toString();
	}

	public List<As3Property> getProperties() {
		return this.properties;
	}

}