package flexserverlib.java2as.as3;

/**
 * @author cliff.meyers
 */
public class As3CustomType implements As3Type {

	private Class<?> clazz;
	private String name;

	public As3CustomType(Class<?> clazz) {
		this.clazz = clazz;
		this.name = clazz.getName();
	}

	public boolean isBasicType() {
		return false;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		As3CustomType that = (As3CustomType) o;
		if (!clazz.equals(that.clazz)) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return clazz.hashCode();
	}
	
}
