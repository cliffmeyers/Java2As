package net.histos.java2as.core.conf.packages;

import net.histos.java2as.core.conf.PackageMapper;

/**
 * Converts a package name starting with "originPackage" to one starting with "targetPackage"
 *
 * @author cliff.meyers
 */
public class PackageMapperRule implements Comparable<PackageMapperRule>, PackageMapper {

	//
	// Fields
	//

	/**
	 * Java package to match.
	 */
	private String originPackage;

	/**
	 * Target package converting to.
	 */
	private String targetPackage;

	//
	// Constructors
	//

	public PackageMapperRule() {
		super();
	}

	public PackageMapperRule(String originPackage, String targetPackage) {
		this.originPackage = originPackage;
		this.targetPackage = targetPackage;
	}

	//
	// Public Methods
	//

	public boolean canMap(String packageName) {
		return packageName.startsWith(originPackage);
	}

	public String performMap(String packageName) {
		return targetPackage + packageName.substring(originPackage.length());
	}

	public int compareTo(PackageMapperRule o) {
		return originPackage.compareTo(o.originPackage);
	}

	@Override
	public String toString() {
		return "PackageMapperRule{" +
				"origin='" + originPackage + '\'' +
				", target='" + targetPackage + '\'' +
				'}';
	}
}





