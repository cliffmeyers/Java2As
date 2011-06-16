package flexserverlib.java2as.core.conf;

/**
 * Generic mapper interface for mapping types.
 *
 * @param <OUT> Outbound (mapped) type.
 * @author cliff.meyers
 */
public interface TypeMapper<OUT> {

	public boolean canMap(Class<?> type);

	public OUT performMap(Class<?> type);

}