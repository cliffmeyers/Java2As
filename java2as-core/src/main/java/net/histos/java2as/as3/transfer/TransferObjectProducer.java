package net.histos.java2as.as3.transfer;

import net.histos.java2as.as3.As3Type;
import net.histos.java2as.core.AbstractProducer;
import net.histos.java2as.core.conf.CompositePropertyMapper;
import net.histos.java2as.core.conf.PackageMapper;
import net.histos.java2as.core.conf.PropertyMapper;
import net.histos.java2as.core.conf.TypeMapper;
import net.histos.java2as.core.meta.JavaTransferObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates ActionScript transfer objects based off some Java transfer objects.
 * 
 * @author cliff.meyers
 */
public class TransferObjectProducer extends AbstractProducer {

	private Logger _log = LoggerFactory.getLogger(getClass());

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
		List<Class<?>> matchingClasses = findMatchingClasses(config.getTypeMatchers(), classes);

		_log.info("java2as found classes to generate: " + matchingClasses.size() + " total");

		// build metadata
		List<JavaTransferObject> javaTOs = buildMetadata(matchingClasses);

		// setup mappers
		TypeMapper<As3Type> typeMapper = config.getTypeMapper();
		PropertyMapper<As3Property> propertyMapper = config.getPropertyMapper();
		PackageMapper packageMapper = config.getPackageMapper();

		// do conversion
		TransferObjectMapper mapper = new TransferObjectMapper(propertyMapper, typeMapper, packageMapper);
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

	/**
	 * Builds transfer object metadata for supplied transfer object classes.
	 *
	 * @param classes Java classes
	 * @return Java transfer objects
	 */
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