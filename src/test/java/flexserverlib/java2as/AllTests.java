package flexserverlib.java2as;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import flexserverlib.java2as.as3.DefaultAs3TypeMapperTests;
import flexserverlib.java2as.as3.transfer.As3PropertyTests;
import flexserverlib.java2as.as3.transfer.TransferObjectProducerTests;
import flexserverlib.java2as.core.meta.JavaPropertyTests;
import flexserverlib.java2as.core.meta.JavaTransferObjectTests;

@RunWith( Suite.class )
@Suite.SuiteClasses({
	As3PropertyTests.class,
	TransferObjectProducerTests.class,
	DefaultAs3TypeMapperTests.class,
	JavaPropertyTests.class,
	JavaTransferObjectTests.class
})
public class AllTests
{

}
