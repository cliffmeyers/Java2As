package flexserverlib.java2as.core.conf;

import java.lang.annotation.Annotation;

/**
 * @author cliff.meyers
 */
public interface TypeMapper2<OUT> {

	public boolean canMap(Class<?> type, Annotation[] metadata, Context context);

	public OUT performMap(Class<?> type, Annotation[] metadata, Context context);

	public enum Context {

		PROPERTY,
		SUPERCLASS,
		INTERFACE,
		METHOD_PARAMETER

	}

}
