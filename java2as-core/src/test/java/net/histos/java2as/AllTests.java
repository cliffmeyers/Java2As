package net.histos.java2as;

import net.histos.java2as.as3.DefaultAs3TypeMapperTests;
import net.histos.java2as.as3.transfer.As3PropertyTests;
import net.histos.java2as.as3.transfer.TransferObjectProducerTests;
import net.histos.java2as.core.meta.JavaMethodTests;
import net.histos.java2as.core.meta.JavaTransferObjectTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		As3PropertyTests.class,
		TransferObjectProducerTests.class,
		DefaultAs3TypeMapperTests.class,
		JavaMethodTests.class,
		JavaTransferObjectTests.class
})
public class AllTests {

}
