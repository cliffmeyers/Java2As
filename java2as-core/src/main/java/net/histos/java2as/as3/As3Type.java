package net.histos.java2as.as3;

/**
 * Represents an ActionScript type such as String, Number, etc.
 *
 * @author cliff.meyers
 */
public interface As3Type {

	/**
	 * Indicates whether the type is an ActionScript built-in or a custom AS3 class.
	 *
	 * @return True if the type is not a standard ActionScript type.
	 */
	public boolean isCustomType();

	/**
	 * Get the fully qualified name of the AS3 type, e.g. "mx.collections.ArrayCollection"
	 *
	 * @return Fully-qualified name of the type.
	 */
	public String getQualifiedName();

	/**
	 * Get the short ame of the AS3 type e.g. "ArrayCollection"
	 *
	 * @return Short name of the type.
	 */
	public String getSimpleName();

}
