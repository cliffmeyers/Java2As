package flexserverlib.java2as.core.conf;

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
