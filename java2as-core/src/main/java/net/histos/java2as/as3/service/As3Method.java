package net.histos.java2as.as3.service;

import net.histos.java2as.as3.As3Type;
import net.histos.java2as.as3.transfer.As3Dependency;
import net.histos.java2as.core.meta.DependencyKind;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class As3Method {

	//
	// Fields
	//

	private String name;

	private List<As3MethodParameter> parameters;

	private As3Type returnType;

	private List<As3Dependency> dependencies;

	//
	// Constructors
	//

	public As3Method(String name, List<As3MethodParameter> parameters, As3Type returnType) {

		this.name = name;
		this.parameters = parameters;
		this.returnType = returnType;
		this.dependencies = new ArrayList<As3Dependency>();

		for (As3MethodParameter param : parameters)
			this.dependencies.add(new As3Dependency(DependencyKind.PARAMETER, param.getType()));
		this.dependencies.add(new As3Dependency(DependencyKind.PARAMETER, returnType));

	}

	//
	// Public Methods
	//

	public String getReturnTypeName() {
		return returnType.getQualifiedName();
	}

	public String getParameterList() {
		List<String> params = new ArrayList<String>();
		for (As3MethodParameter param : parameters)
			params.add(param.getName() + ":" + param.getType().getSimpleName());
		return StringUtils.join(params, ",");
	}

	public String getParameterNameList() {
		List<String> paramNames = new ArrayList<String>();
		for (As3MethodParameter param : parameters)
			paramNames.add(param.getName());
		return StringUtils.join(paramNames, ",");
	}

	//
	// Getters and Setters
	//

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<As3MethodParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<As3MethodParameter> parameters) {
		this.parameters = parameters;
	}

	public As3Type getReturnType() {
		return returnType;
	}

	public void setReturnType(As3Type returnType) {
		this.returnType = returnType;
	}

	public List<As3Dependency> getDependencies() {
		return dependencies;
	}

}
