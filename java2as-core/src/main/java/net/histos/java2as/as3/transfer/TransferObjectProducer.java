package net.histos.java2as.as3.transfer;

import net.histos.java2as.core.AbstractProducer;
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
	private static final String TO_MANIFEST_FTL = "to-manifest.ftl";

	//
	// Fields
	//

	private TransferObjectConfiguration config;
	private List<Class<?>> classes;
	private TransferObjectWriterResolver writerResolver;
	private TransferObjectManifestWriterResolver manifestWriterResolver;

	//
	// Constructors
	//

	public TransferObjectProducer(TransferObjectConfiguration config, List<Class<?>> classes) {
		this.config = config;
		this.classes = classes;
		this.writerResolver = new DefaultTransferObjectWriterResolver(config.getCustomClassDir(), config.getBaseClassDir());
		this.manifestWriterResolver = new DefaultTransferObjectManifestWriterResolver(config.getBaseClassDir());
	}

	//
	// Public Methods
	//

	@Override
	public void produce() {

		// filter
		List<Class<?>> matchingClasses = findMatchingClasses(config.getTypeMatchers(), classes);

		_log.info("Matching transfer object classes: " + matchingClasses.size() + " total");

		if (matchingClasses.size() == 0) {
			_log.warn("No matching transfer objects were found. Aborting!");
			return;
		}

		// build metadata
		List<JavaTransferObject> javaTOs = buildMetadata(matchingClasses);

		// do conversion
		TransferObjectMapper mapper = new TransferObjectMapper(config);
		List<As3TransferObject> as3TransferObjects = mapper.performMappings(javaTOs);

		try {

			Map<String, Object> model = new HashMap<String, Object>();

			// build template configurations
			Configuration customClassConfig = new Configuration();
			Template customTemplate = configureTemplate(customClassConfig, TO_CUSTOM_FTL, config.getCustomClassTemplate());

			Configuration baseClassConfig = new Configuration();
			Template baseTemplate = configureTemplate(baseClassConfig, TO_BASE_FTL, config.getBaseClassTemplate());

			// generate files for to's
			for (As3TransferObject transferObject : as3TransferObjects) {

				model.put("model", transferObject);

				if (writerResolver.shouldCreateCustomClass(transferObject)) {
					Writer customWriter = writerResolver.resolveCustomClass(transferObject);
					customTemplate.process(model, customWriter);
				}

				Writer baseWriter = writerResolver.resolveBaseClass(transferObject);
				baseTemplate.process(model, baseWriter);

			}

			_log.info("Successfully generated transfer objects: " + matchingClasses.size() + " total");

			if (config.isGenerateManifest())
			{
				Configuration manifestConfig = new Configuration();
				manifestConfig.setClassForTemplateLoading(TransferObjectProducer.class, "");
				Template manifestTemplate = manifestConfig.getTemplate(TO_MANIFEST_FTL);

				model.clear();
				model.put("transferObjects", as3TransferObjects);
				Writer manifestWriter = manifestWriterResolver.resolveWriter();
				manifestTemplate.process(model, manifestWriter);

				_log.info("Successfully generated transfer object manifest");
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

	public void setManifestWriterResolver(TransferObjectManifestWriterResolver manifestWriterResolver) {
		this.manifestWriterResolver = manifestWriterResolver;
	}
}