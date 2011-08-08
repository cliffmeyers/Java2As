package net.histos.java2as.as3.service;

import net.histos.java2as.as3.As3CustomType;
import net.histos.java2as.as3.As3SimpleType;
import net.histos.java2as.as3.As3Stereotype;
import net.histos.java2as.as3.DependencyResolver;
import net.histos.java2as.as3.transfer.As3Dependency;
import net.histos.java2as.core.conf.PackageMapper;
import net.histos.java2as.core.meta.DependencyKind;
import net.histos.java2as.core.meta.JavaService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ActionScript Service Delegate that communicates with a Java service
 *
 * @author cliff.meyers
 * @see net.histos.java2as.core.meta.JavaService
 */
public class As3ServiceDelegate implements As3Stereotype {

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

	private String qualifiedName = "";
	private String packageName = "";
	private String simpleName = "";
	private String importsFragment = "";
	private String polymorphicsFragment = "";
	private boolean implementation;

	//
	// Constructors
	//

	public As3ServiceDelegate(JavaService service) {
		this.service = service;
		this.methods = new ArrayList<As3Method>();
		this.dependencies = new ArrayList<As3Dependency>();
		this.implementation = service.isImplementation();
	}

	//
	// Public Methods
	//

	/**
	 * Adds a dependency to this transfer object
	 *
	 * @param dependency Dependency.
	 */
	public void addDependency(As3Dependency dependency) {
		// ensure there is only ever one superclass type registered
		if (dependency.getDependencyKind() == DependencyKind.SUPERCLASS) {
			As3Dependency existingSuperclass = null;
			// a new superclass type will replace the old one
			for (As3Dependency dep : dependencies) {
				if (dep.getDependencyKind() == DependencyKind.SUPERCLASS) {
					existingSuperclass = dep;
					break;
				}
			}
			if (existingSuperclass == null) {
				dependencies.add(dependency);
			} else if (dependency.getDependencyType() != As3SimpleType.Object) {
				dependencies.remove(existingSuperclass);
				dependencies.add(dependency);
			}
		} else {
			this.dependencies.add(dependency);
		}
	}

	public void addMethod(As3Method method) {
		this.methods.add(method);
		this.dependencies.addAll(method.getDependencies());
	}

	/**
	 * Updates metadata based on the supplied DependencyResolver.
	 *
	 * @param packageMapper      Maps the package for the service.
	 * @param dependencyResolver DependencyResolver to use for deciding if/how an item gets imported.
	 */
	public void buildMetadata(PackageMapper packageMapper, DependencyResolver dependencyResolver) {

		simpleName = service.getSimpleName();

		// modify the package name and qualified name if package mapper is applicable
		if (packageMapper.canMap(service.getPackageName())) {
			packageName = packageMapper.performMap(service.getPackageName());
			qualifiedName = packageName + "." + simpleName;
		} else {
			packageName = service.getPackageName();
			qualifiedName = service.getName();
		}

		buildImportsFragment(dependencyResolver);
		buildPolymorphicsFragment(dependencyResolver);

	}

	/**
	 * Specify configuration parameters to customize the output.
	 *
	 * @param superclassName Name of the superclass to use if no other is specified.
	 */
	public void applyConfiguration(String superclassName) {
		if (superclassName != null && superclassName.length() > 0) {
			// add a new default dependency on the specified superclass name
			addDependency(new As3Dependency(DependencyKind.SUPERCLASS, new As3CustomType(superclassName)));
		}
	}

	//
	// Protected Methods
	//

	/**
	 * Builds multi-line string fragment declaring all imported types.
	 *
	 * @param dependencyResolver DependencyResolver to use for deciding if/how an item gets imported.
	 */
	protected void buildImportsFragment(DependencyResolver dependencyResolver) {

		List<String> imports = new ArrayList<String>();
		imports.add(ASYNC_TOKEN);

		// loop over all deps and determine which to add
		for (As3Dependency dependency : dependencies) {
			if (dependencyResolver.shouldResolve(packageName, dependency)) {
				String resolvedName = dependencyResolver.resolveQualifiedName(dependency);
				// check for dups
				if (!imports.contains(resolvedName))
					imports.add(resolvedName);
			}
		}

		Collections.sort(imports);

		StringBuilder fragment = new StringBuilder();

		// build a nicely formatted string with empty lines between new top-level packages
		String prevTopLevelPackage = "";
		for (String importLine : imports) {
			String nextTopLevelPackage = importLine.substring(0, importLine.indexOf("."));
			if (!prevTopLevelPackage.equals(nextTopLevelPackage)) {
				fragment.append("\n");
				prevTopLevelPackage = nextTopLevelPackage;
			}
			fragment.append("\t");
			fragment.append(importLine);
			fragment.append(";\n");
		}

		this.importsFragment = fragment.toString().trim();

	}

	/**
	 * Builds the string used to declare superclass and implemented interfaces.
	 *
	 * @param dependencyResolver DependencyResolver to use for deciding if/how an item gets imported.
	 */
	protected void buildPolymorphicsFragment(DependencyResolver dependencyResolver) {

		String fragment = "";

		for (As3Dependency dependency : dependencies)
			if (dependency.getDependencyKind() == DependencyKind.SUPERCLASS && dependencyResolver.shouldResolve(packageName, dependency)) {
				fragment += " extends " + dependency.getSimpleName() + "";
				break;
			}

		for (As3Dependency dependency : dependencies) {
			int count = 0;
			if (dependency.getDependencyKind() == DependencyKind.INTERFACE && dependencyResolver.shouldResolve(packageName, dependency)) {
				count++;
				if (count == 1)
					fragment += " implements ";
				if (count > 1)
					fragment += ", ";
				fragment += dependency.getSimpleName();
			}
		}

		if (fragment != null && fragment.length() > 0) {
			fragment = fragment.trim();
			fragment = fragment.replaceAll("  ", " ");
		}

		this.polymorphicsFragment = fragment;

	}

	//
	// Getters and Setters
	//

	public List<As3Dependency> getDependencies() {
		return dependencies;
	}

	public String getPackageName() {
		return service.getPackageName();
	}

	public String getQualifiedName() {
		return service.getName();
	}

	public String getSimpleName() {
		return service.getSimpleName();
	}

	public String getImportsFragment() {
		return importsFragment;
	}

	public String getPolymorphicsFragment() {
		return polymorphicsFragment;
	}

	public List<As3Method> getMethods() {
		return methods;
	}

	public boolean isImplementation() {
		return implementation;
	}

}
