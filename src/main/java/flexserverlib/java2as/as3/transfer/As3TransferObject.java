package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.meta.JavaProperty;
import flexserverlib.java2as.core.meta.JavaTransferObject;
import flexserverlib.java2as.core.meta.TransferObject;

import java.util.ArrayList;
import java.util.List;

public class As3TransferObject implements TransferObject<As3Property> {
	private JavaTransferObject transferObject;
	private List<As3Property> properties;

	public As3TransferObject(JavaTransferObject transferObject, List<PropertyMapper<JavaProperty, As3Type>> mappers) {
		this.transferObject = transferObject;
		this.properties = new ArrayList<As3Property>();
		for (JavaProperty property : transferObject.getProperties())
			this.properties.add(new As3Property(property, mappers));
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

	public List<As3Property> getProperties() {
		return this.properties;
	}
}