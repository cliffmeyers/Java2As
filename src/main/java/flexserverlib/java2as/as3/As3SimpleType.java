package flexserverlib.java2as.as3;

/**
 * @author cliff.meyers
 */
public enum As3SimpleType implements As3Type {

	Integer("Integer"),
	Number("Number"),
	String("String"),
	Boolean("Boolean"),
	Date("Date"),
	Array("Array"),
	ArrayCollection("ArrayCollection"),
	ByteArray("ByteArray"),
	Xml("Xml"),
	Object("Object");

	private String name;

	private As3SimpleType(String name) {
		this.name = name;
	}

	public boolean isBasicType() {
		return true;
	}

	public String getName() {
		return name;
	}
	
}
