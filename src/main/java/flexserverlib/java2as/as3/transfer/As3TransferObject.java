package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3SimpleType;
import flexserverlib.java2as.core.meta.DependencyKind;
import flexserverlib.java2as.core.meta.JavaTransferObject;
import org.apache.commons.lang.StringUtils;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class As3TransferObject {

	private JavaTransferObject transferObject;
	private List<As3Property> properties;
	private List<As3Dependency> dependencies;

	private String qualifiedName;
	private String packageName;
	private String simpleName;

	public As3TransferObject(JavaTransferObject transferObject) {
		this.transferObject = transferObject;
		this.properties = new ArrayList<As3Property>();
		this.dependencies = new ArrayList<As3Dependency>();
		buildMetadata();
	}

	//
	// Public Methods
	//

	public String getImportsFragment() {

		List<String> imports = new ArrayList<String>();

		for (As3Dependency dependency : dependencies) {
			if (dependency.requiresImport() && !imports.contains(dependency.getQualifiedName())) {
				imports.add(dependency.getQualifiedName());
			}
		}

		Collections.sort(imports);

		StringBuilder fragment = new StringBuilder();

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

		return fragment.toString().trim();

	}

	/**
	 * Builds the string used to declare superclass and implemented interfaces.
	 *
	 * @return
	 */
	public String getPolymorphicsFragment() {

		String fragment = "";

		for (As3Dependency dependency : dependencies)
			if (dependency.getDependencyKind() == DependencyKind.SUPERCLASS && dependency.getDependencyType() != As3SimpleType.Object) {
				fragment += " extends " + dependency.getSimpleName() + "";
				break;
			}

		for (As3Dependency dependency : dependencies) {
			int count = 0;
			if (dependency.getDependencyKind() == DependencyKind.INTERFACE) {
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

		return fragment;

	}

	public void addProperty(As3Property property) {
		this.properties.add(property);
		addDependency(property.getDependency());
	}

	public void addDependency(As3Dependency dependency) {
		this.dependencies.add(dependency);
	}

	//
	// Protected Methods
	//

	protected void buildMetadata() {
		qualifiedName = transferObject.getName();
		packageName = transferObject.getPackageName();
		simpleName = transferObject.getSimpleName();
	}

	//
	// Getters and Setters
	//

	public String getPackageName() {
		return packageName;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public List<As3Property> getProperties() {
		return this.properties;
	}

	public List<As3Property> getAs3Childs() {
		return getProperties();
	}

}