package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.transfer.test.ArrayProperties;
import flexserverlib.java2as.as3.transfer.test.User;
import flexserverlib.java2as.as3.transfer.test.User2;
import flexserverlib.java2as.test.util.TestClasses;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TransferObjectProducerTests {
	@Test
	public void testProduce() {
		TransferObjectConfiguration config = new TransferObjectConfiguration();
		List<Class<?>> classes = Arrays.asList(
				new Class<?>[]{
						ArrayProperties.class,
						User.class,
						User2.class
				}
		);
		TransferObjectProducer producer = new TransferObjectProducer(config, classes);
		producer.produce();
	}
}
