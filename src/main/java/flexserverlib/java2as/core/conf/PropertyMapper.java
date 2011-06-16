package flexserverlib.java2as.core.conf;

import flexserverlib.java2as.core.meta.JavaProperty;
import flexserverlib.java2as.core.meta.Property;

public interface PropertyMapper<OUT extends Property> {

	public boolean canMap(JavaProperty prop);

	public OUT performMap(JavaProperty prop);

}