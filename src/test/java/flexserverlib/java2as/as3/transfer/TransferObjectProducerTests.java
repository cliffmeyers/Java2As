package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.test.util.TestClasses;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TransferObjectProducerTests {
	@Test
	public void testProduce() {
		TransferObjectConfiguration config = new TransferObjectConfiguration();
		List<Class<?>> classes = Arrays.asList(new Class<?>[]{TestClasses.getDefaultTestClass()});
		TransferObjectProducer producer = new TransferObjectProducer(config, classes);
		producer.produce();
	}
}
