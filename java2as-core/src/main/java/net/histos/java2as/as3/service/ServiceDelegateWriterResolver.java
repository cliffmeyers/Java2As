package net.histos.java2as.as3.service;

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
