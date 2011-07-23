package net.histos.java2as.as3;

/**
 * Defines the standard ActionScript types available
 *
 * @author cliff.meyers
 */
public enum As3SimpleType implements As3Type {

	//
	// Fields
	//

	Integer("Integer"),
	Number("Number"),
	String("String"),
	Boolean("Boolean"),
	Date("Date"),
	Array("Array"),
	ArrayCollection("mx.collections.ArrayCollection"),
	ByteArray("flash.utils.ByteArray"),
	Xml("Xml"),
	Object("Object"),
	Void("void");

	/**
	 * Qualified name of this type.
	 */
	private String qualifiedName;

	/**
	 * Short name of this type.
	 */
	private String simpleName;

	//
	// Constructors
	//

	/**
	 * Constructs the standard ActionScript type from its qualified name.
	 *
	 * @param qualifiedName Fully-qualified name of the type.
	 */
	private As3SimpleType(String qualifiedName) {
		this.qualifiedName = qualifiedName;
		this.simpleName = qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1);
	}

	//
	// Getters and Setters
	//

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
