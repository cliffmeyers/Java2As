package flexserverlib.java2as.core.conf;

import flexserverlib.java2as.core.SimplePackageMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class that provides support for
 */
public abstract class AbstractConfiguration {

    //
    // Fields
    //

    protected List<TypeMatcher> matchers;
    protected List<SimplePackageMapper> packageMappers;

    //
    // Constructors
    //

    public AbstractConfiguration() {
        matchers = new ArrayList<TypeMatcher>();
        packageMappers = new ArrayList<SimplePackageMapper>();
    }

    //
    // Public Methods
    //

    public void addMatcher(TypeMatcher matcher) {
        matchers.add(matcher);
    }

    public void addPackageMapper(SimplePackageMapper mapper) {
        packageMappers.add(mapper);
    }

    //
    // Getters and Setters
    //

    public List<TypeMatcher> getMatchers() {
        return matchers;
    }

    public void setMatchers(List<TypeMatcher> matchers) {
        this.matchers = matchers;
    }

    public List<SimplePackageMapper> getPackageMappers() {
        return packageMappers;
    }

    public void setPackageMappers(List<SimplePackageMapper> packageMappers) {
        this.packageMappers = packageMappers;
    }

}