package flexserverlib.java2as.as3;

import flexserverlib.java2as.core.conf.TypeMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cliff.meyers
 */
public class CompositeAvmTypeMapper implements TypeMapper<As3Type> {

	private List<TypeMapper<As3Type>> mappers;

	public CompositeAvmTypeMapper() {
		mappers = new ArrayList<TypeMapper<As3Type>>();
	}

	public boolean canMapType(Class<?> type) {
		for (TypeMapper mapper : mappers)
			if (mapper.canMapType(type))
				return true;
		return false;
	}

	public As3Type mapType(Class<?> type) {
		for (TypeMapper<As3Type> mapper : mappers)
			if (mapper.canMapType(type))
				return mapper.mapType(type);
		return null;
	}

	public void addTypeMapper(TypeMapper<As3Type> mapper) {
		this.mappers.add(mapper);
	}


}
