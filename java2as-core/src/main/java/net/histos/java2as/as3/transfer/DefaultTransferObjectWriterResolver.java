package net.histos.java2as.as3.transfer;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Default implementation of TransferObjectWriterResolver.
 * Writes classes to the file system based on their package name.
 * Different top-level directories can be specified for base and custom classes.
 *
 * @author cliff.meyers
 */
// TODO: i hate this name too
// TODO: generify?
public class DefaultTransferObjectWriterResolver implements TransferObjectWriterResolver {

	//
	// Statics
	//

	private static final String FILENAME_SUFFIX = ".as";

	//
	// Fields
	//

	private File customClassDirectory;
	private File baseClassDirectory;

	//
	// Constructor
	//

	/**
	 * Construct the resolver based off directories for custom and base classes.
	 *
	 * @param customClassDirectory Directory to generate custom classes (once).
	 * @param baseClassDirectory Directory to generate base classes (every time).
	 */
	public DefaultTransferObjectWriterResolver(File customClassDirectory, File baseClassDirectory) {
		this.customClassDirectory = customClassDirectory;
		this.baseClassDirectory = baseClassDirectory;
	}

	//
	// Public Methods
	//

	public Writer resolveBaseClass(As3TransferObject transferObject) {

		File directory = resolveDirectoryForClass(baseClassDirectory, transferObject);
		directory.mkdirs();
		File file = resolveFileForClass(baseClassDirectory, transferObject, true);

		try {
			return new FileWriter(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public Writer resolveCustomClass(As3TransferObject transferObject) {

		File directory = resolveDirectoryForClass(customClassDirectory, transferObject);
		directory.mkdirs();
		File file = resolveFileForClass(customClassDirectory, transferObject, false);

		try {
			return new FileWriter(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public boolean shouldCreateCustomClass(As3TransferObject transferObject) {
		File file = resolveFileForClass(customClassDirectory, transferObject, false);
		return !file.exists();
	}

	//
	// Protected Methods
	//

	/**
	 * Determines to which directory the file should be written.
	 *
	 * @param rootDirectory Root directory for all classes (either custom or base)
	 * @param transferObject ActionScript transfer object to generate
	 * @return File representing the directory where file contents should be written
	 */
	protected File resolveDirectoryForClass(File rootDirectory, As3TransferObject transferObject) {
		String rootDir = rootDirectory.getAbsolutePath();
		String relativePath = StringUtils.replace(transferObject.getPackageName(), ".", File.separator);
		String pathToDirectory = rootDir + File.separator + relativePath;
		return new File(pathToDirectory);
	}

	/**
	 * Determines to which file the file should be written.
	 *
	 * @param rootDirectory Root directory for all classes (either custom or base)
	 * @param transferObject ActionScript transfer object to generate
	 * @param baseClass
	 * @return File where file contents should be written
	 */
	protected File resolveFileForClass(File rootDirectory, As3TransferObject transferObject, boolean baseClass) {
		String pathToDirectory = resolveDirectoryForClass(rootDirectory, transferObject).getAbsolutePath();
		String fullPath = pathToDirectory + File.separator + transferObject.getSimpleName() + (baseClass ? "Base" : "") + FILENAME_SUFFIX;
		return new File(fullPath);
	}

}
