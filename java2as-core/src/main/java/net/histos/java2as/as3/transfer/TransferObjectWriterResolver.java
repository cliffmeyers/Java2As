package net.histos.java2as.as3.transfer;

import java.io.Writer;

/**
 * Returns appropriate Writers for a ActionScript transfer object.
 * This exists mainly to support testing of generation w/o creating files (write to System.out instead)
 *
 * @author cliff.meyers
 */
// TODO: i hate this name
// TODO: generify?
public interface TransferObjectWriterResolver {

	/**
	 * Returns a Writer to be used for generation of the base class artifact.
	 *
	 * @param transferObject ActionScript transfer object to write
	 * @return Writer
	 */
	public Writer resolveBaseClass(As3TransferObject transferObject);

	/**
	 * Returns a Writer to be used for generation of the custom class artifact (extends base class).
	 *
	 * @param transferObject ActionScript transfer object to write
	 * @return Writer
	 */
	public Writer resolveCustomClass(As3TransferObject transferObject);

	/**
	 * Indicates whether the custom class needs to be created.
	 * Custom classes usually are not overwritten.
	 *
	 * @param transferObject ActionScript transfer object to write
	 * @return True if the custom class should be created.
	 */
	public boolean shouldCreateCustomClass(As3TransferObject transferObject);

}
