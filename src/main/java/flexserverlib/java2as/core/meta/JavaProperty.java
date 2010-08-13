package flexserverlib.java2as.core.meta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Models a Transfer Object property, either from a getter method or field.
 * 
 * @author cliff.meyers
 */
public class JavaProperty implements Property<Class<?>>
{
	/*
	 * Fields
	 */
	
	private Method getter;
	private Field field;
	
	/*
	 * Constructors
	 */
	
	public JavaProperty( Method getter )
	{
		this.getter = getter;
	}
	
	public JavaProperty( Field field )
	{
		this.field = field;
	}
	
	/*
	 * Public Methods
	 */
	
	/**
	 * @return Property's name
	 */
	public String getName()
	{
		if ( this.getter != null )
			return getter.getName().substring(3,3).toLowerCase() + getter.getName().substring(4);
		else
			return field.getName().substring(0,0).toLowerCase() + getter.getName().substring(1);
	}
	
	/**
	 * @return The underlying Java type for this property
	 */
	public Class<?> getType()
	{
		if ( getter != null )
			return getter.getReturnType();
		else
			return field.getType();
	}
	
	public boolean isArrayType() {
		Class<?> javaType = getType();
		return javaType.isArray() || Collection.class.isAssignableFrom( javaType );
	}
	
	/**
	 * @return The type contained in the array type, or null if untyped.
	 */
	public Class<?> getArrayType()
	{
		Class<?> javaType = getType();
		if ( javaType.isArray() )
			return javaType.getComponentType();
		
		Type type = null;
		if ( getter != null )
			type = getter.getGenericReturnType();
		else if ( field != null )
			type = field.getGenericType();
		
		if ( type instanceof ParameterizedType )
			return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
		
		return null;
	}

	/**
	 * @return The annotations associated with the Field or getter Method for this property.
	 */
	public Annotation[] getAnnotations()
	{
		if ( getter != null )
			return getter.getAnnotations();
		else
			return field.getAnnotations();
	}
}