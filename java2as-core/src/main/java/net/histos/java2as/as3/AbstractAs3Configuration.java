package net.histos.java2as.as3;

import net.histos.java2as.core.conf.AbstractConfiguration;
import net.histos.java2as.core.conf.TypeMapper;

/**
 * Convenience configuration class for ActionScript generation which includes a default AS3 TypeMapper.
 *
 * @author cliff.meyers
 */
public abstract class AbstractAs3Configuration extends AbstractConfiguration {

	//
	// Fields
	//

	/**
	 * TypeMapper to use
	 */
	protected TypeMapper<As3Type> typeMapper;

	//
	// Constructors
	//

	public AbstractAs3Configuration() {
		super();
		typeMapper = new DefaultAs3TypeMapper();
	}

	//
	// Getters and Setters
	//

	public TypeMapper<As3Type> getTypeMapper() {
		return typeMapper;
	}

	public void setTypeMapper(TypeMapper<As3Type> typeMapper) {
		this.typeMapper = typeMapper;
	}

}
