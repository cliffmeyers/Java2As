package net.histos.java2as.as3.service;

import net.histos.java2as.as3.As3Type;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class As3MethodParameter {

	//
	// Fields
	//

	private String name;

	private As3Type type;

	//
	// Constructors
	//

	public As3MethodParameter(String name, As3Type type) {
		this.name = name;
		this.type = type;
	}

	//
	// Getters and Setters
	//

	public String getName() {
		return name;
	}

	public As3Type getType() {
		return type;
	}

}
