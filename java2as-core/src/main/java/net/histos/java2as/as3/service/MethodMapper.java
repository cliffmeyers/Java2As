package net.histos.java2as.as3.service;

import net.histos.java2as.as3.As3Type;
import net.histos.java2as.core.conf.TypeMapper;
import net.histos.java2as.core.meta.JavaMethod;

/**
 * Maps a Java service method into an ActionScript service method.
 *
 * @author cliff.meyers
 */
// TODO: generify?
public interface MethodMapper {

	/**
	 * Converts a Java method instance into ActionScript method.
	 *
	 * @param method Java method to map.
	 * @return The converted ActionScript method.
	 */
	As3Method mapMethod(JavaMethod method);

	/**
	 * Provides a TypeMapper to use for map the type contained by the property.
	 * 
	 * @param typeMapper
	 */
	void setTypeMapper(TypeMapper<As3Type> typeMapper);

}
