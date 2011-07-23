package net.histos.java2as.core.conf;

import net.histos.java2as.core.meta.JavaProperty;
import net.histos.java2as.core.meta.Property;

/**
 * Converts a JavaProperty instance into another typed Property.
 *
 * @param <OUT> The type of Property that is mapped.
 */
public interface PropertyMapper<OUT extends Property> {

	/**
	 * Decides whether the mapper should even try to map the property.
	 *
	 * @param prop Property to map.
	 * @return True if the mapper will map the property.
	 */
	public boolean canMapProperty(JavaProperty prop);

	/**
	 * Converts a JavaProperty instance into another typed Property.
	 *
	 * @param prop Property to map.
	 * @return The converted property.
	 */
	public OUT mapProperty(JavaProperty prop);

	/**
	 * Provides a TypeMapper to use for map the type contained by the property.
	 *
	 * @param typeMapper
	 */
	public void setTypeMapper(TypeMapper typeMapper);

}