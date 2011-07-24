package net.histos.java2as.as3.transfer.extensions.test;

import org.springframework.flex.core.io.AmfIgnoreField;

/**
 * @author cliff.meyers
 */
public class Place {

	//
	// Fields
	//

	private String location;

	private String zipCode;

	@AmfIgnoreField
	private byte[] photo;
	
}
