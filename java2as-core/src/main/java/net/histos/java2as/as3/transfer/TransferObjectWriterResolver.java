package net.histos.java2as.as3.transfer;

import java.io.Writer;

/**
 * Description
 *
 * @author cliff.meyers
 */
public interface TransferObjectWriterResolver {

	public Writer resolveBaseClass(As3TransferObject transferObject);

	public Writer resolveCustomClass(As3TransferObject transferObject);

	public boolean shouldCreateCustomClass(As3TransferObject transferObject);

}
