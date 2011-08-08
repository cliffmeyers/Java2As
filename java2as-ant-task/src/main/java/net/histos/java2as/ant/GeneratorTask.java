package net.histos.java2as.ant;

import net.histos.java2as.as3.AbstractAs3Configuration;
import net.histos.java2as.as3.As3Type;
import net.histos.java2as.core.conf.TypeMapper;
import net.histos.java2as.core.conf.TypeMatcher;
import net.histos.java2as.core.conf.matchers.AnnotationTypeMatcher;
import net.histos.java2as.core.conf.matchers.DefaultTypeMatcher;
import net.histos.java2as.core.conf.matchers.InterfaceTypeMatcher;
import net.histos.java2as.core.conf.matchers.SuperclassTypeMatcher;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cliff.meyers
 */
public class GeneratorTask<C extends AbstractAs3Configuration> extends Task {

	private Logger _log = LoggerFactory.getLogger(getClass());

	//
	// Fields
	//

	/**
	 * The configuration object to supply to the producer.
	 */
	protected C config;

	/**
	 * Locations of classes that are candidates for generation.
	 */
	protected List<FileSet> fileSets = new ArrayList<FileSet>();

	/**
	 * Custom TypeMapper class name to be used by java2as.
	 */
	protected String typeMapper;

	/**
	 * List of package names to be used in class matching.
	 */
	protected List<AntPackageMatcher> packageMatchers = new ArrayList<AntPackageMatcher>();

	/**
	 * List of TypeMatcher class names to be used by java2as.
	 */
	protected List<AntTypeMatcher> typeMatchers = new ArrayList<AntTypeMatcher>();

	/**
	 * Name of the superclass to match on using a default TypeMatcher.
	 *
	 * @see net.histos.java2as.core.conf.matchers.SuperclassTypeMatcher
	 */
	protected String superclassName;

	/**
	 * Name of the interface to match on using a default TypeMatcher.
	 *
	 * @see net.histos.java2as.core.conf.matchers.InterfaceTypeMatcher
	 */
	protected String interfaceName;

	/**
	 * Name of the annotation to match on using a default TypeMatcher.
	 *
	 * @see net.histos.java2as.core.conf.matchers.AnnotationTypeMatcher
	 */
	protected String annotationName;


	//
	// Public Methods
	//

	public void addConfigured(AntPackageMatcher matcher) {
		packageMatchers.add(matcher);
	}

	public void addConfigured(AntTypeMatcher matcher) {
		typeMatchers.add(matcher);
	}

	public void addFileset(FileSet fileset) {
		fileSets.add(fileset);
	}

	//
	// Protected Methods
	//

	protected void loadConfigurationClasses() {

		try {

			if (typeMapper != null) {
				Class<TypeMapper<As3Type>> typeMapperClass = (Class<TypeMapper<As3Type>>) Class.forName(typeMapper);
				config.setTypeMapper(typeMapperClass.newInstance());
			}

			loadTypeMatchers();

		} catch (ClassNotFoundException cnfe) {
			_log.error("Error occurred while loading configuration classes, could not load class: " + cnfe.getMessage());
			throw new BuildException("Error occurred while loading configuration classes", cnfe);
		} catch (Exception e) {
			_log.error("Error occurred while loading configuration classes: " + e.getMessage());
			throw new BuildException("Error occurred while loading configuration classes", e);
		}

	}

	protected void loadTypeMatchers() throws IllegalAccessException, InstantiationException, ClassNotFoundException {

		// use the custom type matchers, if specified
		// otherwise fall back to the simple properties and default type matchers
		if (typeMatchers != null && typeMatchers.size() > 0) {
			for (AntTypeMatcher matcher : typeMatchers) {
				Class<TypeMatcher> typeMatcherClass = (Class<TypeMatcher>) Class.forName(matcher.getClassName());
				config.addTypeMatcher(typeMatcherClass.newInstance());
			}
		} else {
			// support multiple default type matchers, even though this is an unlikely use case
			if (!StringUtils.isEmpty(superclassName))
				config.addTypeMatcher(new SuperclassTypeMatcher(superclassName));

			if (!StringUtils.isEmpty(interfaceName))
				config.addTypeMatcher(new InterfaceTypeMatcher(interfaceName));

			if (!StringUtils.isEmpty(annotationName))
				config.addTypeMatcher(new AnnotationTypeMatcher(annotationName));
		}

		// if all else fails, use the reasonable default
		if (config.getTypeMatchers().size() == 0)
			config.addTypeMatcher(new DefaultTypeMatcher());

	}

	//
	// Getters and Setters
	//

	public void setTypeMapper(String value) {
		typeMapper = value;
	}

	public void setSuperclassName(String superclassName) {
		this.superclassName = superclassName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public void setAnnotationName(String annotationName) {
		this.annotationName = annotationName;
	}

}
