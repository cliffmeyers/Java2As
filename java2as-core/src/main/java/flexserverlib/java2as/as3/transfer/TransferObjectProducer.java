package flexserverlib.java2as.as3.transfer;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.DefaultAs3TypeMapper;
import flexserverlib.java2as.core.AbstractProducer;
import flexserverlib.java2as.core.CompositePackageMapper;
import flexserverlib.java2as.core.PackageMapper;
import flexserverlib.java2as.core.conf.CompositePropertyMapper;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.meta.JavaTransferObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferObjectProducer extends AbstractProducer {

	//
	// Statics
	//

	private static final String TO_BASE_FTL = "to-base.ftl";
	private static final String TO_CUSTOM_FTL = "to-custom.ftl";

	//
	// Fields
	//

	private TransferObjectConfiguration config;
	private List<Class<?>> classes;
	private TransferObjectWriterResolver writerResolver;

	//
	// Constructors
	//

	public TransferObjectProducer(TransferObjectConfiguration config, List<Class<?>> classes) {
		this.config = config;
		this.classes = classes;
		this.writerResolver = new DefaultTransferObjectWriterResolver(config.getCustomClassDir(), config.getBaseClassDir());
	}

	//
	// Public Methods
	//

	@Override
	public void produce() {

		// filter
		List<Class<?>> matchingClasses = findMatchingClasses(config.getMatchers(), classes);

		System.out.println("java2as found classes to generate: " + matchingClasses.size() + " total");

		// build metadata
		List<JavaTransferObject> javaTOs = buildMetadata(matchingClasses);

		// setup mappers
		TypeMapper<As3Type> typeMapper = config.getTypeMapper();
		if (typeMapper == null)
			typeMapper = new DefaultAs3TypeMapper();

		CompositePropertyMapper<As3Property> compositePropertyMapper = new CompositePropertyMapper<As3Property>();
		compositePropertyMapper.addAll(config.getPropertyMappers());
		if (!compositePropertyMapper.hasMappers())
			compositePropertyMapper.addPropertyMapper(new DefaultAs3PropertyMapper());

		CompositePackageMapper compositePackageMapper = new CompositePackageMapper();
		compositePackageMapper.addMappers(config.getPackageMappers());

		// do conversion
		TransferObjectMapper mapper = new TransferObjectMapper(compositePropertyMapper, typeMapper, compositePackageMapper);
		List<As3TransferObject> as3TransferObjects = mapper.performMappings(javaTOs);

		try {

			Map<String, Object> model = new HashMap<String, Object>();

			// build template configurations
			Configuration customClassConfig = new Configuration();
			Template customTemplate = configureTemplate(customClassConfig, TO_CUSTOM_FTL, config.getCustomClassTemplate());

			Configuration baseClassConfig = new Configuration();
			Template baseTemplate = configureTemplate(baseClassConfig, TO_BASE_FTL, config.getBaseClassTemplate());

			// generate files
			for (As3TransferObject transferObject : as3TransferObjects) {

				model.put("model", transferObject);

				if (writerResolver.shouldCreateCustomClass(transferObject)) {
					Writer customWriter = writerResolver.resolveCustomClass(transferObject);
					customTemplate.process(model, customWriter);
				}

				Writer baseWriter = writerResolver.resolveBaseClass(transferObject);
				baseTemplate.process(model, baseWriter);

			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}
	}

	//
	// Protected Methods
	//

	protected List<JavaTransferObject> buildMetadata(List<Class<?>> classes) {
		List<JavaTransferObject> transferObjects = new ArrayList<JavaTransferObject>();
		for (Class<?> clazz : classes)
			transferObjects.add(new JavaTransferObject(clazz));
		return transferObjects;
	}

	// TODO: move some logic to TransferObjectConfig to centralize handling of defaults?

	/**
	 * Configures a Freemarker template to use either the default or custom template file.
	 *
	 * @param configuration           Freemarker configuration
	 * @param defaultTemplateFilename Filename for the default template
	 * @param customTemplate          Custom template file; if null, it will be ignored
	 * @return configured Freemarker template
	 */
	protected Template configureTemplate(Configuration configuration, String defaultTemplateFilename, File customTemplate) {

		try {

			String customTemplateFilename = defaultTemplateFilename;

			if (customTemplate == null) {
				configuration.setClassForTemplateLoading(TransferObjectProducer.class, "");
			} else {
				File templateDirectory = customTemplate.getParentFile();
				customTemplateFilename = customTemplate.getName();
				configuration.setDirectoryForTemplateLoading(templateDirectory);
			}

			return configuration.getTemplate(customTemplateFilename);

		} catch (IOException ioe) {
			throw new RuntimeException("Error while loading Freemarker template", ioe);
		}

	}

	//
	// Getters and Setters
	//

	public void setWriterResolver(TransferObjectWriterResolver writerResolver) {
		this.writerResolver = writerResolver;
	}

}