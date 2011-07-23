package net.histos.java2as.core.conf;

/**
 * Maps one package name to another package name.
 *
 * @author cliff.meyers
 */
public interface PackageMapper {

	boolean canMap(String packageName);

	String performMap(String packageName);

}
