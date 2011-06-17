package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.DefaultAs3TypeMapper;
import flexserverlib.java2as.core.AbstractProducer;
import flexserverlib.java2as.core.conf.CompositePropertyMapper;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.meta.JavaTransferObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferObjectProducer extends AbstractProducer {

	private TransferObjectConfiguration config;
	private List<Class<?>> classes;

	public TransferObjectProducer(TransferObjectConfiguration config, List<Class<?>> classes) {
		this.config = config;
		this.classes = classes;
	}

	@Override
	public void produce() {
		// filter
		List<Class<?>> matchingClasses = findMatchingClasses(config.getMatchers(), classes);

		// build metadata
		List<JavaTransferObject> javaTOs = buildMetadata(matchingClasses);

		// build template configuration
		Configuration fmConfig = new Configuration();
		fmConfig.setClassForTemplateLoading(TransferObjectProducer.class, "");

		try {
			Template template = fmConfig.getTemplate("to-base.ftl");
			Map<String, Object> model = new HashMap<String, Object>();

			// setup mappers
			TypeMapper<As3Type> typeMapper = config.getTypeMapper();
			if (typeMapper == null)
				typeMapper = new DefaultAs3TypeMapper();


			CompositePropertyMapper<As3Property> compositePropertyMapper = new CompositePropertyMapper<As3Property>();
			compositePropertyMapper.addAll(config.getPropertyMappers());
			if (!compositePropertyMapper.hasMappers())
				compositePropertyMapper.addPropertyMapper(new DefaultAs3PropertyMapper());

			// do conversion
			TransferObjectMapper mapper = new TransferObjectMapper(compositePropertyMapper, typeMapper);
			List<As3TransferObject> as3TransferObjects = mapper.performMappings(javaTOs);

			// generate files
			for (As3TransferObject transferObject : as3TransferObjects) {
				model.put("model", transferObject);
				Writer writer = new PrintWriter(System.out);
				template.process(model, writer);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}
	}

	private List<JavaTransferObject> buildMetadata(List<Class<?>> classes) {
		List<JavaTransferObject> transferObjects = new ArrayList<JavaTransferObject>();
		for (Class<?> clazz : classes)
			transferObjects.add(new JavaTransferObject(clazz));
		return transferObjects;
	}
}