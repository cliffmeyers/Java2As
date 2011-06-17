package flexserverlib.java2as.core.conf;

import java.lang.annotation.Annotation;

/**
 * @author cliff.meyers
 */
public interface TypeMapper<OUT> {

	public boolean canMapType(Class<?> type);

	public OUT mapType(Class<?> type);

	public enum Context {

		PROPERTY,
		SUPERCLASS,
		INTERFACE,
		METHOD_PARAMETER

	}

}
