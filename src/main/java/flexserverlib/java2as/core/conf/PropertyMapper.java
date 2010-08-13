package flexserverlib.java2as.core.conf;

public interface PropertyMapper<Property, OUT> extends TypeMapper<Property, OUT> {

	public boolean canMapType( Class<?> type );
	
	public OUT performMapType( Class<?> type );
	
	public void setTypeMapper( TypeMapper<Class<?>, OUT> typeMapper );
	
}