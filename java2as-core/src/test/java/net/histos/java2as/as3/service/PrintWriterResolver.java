package net.histos.java2as.as3.service;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class PrintWriterResolver implements ServiceDelegateWriterResolver {

	public Writer resolveServiceInterface(As3ServiceDelegate service) {
		return new PrintWriter(System.out);
	}

	public Writer resolveServiceImpl(As3ServiceDelegate service) {
		return new PrintWriter(System.out);
	}

}
