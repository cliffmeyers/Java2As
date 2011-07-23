package net.histos.java2as.core.conf;

/**
 * Converts Java type into another data type.
 *
 * @author cliff.meyers
 */
public interface TypeMapper<OUT> {

	/**
	 * Decides whether the mapper should even try to map the type.
	 *
	 * @param type Java type to map.
	 * @return True if the mapper will map the property.
	 */
	public boolean canMapType(Class<?> type);

	/**
	 * Converts a Java type into another type.
	 *
	 * @param type Java type to map.
	 * @return The converted type.
	 */
	public OUT mapType(Class<?> type);

}
