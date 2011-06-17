package flexserverlib.java2as.as3;

/**
 * @author cliff.meyers
 */
public class As3CustomType implements As3Type {

	private Class<?> clazz;
	private String qualifiedName;
	private String simpleName;

	public As3CustomType(Class<?> clazz) {
		this.clazz = clazz;
		this.qualifiedName = clazz.getName();
		this.simpleName = clazz.getSimpleName();
	}

	public boolean isCustomType() {
		return true;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public String getSimpleName() {
		return simpleName;
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
