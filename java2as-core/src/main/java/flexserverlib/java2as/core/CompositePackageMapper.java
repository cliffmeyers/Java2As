package flexserverlib.java2as.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class CompositePackageMapper implements PackageMapper {

    private List<SimplePackageMapper> mappers;

    //
    // Constructors
    //

    public CompositePackageMapper() {
        mappers = new ArrayList<SimplePackageMapper>();
    }

    //
    // Public Methods
    //

    public boolean canMap(String packageName) {
        PackageMapper lastMatch = null;
        for (PackageMapper mapper : mappers) {
            if (mapper.canMap(packageName))
                lastMatch = mapper;
        }
        return lastMatch != null;
    }

    public String performMap(String packageName) {
        PackageMapper lastMatch = null;
        for (PackageMapper mapper : mappers) {
            if (mapper.canMap(packageName))
                lastMatch = mapper;
        }
        return lastMatch.performMap(packageName);
    }

    public void addMapper(SimplePackageMapper mapper) {
        mappers.add(mapper);
        // keep mappers sorted alphabetically so we can map just by looping through the list
        Collections.sort(mappers);
    }

    public void addMappers(List<SimplePackageMapper> mappers) {
        this.mappers.addAll(mappers);
        // keep mappers sorted alphabetically so we can map just by looping through the list
        Collections.sort(this.mappers);
    }

}
