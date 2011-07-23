package net.histos.java2as.core.meta;

import java.util.List;

/**
 * Models a transfer object which consists of child properties.
 */
public interface TransferObject {

	/**
	 * Package name of the transfer object.
	 *
	 * @return Package name.
	 */
	public String getPackageName();

	/**
	 * Fully-qualified name of the type.
	 *
	 * @return Fully-qualified name.
	 */
	public String getName();

	/**
	 * Simple name of the type (no package).
	 *
	 * @return Simple name.
	 */
	public String getSimpleName();

	/**
	 * Indicates whether the class has a specific super class or extends from the language default.
	 *
	 * @return True if the superclass is not a default.
	 */
	public boolean hasSuperclass();

	/**
	 * Transfer object's super class.
	 *
	 * @return Super class.
	 */
	public Class<?> getSuperclass();

	/**
	 * Indicates whether the transfer object implements any interfaces.
	 *
	 * @return True if the class implements any interfaces.
	 */
	public boolean hasInterfaces();

	/**
	 * List of properties contained by the transfer object.
	 *
	 * @return List of properties.
	 */
	public List<?> getProperties();

}