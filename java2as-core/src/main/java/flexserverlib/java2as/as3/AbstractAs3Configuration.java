package flexserverlib.java2as.as3;

import flexserverlib.java2as.core.conf.AbstractConfiguration;
import flexserverlib.java2as.core.conf.TypeMapper;

/**
 * Description
 *
 * @author cliff.meyers
 */
public abstract class AbstractAs3Configuration extends AbstractConfiguration {

	//
	// Fields
	//

	/**
	 * TypeMapper
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
