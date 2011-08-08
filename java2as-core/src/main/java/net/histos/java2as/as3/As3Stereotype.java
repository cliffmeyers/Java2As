package net.histos.java2as.as3;

import net.histos.java2as.as3.transfer.As3Dependency;

import java.util.List;

/**
 * Represents an AS3 type being generated
 *
 * @author cliff.meyers
 */
public interface As3Stereotype {

	String getPackageName();

	String getQualifiedName();

	List<As3Dependency> getDependencies();

}