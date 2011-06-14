package flexserverlib.java2as.core.meta;

public interface Property<T> {
	String getName();

	T getType();

	boolean isArrayType();

	T getArrayType();
}