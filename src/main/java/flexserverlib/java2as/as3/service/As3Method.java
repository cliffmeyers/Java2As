package flexserverlib.java2as.as3.service;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.transfer.As3Dependency;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class As3Method {

    //
    // Fields
    //
    
    private String name;

    private List<As3MethodParameter> parameters;

    private As3Type returnType;

    private List<As3Dependency> dependencies;

    //
    // Constructors
    //

    public As3Method(String name, List<As3MethodParameter> parameters, As3Type returnType) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.dependencies = new ArrayList<As3Dependency>();
    }

    //
    // Public Methods
    //

    public void addDependency(As3Dependency dependency) {
        this.dependencies.add(dependency);
    }

    public String getReturnTypeName() {
        return returnType.getQualifiedName();
    }

    //
    // Getters and Setters
    //

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<As3MethodParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<As3MethodParameter> parameters) {
        this.parameters = parameters;
    }

    public As3Type getReturnType() {
        return returnType;
    }

    public void setReturnType(As3Type returnType) {
        this.returnType = returnType;
    }
}
