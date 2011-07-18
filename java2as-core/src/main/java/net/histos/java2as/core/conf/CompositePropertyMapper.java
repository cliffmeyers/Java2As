package net.histos.java2as.core.conf;

import net.histos.java2as.core.meta.JavaProperty;
import net.histos.java2as.core.meta.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cliff.meyers
 */
public class CompositePropertyMapper<OUT extends Property> implements PropertyMapper {

	private List<PropertyMapper<OUT>> mappers;
	private TypeMapper typeMapper;

	public CompositePropertyMapper() {
		mappers = new ArrayList<PropertyMapper<OUT>>();
	}

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

	public void addPropertyMapper(PropertyMapper<OUT> mapper) {
		this.mappers.add(mapper);
	}

	public void setTypeMapper(TypeMapper typeMapper) {
		this.typeMapper = typeMapper;
		// TODO: update children
	}

	public void addAll(List<PropertyMapper<OUT>> propertyMappers) {
		mappers.addAll(propertyMappers);
	}

	public boolean hasMappers() {
		return mappers.size() > 0;
	}
}
