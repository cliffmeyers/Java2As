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
	ArrayCollection("mx.collections.ArrayCollection"),
	ByteArray("flash.utils.ByteArray"),
	Xml("Xml"),
	Object("Object");

	private String qualifiedName;
	private String simpleName;

	private As3SimpleType(String qualifiedName) {
		this.qualifiedName = qualifiedName;
		this.simpleName = qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1 );
	}

	public boolean isCustomType() {
		return false;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public String getSimpleName() {
		return simpleName;
	}
	
}
