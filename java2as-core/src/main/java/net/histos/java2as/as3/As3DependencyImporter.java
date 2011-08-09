package net.histos.java2as.as3;

import net.histos.java2as.core.meta.DependencyImporter;

/**
 * Determines whether an ActionScript dependency is required in the context of an ActionScript class.
 * Also makes the qualified and simple names for the dependency type available.
 *
 * @author cliff.meyers
 */
public interface As3DependencyImporter extends DependencyImporter<As3Dependency> {
	
}
