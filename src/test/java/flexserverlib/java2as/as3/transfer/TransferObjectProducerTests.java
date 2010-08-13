package flexserverlib.java2as.as3.transfer;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import flexserverlib.java2as.test.util.TestClasses;

public class TransferObjectProducerTests
{
	@Test
	public void testProduce()
	{
		TransferObjectConfiguration config = new TransferObjectConfiguration();
		List<Class<?>> classes = Arrays.asList( new Class<?>[] { TestClasses.getDefaultTestClass() } );
		TransferObjectProducer producer = new TransferObjectProducer(config, classes);
		producer.produce();
	}
}
