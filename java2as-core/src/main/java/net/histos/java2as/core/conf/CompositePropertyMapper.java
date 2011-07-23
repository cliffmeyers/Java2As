package net.histos.java2as.core.conf;

import net.histos.java2as.core.meta.JavaProperty;
import net.histos.java2as.core.meta.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * PropertyMapper impl that allows multiple child PropertyMapper instances.
 * Mappers are searched in the order they've been added: first match wins.
 *
 * @author cliff.meyers
 */
public class CompositePropertyMapper<OUT extends Property> implements PropertyMapper {

	//
	// Fields
	//

	/**
	 * Internal list of PropertyMappers
	 */
	private List<PropertyMapper<OUT>> mappers;

	/**
	 * Property mapper
	 */
	private TypeMapper typeMapper;

	//
	// Constructors
	//

	public CompositePropertyMapper() {
		mappers = new ArrayList<PropertyMapper<OUT>>();
	}

	//
	// Public Methods
	//

	public boolean canMapProperty(JavaProperty prop) {
		for (PropertyMapper mapper : mappers)
			if (mapper.canMapProperty(prop))
				return true;
		return false;
	}

	public OUT mapProperty(JavaProperty prop) {
		for (PropertyMapper<OUT> mapper : mappers)
			if (mapper.canMapProperty(prop))
				return mapper.mapProperty(prop);
		return null;
	}

	public void setTypeMapper(TypeMapper typeMapper) {
		this.typeMapper = typeMapper;
		for (PropertyMapper mapper : mappers)
			mapper.setTypeMapper(typeMapper);
	}

	public void addPropertyMapper(PropertyMapper<OUT> mapper) {
		mappers.add(mapper);
	}

	public void removeAllMappers() {
		mappers.clear();
	}

	//
	// Getters and Setters
	//

	public List<PropertyMapper<OUT>> getMappers() {
		return mappers;
	}

	public void setMappers(List<PropertyMapper<OUT>> mappers) {
		this.mappers = mappers;
	}

}
