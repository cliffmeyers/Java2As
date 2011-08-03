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

	/**
	 * Name of the superclass to match on using a default TypeMatcher.
	 *
	 * @see net.histos.java2as.core.conf.matchers.SuperclassTypeMatcher
	 */
	protected String superclassName;

	/**
	 * Name of the interface to match on using a default TypeMatcher.
	 *
	 * @see net.histos.java2as.core.conf.matchers.InterfaceTypeMatcher
	 */
	protected String interfaceName;

	/**
	 * Name of the annotation to match on using a default TypeMatcher.
	 *
	 * @see net.histos.java2as.core.conf.matchers.AnnotationTypeMatcher
	 */
	protected String annotationName;

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

	public String getSuperclassName() {
		return superclassName;
	}

	public void setSuperclassName(String superclassName) {
		this.superclassName = superclassName;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getAnnotationName() {
		return annotationName;
	}

	public void setAnnotationName(String annotationName) {
		this.annotationName = annotationName;
	}
	
}
