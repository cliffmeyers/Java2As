package net.histos.java2as.as3.service;

import org.apache.commons.lang.StringUtils;

import java.io.*;

/**
 * Default implementation of ServiceDelegateWriterResolver.
 * Writes classes to the file system based on their package name.
 *
 * @author cliff.meyers
 */
public class DefaultServiceDelegateWriterResolver implements ServiceDelegateWriterResolver {

	//
	// Statics
	//

	private static final String INTERFACE_PREFIX = "I";
	private static final String IMPL_SUFFIX = "Impl";
	private static final String FILENAME_SUFFIX = ".as";

	//
	// Fields
	//

	private File serviceImplDirectory;
	private File serviceInterfaceDirectory;

	//
	// Constructor
	//

	public DefaultServiceDelegateWriterResolver(File serviceImplDirectory /*, File serviceInterfaceDirectory*/) {
		this.serviceImplDirectory = serviceImplDirectory;
		//this.serviceInterfaceDirectory = serviceInterfaceDirectory;
	}

	//
	// Public Methods
	//

	public Writer resolveServiceImpl(As3ServiceDelegate service) {

		File directory = resolveDirectoryForClass(serviceImplDirectory, service);
		directory.mkdirs();
		File file = resolveFileForClass(serviceImplDirectory, service);

		try {
			return new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/*
	public Writer resolveServiceInterface(As3ServiceDelegate service) {

		File directory = resolveDirectoryForClass(serviceInterfaceDirectory, service);
		directory.mkdirs();
		File file = resolveFileForClass(serviceInterfaceDirectory, service);

		try {
			return new FileWriter(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	*/

	//
	// Protected Methods
	//

	/**
	 * Determines to which directory the file should be written.
	 *
	 * @param rootDirectory Root directory for all classes
	 * @param service ActionScript service delegate to generate
	 * @return File representing the directory where file contents should be written
	 */
	protected File resolveDirectoryForClass(File rootDirectory, As3ServiceDelegate service) {
		String rootDir = rootDirectory.getAbsolutePath();
		String relativePath = StringUtils.replace(service.getPackageName(), ".", File.separator);
		String pathToDirectory = rootDir + File.separator + relativePath;
		return new File(pathToDirectory);
	}

	/**
	 * Determines to which file the file should be written.
	 *
	 * @param rootDirectory Root directory for all classes
	 * @param service ActionScript service delegate to generate
	 * @return File where file contents should be written
	 */
	protected File resolveFileForClass(File rootDirectory, As3ServiceDelegate service) {
		String pathToDirectory = resolveDirectoryForClass(rootDirectory, service).getAbsolutePath();
		String filename = service.getSimpleName() + FILENAME_SUFFIX;
		String fullPath = pathToDirectory + File.separator + filename;
		return new File(fullPath);
	}

}
