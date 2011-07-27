package net.histos.java2as.as3.transfer;

import java.io.*;

/**
 * Returns a Writer to be used by the FreeMarker engine when generating the transfer object manifest.
 *
 * @author cliff.meyers
 */
// TODO: also hate this name :)
public class DefaultTransferObjectManifestWriterResolver implements TransferObjectManifestWriterResolver {

	//
	// Fields
	//

	/**
	 * Root directory where manifest should be written.
	 */
	protected File rootDirectory;

	//
	// Constructors
	//

	/**
	 * @param rootDirectory Directory where manifest should be written.
	 */
	public DefaultTransferObjectManifestWriterResolver(File rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	/**
	 * Returns the Writer that should be used for writing the manifest.
	 *
	 * @return Writer
	 */
	public Writer resolveWriter() {
		try {
			return new BufferedWriter(new FileWriter(rootDirectory));
		} catch (IOException e) {
			throw new RuntimeException("Exception while trying to get Writer for manifest", e);
		}
	}

}
