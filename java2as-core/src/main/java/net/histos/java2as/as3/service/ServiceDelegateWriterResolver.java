package net.histos.java2as.as3.service;

import java.io.Writer;

/**
 * Returns appropriate Writers for a ActionScript service delegate.
 *
 * @author cliff.meyers
 */
public interface ServiceDelegateWriterResolver {

	/**
	public Writer resolveServiceInterface(As3ServiceDelegate service);
	*/

	/**
	 * Returns a Writer to be used for generation of the service impl artifact.
	 *
	 * @param service ActionScript service delegate to write
	 * @return Writer
	 */
	public Writer resolveServiceImpl(As3ServiceDelegate service);

}
