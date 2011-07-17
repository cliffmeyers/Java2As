package flexserverlib.java2as.as3.service;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.meta.JavaMethod;
import flexserverlib.java2as.core.meta.JavaMethodParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class DefaultMethodMapper implements MethodMapper {

	//
	// Fields
	//

	private TypeMapper<As3Type> typeMapper;

	//
	// Constructors
	//

	public DefaultMethodMapper(TypeMapper<As3Type> typeMapper) {
		this.typeMapper = typeMapper;
	}

	//
	// Publid Methods
	//

	public As3Method mapMethod(JavaMethod method) {

		String name = method.getName();
		As3Type returnType = typeMapper.mapType(method.getReturnType());
		List<As3MethodParameter> parameters = new ArrayList<As3MethodParameter>();

		for (JavaMethodParameter javaParameter : method.getParameters()) {
			String paramName = javaParameter.getName();
			As3Type type = typeMapper.mapType(javaParameter.getType());
			As3MethodParameter parameter = new As3MethodParameter(paramName, type);
			parameters.add(parameter);
		}

		return new As3Method(name, parameters, returnType);

	}

	public void setTypeMapper(TypeMapper<As3Type> typeMapper) {
		this.typeMapper = typeMapper;
	}

}
