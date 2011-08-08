package net.histos.java2as.as3;

import net.histos.java2as.as3.transfer.As3Dependency;
import net.histos.java2as.core.conf.PackageMapper;
import net.histos.java2as.core.conf.TypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generic base class for mapping AS3 classes
 *
 * @author cliff.meyers
 */
public class AbstractAs3Mapper<C extends AbstractAs3Configuration> {

	private Logger _log = LoggerFactory.getLogger(getClass());

	//
	// Fields
	//

	protected C config;
	protected DependencyResolver dependencyResolver;
	protected PackageMapper packageMapper;
	protected TypeMapper<As3Type> typeMapper;

	//
	// Constructors
	//

	protected AbstractAs3Mapper(C config) {
		this.config = config;
		this.dependencyResolver = new DefaultDependencyResolver();
		this.packageMapper = config.getPackageMapper();
		this.typeMapper = config.getTypeMapper();
	}

	/**
	 * Inspects classes and dependencies to warn about missing dependencies.
	 *
	 * @param stereotypeList
	 */
	protected void validateMetadata(List<As3Stereotype> stereotypeList) {

		List<String> recognizedTypeNames = new ArrayList<String>();
		List<String> unrecognizedTypeNames = new ArrayList<String>();
		List<As3Dependency> dependencies = new ArrayList<As3Dependency>();

		for (As3Stereotype stereotype : stereotypeList) {
			recognizedTypeNames.add(stereotype.getQualifiedName());
			for (As3Dependency dependency : stereotype.getDependencies()) {
				if (dependencyResolver.shouldResolve(stereotype.getPackageName(), dependency) && dependency.getDependencyType() instanceof As3CustomType) {
					if (!dependencies.contains(dependency))
						dependencies.add(dependency);
				}
			}
		}

		for (As3Dependency dependency : dependencies) {
			String typeName = dependency.getQualifiedName();
			if (!recognizedTypeNames.contains(typeName) && !unrecognizedTypeNames.contains(typeName))
				unrecognizedTypeNames.add(typeName);
		}

		Collections.sort(unrecognizedTypeNames);

		for (String typeName : unrecognizedTypeNames)
			_log.warn("Could not resolve dependency for " + typeName);
	}

}
