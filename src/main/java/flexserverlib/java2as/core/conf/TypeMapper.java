package flexserverlib.java2as.core.conf;

/**
 * Generic mapper interface for mapping types.
 * @author cliff.meyers
 *
 * @param <IN> Inbound type.
 * @param <OUT> Outbound (mapped) type.
 */
public interface TypeMapper<IN, OUT>
{
	public boolean canMap( IN type );
	
	public OUT performMap( IN type );
}