package net.histos.java2as.as3.transfer;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Prints to standard out.
 *
 * @author cliff.meyers
 */
public class PrintManifestWriterResolver implements TransferObjectManifestWriterResolver {

	/**
	 * Returns a Writer that prints to standard out.
	 *
	 * @return Writer
	 */
	public Writer resolveWriter() {
		return new BufferedWriter(new PrintWriter(System.out));
	}

}
