package flexserverlib.java2as.core.conf;

/**
 * Generic mapper interface for mapping types.
 *
 * @param <IN>  Inbound type.
 * @param <OUT> Outbound (mapped) type.
 * @author cliff.meyers
 */
public interface TypeMapper<IN, OUT> {
	public boolean canMap(IN type);

	public OUT performMap(IN type);
}