package net.histos.java2as.core.meta;

/**
 * Models a property on a type (usually a data transfer object).
 *
 * @param <TYPE> The backing type of this property.
 */
public interface Property<TYPE> {

	/**
	 * Name of the property.
	 *
	 * @return name.
	 */
	String getName();

	/**
	 * Backing type of this property.
	 *
	 * @return backing tye.
	 */
	TYPE getType();

	/**
	 * Indicates whether the property is an array type, or a single type.
	 *
	 * @return True if an array type.
	 */
	boolean isArrayType();

	/**
	 * Indicates the type contained by this property if it is an array type.
	 *
	 * @return the type of the array elements.
	 */
	TYPE getArrayElementType();

}