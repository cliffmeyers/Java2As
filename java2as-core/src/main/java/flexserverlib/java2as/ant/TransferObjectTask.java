package flexserverlib.java2as.ant;

import flexserverlib.java2as.as3.transfer.TransferObjectConfiguration;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;

public class TransferObjectTask extends Task {

	private TransferObjectConfiguration config;

	//
	// Public Methods
	//

	@Override
	public void execute() throws BuildException {

		config = new TransferObjectConfiguration();


		// TODO: read the config properties and build up objects


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
		// TODO: create type mapper via reflect
	}



}