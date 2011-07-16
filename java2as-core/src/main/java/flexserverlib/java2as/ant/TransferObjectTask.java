package flexserverlib.java2as.ant;

import flexserverlib.java2as.as3.transfer.TransferObjectConfiguration;
import flexserverlib.java2as.core.conf.TypeMatcher;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;

public class TransferObjectTask extends Task {

	private TransferObjectConfiguration config;

	@Override
	public void init() throws BuildException {

		super.init();

		System.out.println("init");

		config = new TransferObjectConfiguration();

	}

	//
	// Public Methods
	//

	@Override
	public void execute() throws BuildException {

		// TODO: read the config properties and build up objects

		System.out.println("execute");

	}

	public void addConfiguredMyTypeMatcher(MyTypeMatcher myTypeMatcher)
	{
		System.out.println(myTypeMatcher.className);
	}

	/*
	public MyTypeMatcher createMyTypeMatcher() {
		return new MyTypeMatcher();
	}
	*/

	/*
	public void addTypeMatcher(TypeMatcher typeMatcher) {

	}

	public void addConfiguredTypeMatcher(TypeMatcher typeMatcher) {

	}
	*/

	//
	// Getters and Setters
	//

	public void setCustomClassDir(File value) {
		config.setCustomClassDir(value);
	}

	public void setBaseClassDir(File value) {
		config.setCustomClassDir(value);
	}

}