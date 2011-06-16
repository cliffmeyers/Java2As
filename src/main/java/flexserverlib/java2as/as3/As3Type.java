package flexserverlib.java2as.as3;

import java.security.PrivateKey;

public enum As3Type {
	Integer,
	Number,
	String,
	Boolean,
	Date,
	Array,
	ArrayCollection,
	ByteArray,
	Xml,
	Object,
	RemoteClass;

	private String remoteClass;

	As3Type() {}

	private As3Type(String remoteClass) {
		this.remoteClass = remoteClass;
	}

	public String getRemoteClass() {
		return remoteClass;
	}
}