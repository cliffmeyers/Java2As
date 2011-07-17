package flexserverlib.java2as.as3.service;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Description
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

	public DefaultServiceDelegateWriterResolver(File serviceImplDirectory, File serviceInterfaceDirectory) {
		this.serviceImplDirectory = serviceImplDirectory;
		this.serviceInterfaceDirectory = serviceInterfaceDirectory;
	}

	//
	// Public Methods
	//

	public Writer resolveServiceImpl(As3ServiceDelegate service) {

		File directory = resolveDirectoryForClass(serviceImplDirectory, service);
		directory.mkdirs();
		File file = resolveFileForClass(serviceImplDirectory, service);

		try {
			return new FileWriter(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

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

	//
	// Protected Methods
	//

	protected File resolveDirectoryForClass(File rootDirectory, As3ServiceDelegate service) {
		String rootDir = rootDirectory.getAbsolutePath();
		String relativePath = StringUtils.replace(service.getPackageName(), ".", File.separator);
		String pathToDirectory = rootDir + File.separator + relativePath;
		return new File(pathToDirectory);
	}

	protected File resolveFileForClass(File rootDirectory, As3ServiceDelegate service) {
		String pathToDirectory = resolveDirectoryForClass(rootDirectory, service).getAbsolutePath();
		String filename = service.getSimpleName() + FILENAME_SUFFIX;
		String fullPath = pathToDirectory + File.separator + filename;
		return new File(fullPath);
	}

}
