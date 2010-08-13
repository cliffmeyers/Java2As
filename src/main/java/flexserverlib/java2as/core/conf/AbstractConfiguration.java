package flexserverlib.java2as.core.conf;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConfiguration
{
	protected List<TypeMatcher> matchers;

	public AbstractConfiguration() {
		matchers = new ArrayList<TypeMatcher>();
	}

	public void addMatcher( TypeMatcher matcher ) {
		matchers.add( matcher );
	}

	/*
	 * Getters and Setters
	 */

	public List<TypeMatcher> getMatchers() {
		return matchers;
	}
	public void setMatchers( List<TypeMatcher> matchers ) {
		this.matchers = matchers;
	}
}