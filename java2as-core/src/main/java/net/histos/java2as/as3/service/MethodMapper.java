package net.histos.java2as.as3.service;

import net.histos.java2as.as3.As3Type;
import net.histos.java2as.core.conf.TypeMapper;
import net.histos.java2as.core.meta.JavaMethod;

/**
 * Description
 *
 * @author cliff.meyers
 */
public interface MethodMapper {

	As3Method mapMethod(JavaMethod method);

	void setTypeMapper(TypeMapper<As3Type> typeMapper);

}
