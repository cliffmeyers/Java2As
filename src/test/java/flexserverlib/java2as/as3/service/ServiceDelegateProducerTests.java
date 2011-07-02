package flexserverlib.java2as.as3.service;

import flexserverlib.java2as.as3.service.test.IUserService;
import flexserverlib.java2as.as3.transfer.TransferObjectConfiguration;
import flexserverlib.java2as.as3.transfer.TransferObjectProducer;
import flexserverlib.java2as.as3.transfer.test.ArrayProperties;
import flexserverlib.java2as.as3.transfer.test.User;
import flexserverlib.java2as.as3.transfer.test.User2;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class ServiceDelegateProducerTests {
    @Test
	public void testProduce() {
		ServiceDelegateConfiguration config = new ServiceDelegateConfiguration();
		List<Class<?>> classes = Arrays.asList(
                new Class<?>[]{
                        IUserService.class,
                }
        );
		ServiceDelegateProducer producer = new ServiceDelegateProducer(config, classes);
		producer.produce();
	}
}
