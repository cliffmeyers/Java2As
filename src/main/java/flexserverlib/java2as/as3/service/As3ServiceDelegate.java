package flexserverlib.java2as.as3.service;

import flexserverlib.java2as.as3.transfer.As3Dependency;
import flexserverlib.java2as.core.meta.JavaService;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class As3ServiceDelegate {

    //
    // Statics
    //

    private static final String REMOTE_OBJECT = "mx.rpc.remoting.RemoteObject";
    private static final String ASYNC_TOKEN = "mx.rpc.AsyncToken";

    //
    // Fields
    //

    private JavaService service;
    private List<As3Method> methods;
    private List<As3Dependency> dependencies;

    //
    // Constructors
    //

    public As3ServiceDelegate(JavaService service) {
        this.service = service;
        this.methods = new ArrayList<As3Method>();
        this.dependencies = new ArrayList<As3Dependency>();
    }

    //
    // Public Methods
    //

    public void addMethod(As3Method method) {
        this.methods.add(method);
    }

    public void addDependency(As3Dependency dependency) {
        this.dependencies.add(dependency);
    }

    public String getPackageName() {
        return service.getPackageName();
    }

    public String getSimpleName() {
        return service.getSimpleName();
    }

    public String getImportsFragment() {
        return ASYNC_TOKEN + ";";
    }

    public String getPolymorphicsFragment() {
        return "";
    }

    //
    // Getters and Setters
    //

    public List<As3Method> getMethods() {
        return methods;
    }
    
}
