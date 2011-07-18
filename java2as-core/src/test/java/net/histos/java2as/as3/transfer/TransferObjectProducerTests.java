package net.histos.java2as.as3.transfer;

import net.histos.java2as.as3.transfer.test.ArrayProperties;
import net.histos.java2as.as3.transfer.test.User;
import net.histos.java2as.as3.transfer.test.User2;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TransferObjectProducerTests {

	@Ignore
	@Test
	public void testProduceFilesystem() {

		TransferObjectConfiguration config = new TransferObjectConfiguration();

		File tempDir = new File("c:" + File.separatorChar + "temp" + File.separator + "java2as" + File.separator + "dto");
		File baseDir = new File(tempDir + File.separator + "base");
		File customDir = new File(tempDir + File.separator + "custom");
		config.setBaseClassDir(baseDir);
		config.setCustomClassDir(customDir);

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

	@Test
	public void testProduceSystemOut() {

		TransferObjectConfiguration config = new TransferObjectConfiguration();
		List<Class<?>> classes = Arrays.asList(
				new Class<?>[]{
						ArrayProperties.class,
						User.class,
						User2.class
				}
		);
		TransferObjectProducer producer = new TransferObjectProducer(config, classes);
		producer.setWriterResolver(new PrintWriterResolver());
		producer.produce();

	}

}
