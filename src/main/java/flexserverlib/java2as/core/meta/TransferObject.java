package flexserverlib.java2as.core.meta;

import java.util.List;

public interface TransferObject {

	public String getPackageName();

	public String getName();

	public String getSimpleName();

	public boolean hasSuperclass();

	public Class<?> getSuperclass();

	public boolean hasInterfaces();

	public List<?> getProperties();

}