package net.histos.java2as.ant;

import net.histos.java2as.as3.As3Type;
import net.histos.java2as.as3.service.MethodMapper;
import net.histos.java2as.as3.service.ServiceDelegateConfiguration;
import net.histos.java2as.as3.service.ServiceDelegateProducer;
import net.histos.java2as.core.conf.TypeMapper;
import net.histos.java2as.core.conf.TypeMatcher;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cliff.meyers
 */
public class ServiceDelegateTask extends GeneratorTask<ServiceDelegateConfiguration> {

	private Logger _log = LoggerFactory.getLogger(getClass());

	//
	// Fields
	//

	// internal infrastructure

	/**
	 * The producer that creates the files.
	 */
	protected ServiceDelegateProducer producer;

	// parameters supplied by task end-users

	/**
	 * Custom MethodMapper class name to be used by java2as.
	 */
	private String methodMapper;

	/**
	 * Directory in which to generate service implementations (e.g. UserService implements IUserService)
	 */
	private File serviceImplDir;

	/**
	 * Custom Freemarker template to use for generation of service impl classes.
	 */
	private File serviceImplTemplate;

	/**
	 * Provide a base class which all Services will extend.
	 */
	private String serviceDelegatetBaseClass;

	//
	// Public Methods
	//

	@Override
	public void init() throws BuildException {
		super.init();
		config = new ServiceDelegateConfiguration();
	}

	@Override
	public void execute() throws BuildException {

		config.setServiceImplDir(serviceImplDir);
		config.setServiceImplTemplate(serviceImplTemplate);
		config.setServiceDelegateBaseClass(serviceDelegatetBaseClass);
		loadConfiguratonClasses();

		_log.info("Configuration classes loaded successfully!");
		config.logConfiguration();

		executeProduce();

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

	protected void executeProduce() {

		final String SLASH = File.separator;
		final String EXT = "class";
		final String DOT_EXT = "." + EXT;
		final String PACKAGE_DELIM = ".";

		List<String> candidateClassNames = new LinkedList<String>();

		for (FileSet fileSet : fileSets) {
			for (String filePath : fileSet.getDirectoryScanner().getIncludedFiles()) {
				if (filePath.endsWith(DOT_EXT)) {
					String className = filePath.substring(0, filePath.length() - DOT_EXT.length());
					className = StringUtils.replace(className, SLASH, PACKAGE_DELIM);
					candidateClassNames.add(className);
				}
			}
		}

		// now let's load some classes!
		List<Class<?>> candidateClasses = new ArrayList<Class<?>>(500);

		for (String name : candidateClassNames) {
			try {
				Class<?> clazz = Class.forName(name);
				candidateClasses.add(clazz);
			} catch (ClassNotFoundException e) {
				System.out.println("Could not load candidate class: " + name + "; will be ignored");
			}
		}

		if (candidateClasses.size() == 0) {
			System.out.println("No candidate classes were found; produce will be skipped.");
			System.out.println("This is probably due to a configuration error in compiledClassesLocations");
			return;
		}

		_log.info("Candidate classes were found for generation: " + candidateClasses.size() + " total");
		producer = new ServiceDelegateProducer(config, candidateClasses);
		producer.produce();

	}

	protected void loadConfiguratonClasses() {

		super.loadConfigurationClasses();

		try {

			if (methodMapper != null) {
				Class<MethodMapper> methodMapperClass = (Class<MethodMapper>) Class.forName(methodMapper);
				config.setMethodMapper(methodMapperClass.newInstance());
			}

		} catch (Exception e) {
			_log.error("Error during loading config classes: " + e.getMessage());
			throw new BuildException(e.getMessage(), e);
		}

	}

	//
	// Getters and Setters
	//

	public void setMethodMapper(String methodMapper) {
		this.methodMapper = methodMapper;
	}

	public void setServiceImplDir(File serviceImplDir) {
		this.serviceImplDir = serviceImplDir;
	}

	public void setServiceImplTemplate(File serviceImplTemplate) {
		this.serviceImplTemplate = serviceImplTemplate;
	}

	public void setServiceDelegatetBaseClass(String serviceDelegatetBaseClass) {
		this.serviceDelegatetBaseClass = serviceDelegatetBaseClass;
	}

}
