package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.DefaultAs3TypeMapper;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.meta.JavaProperty;

public class DefaultAs3PropertyMapper implements PropertyMapper<JavaProperty, As3Type>
{
	private TypeMapper<Class<?>, As3Type> typeMapper;
	
	public DefaultAs3PropertyMapper()
	{
		typeMapper = new DefaultAs3TypeMapper();
	}
	
	public boolean canMap( JavaProperty type )
	{
		return typeMapper.canMap( type.getType() );
	}

	public As3Type performMap( JavaProperty type )
	{
		return typeMapper.performMap( type.getType() );
	}
	
	public boolean canMapType( Class<?> type )
	{
		return typeMapper.canMap( type );
	}

	public As3Type performMapType( Class<?> type ) {
		return typeMapper.performMap( type );
	}
	
	public void setTypeMapper( TypeMapper<Class<?>, As3Type> typeMapper )
	{
		this.typeMapper = typeMapper;
	}
}