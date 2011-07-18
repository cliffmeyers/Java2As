package net.histos.java2as.core.conf;

/**
 * Description
 *
 * @author cliff.meyers
 */
public interface PackageMapper {

	boolean canMap(String packageName);

	String performMap(String packageName);

}
