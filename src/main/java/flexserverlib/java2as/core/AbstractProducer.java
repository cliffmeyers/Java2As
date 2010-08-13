package flexserverlib.java2as.core;

import java.util.ArrayList;
import java.util.List;

import flexserverlib.java2as.core.conf.TypeMatcher;

public abstract class AbstractProducer
{
	public abstract void produce();
	
	protected List<Class<?>> findMatchingClasses( List<TypeMatcher> matchers, List<Class<?>> classes )
	{
		if ( matchers.size() == 0 ) return classes;
		List<Class<?>> matchingClasses = new ArrayList<Class<?>>();
		for ( Class<?> clazz : classes )
		{
			for ( TypeMatcher matcher : matchers )
			{
				if ( matcher.match( clazz ) && !matchingClasses.contains( clazz ) )
				{
					matchingClasses.add( clazz );
					break;
				}
			}
		}
		return matchingClasses;
	}
}