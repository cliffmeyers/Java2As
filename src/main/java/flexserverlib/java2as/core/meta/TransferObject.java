package flexserverlib.java2as.core.meta;

import java.util.List;

public interface TransferObject<T>
{
	public String getPackageName();
	
	public String getName();
	
	public String getSimpleName();

	public List<T> getProperties();
}