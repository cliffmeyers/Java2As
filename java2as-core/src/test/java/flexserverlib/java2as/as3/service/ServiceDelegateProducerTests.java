package flexserverlib.java2as.as3.service;

import flexserverlib.java2as.as3.service.test.IUserService;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class ServiceDelegateProducerTests {

	@Test
	@Ignore
	public void testProduceFilesystem() {

		ServiceDelegateConfiguration config = new ServiceDelegateConfiguration();
		File tempDir = new File("c:" + File.separatorChar + "temp" + File.separator + "java2as" + File.separator + "service");
		File baseDir = new File(tempDir + File.separator + "impl");
		config.setServiceImplDir(baseDir);

		List<Class<?>> classes = Arrays.asList(
				new Class<?>[]{
						IUserService.class,
				}
		);

		ServiceDelegateProducer producer = new ServiceDelegateProducer(config, classes);
		producer.produce();

	}

	@Test
	public void testProduceSystemOut() {

		ServiceDelegateConfiguration config = new ServiceDelegateConfiguration();

		List<Class<?>> classes = Arrays.asList(
				new Class<?>[]{
						IUserService.class,
				}
		);

		ServiceDelegateProducer producer = new ServiceDelegateProducer(config, classes);
		producer.setWriterResolver(new PrintWriterResolver());
		producer.produce();

	}

}
