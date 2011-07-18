package net.histos.java2as.as3.transfer;

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

	protected File resolveDirectoryForClass(File rootDirectory, As3TransferObject transferObject) {
		String rootDir = rootDirectory.getAbsolutePath();
		String relativePath = StringUtils.replace(transferObject.getPackageName(), ".", File.separator);
		String pathToDirectory = rootDir + File.separator + relativePath;
		return new File(pathToDirectory);
	}

	protected File resolveFileForClass(File rootDirectory, As3TransferObject transferObject, boolean baseClass) {
		String pathToDirectory = resolveDirectoryForClass(rootDirectory, transferObject).getAbsolutePath();
		String fullPath = pathToDirectory + File.separator + transferObject.getSimpleName() + (baseClass ? "Base" : "") + FILENAME_SUFFIX;
		return new File(fullPath);
	}

}
