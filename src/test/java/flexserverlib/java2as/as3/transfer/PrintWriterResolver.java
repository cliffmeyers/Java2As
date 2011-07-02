package flexserverlib.java2as.as3.transfer;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Implemenation of TransferObjectWriterResolver that prints to System.out.
 * This impl will NOT write files to the filesystem. Used only for testing.
 *
 * @author cliff.meyers
 */
public class PrintWriterResolver implements TransferObjectWriterResolver {

    /**
     * @inheritDoc
     */
    public Writer resolveBaseClass(As3TransferObject transferObject) {
        return new PrintWriter(System.out);
    }

    /**
     * @inheritDoc
     */
    public Writer resolveCustomClass(As3TransferObject transferObject) {
        return new PrintWriter(System.out);
    }

    /**
     * @inheritDoc
     */
    public boolean shouldCreateCustomClass(As3TransferObject transferObject) {
        return true;
    }
    
}
