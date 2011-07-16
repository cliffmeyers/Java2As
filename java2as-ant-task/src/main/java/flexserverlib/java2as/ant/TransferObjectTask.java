package flexserverlib.java2as.ant;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.transfer.TransferObjectConfiguration;
import flexserverlib.java2as.core.conf.PropertyMapper;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.conf.TypeMatcher;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TransferObjectTask extends Task {

	//
	// Fields
	//

	private TransferObjectConfiguration config;

	private String typeMapper;

	private List<AntPropertyMapper> propertyMappers = new ArrayList<AntPropertyMapper>();

	private List<AntTypeMatcher> typeMatchers = new ArrayList<AntTypeMatcher>();

	private List<FileSet> fileSets = new ArrayList<FileSet>();

	//
	// Public Methods
	//

	@Override
	public void init() throws BuildException {

		super.init();
		System.out.println("init");
		config = new TransferObjectConfiguration();

	}

	@Override
	public void execute() throws BuildException {
		// TODO: read the config properties and build up objects
		System.out.println("execute");
		loadConfiguratonClasses(config);
	}

	public void addConfigured(AntPropertyMapper propertyMapper) {
		propertyMappers.add(propertyMapper);
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

	protected void loadConfiguratonClasses(TransferObjectConfiguration config) {

		try {

			Class<TypeMapper<As3Type>> typeMapperClass = (Class<TypeMapper<As3Type>>) Class.forName(typeMapper);
			config.setTypeMapper(typeMapperClass.newInstance());

			for (AntPropertyMapper propertyMapper : propertyMappers) {
				Class<PropertyMapper> propertyMapperClass = (Class<PropertyMapper>) Class.forName(propertyMapper.getClassName());
				config.addPropertyMapper(propertyMapperClass.newInstance());
			}

			for (AntTypeMatcher matcher : typeMatchers) {
				Class<TypeMatcher> typeMatcherClass = (Class<TypeMatcher>) Class.forName(matcher.getClassName());
				config.addMatcher(typeMatcherClass.newInstance());
			}

			System.out.println("Configuration classes loaded successfully!");
			for (String line : config.getConfigurationSummary())
				System.out.println(line);

		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e.getMessage());
		}

	}

	//
	// Getters and Setters
	//

	public void setCustomClassDir(File value) {
		config.setCustomClassDir(value);
	}

	public void setBaseClassDir(File value) {
		config.setCustomClassDir(value);
	}

	public void setTypeMapper(String value) {
		typeMapper = value;
	}

}