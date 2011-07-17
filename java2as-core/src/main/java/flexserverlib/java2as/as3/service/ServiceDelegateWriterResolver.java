package flexserverlib.java2as.as3.service;

import flexserverlib.java2as.as3.transfer.As3TransferObject;

import java.io.Writer;

/**
 * Description
 *
 * @author cliff.meyers
 */
public interface ServiceDelegateWriterResolver {

	public Writer resolveServiceInterface(As3ServiceDelegate service);

	public Writer resolveServiceImpl(As3ServiceDelegate service);

}
