package net.histos.java2as.core.meta;

public interface Property<TYPE> {

	String getName();

	TYPE getType();

	boolean isArrayType();

	TYPE getArrayElementType();

}