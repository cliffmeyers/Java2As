package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.core.meta.JavaTransferObject;

import java.util.ArrayList;
import java.util.List;

public class As3TransferObject {

	private JavaTransferObject transferObject;
	private List<As3Property> properties;
	private List<As3Dependency> dependencies;

	public As3TransferObject(JavaTransferObject transferObject, List<As3Property> properties) {
		this.transferObject = transferObject;
		this.properties = properties;
		// TODO: build more metadata from properties to determine dependent types
		buildMetadata();
	}

	//
	// Public Methods
	//

	protected void buildMetadata() {
		String packageName = transferObject.getPackageName();
		String qualifiedName = transferObject.getName();
		String simpleName = transferObject.getSimpleName();

		dependencies = new ArrayList<As3Dependency>();
		for (As3Property prop : properties)
			dependencies.addAll(prop.getDependencies());
				
	}

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