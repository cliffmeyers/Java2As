package flexserverlib.java2as.core;

/**
 * Description
 *
 * @author cliff.meyers
 */
public interface PackageMapper {

    boolean canMap(String packageName);

    String performMap(String packageName);
    
}
