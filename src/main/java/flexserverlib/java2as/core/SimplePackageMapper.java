package flexserverlib.java2as.core;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class SimplePackageMapper implements Comparable<SimplePackageMapper>, PackageMapper {

    //
    // Fields
    //

    /**
     * Java package to match.
     */
    private String originPackage;

    /**
     * AS3 package converting to.
     */
    private String targetPackage;

    //
    // Constructors
    //

    public SimplePackageMapper(String originPackage, String targetPackage) {
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

    public int compareTo(SimplePackageMapper o) {
        return originPackage.compareTo(o.originPackage);
    }

    @Override
    public String toString() {
        return "SimplePackageMapper{" +
                "origin='" + originPackage + '\'' +
                ", target='" + targetPackage + '\'' +
                '}';
    }
}





