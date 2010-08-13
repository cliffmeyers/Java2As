package flexserverlib.java2as.core.meta;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class JavaTransferObject implements TransferObject<JavaProperty>
{
	private Class<?> clazz;
	private List<JavaProperty> properties;
	
	public JavaTransferObject( Class<?> clazz )
	{
		this.clazz = clazz;
		this.properties = new ArrayList<JavaProperty>();
		
		// extract properties based off get/set methods
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo( clazz );
			PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
			for ( PropertyDescriptor prop : props )
				properties.add( new JavaProperty( prop.getReadMethod() ) );
		}
		catch (IntrospectionException e)
		{
			throw new RuntimeException( e );
		}
		
		// extract public fields
		for ( Field field : clazz.getDeclaredFields() )
		{
			int modifiers = field.getModifiers();
			if ( Modifier.isPublic( modifiers ) && !Modifier.isStatic( modifiers ) && !Modifier.isTransient( modifiers ) )
				properties.add( new JavaProperty( field ) );
		}
	}
	
	public String getPackageName()
	{
		return clazz.getPackage().getName();
	}
	
	public String getName()
	{
		return clazz.getName();
	}
	
	public String getSimpleName()
	{
		return clazz.getSimpleName();
	}

	public List<JavaProperty> getProperties()
	{
		return properties;
	}
}