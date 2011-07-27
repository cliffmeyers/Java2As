package net.histos.java2as.as3.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.histos.java2as.core.AbstractProducer;
import net.histos.java2as.core.meta.JavaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates ActionScript service delegates based off some Java services.
 *
 * @author cliff.meyers
 */
public class ServiceDelegateProducer extends AbstractProducer {

	private Logger _log = LoggerFactory.getLogger(getClass());

	//
	// Statics
	//

	private static final String SERVICE_IMPL_FTL = "service-impl.ftl";

	//
	// Fields
	//

	private ServiceDelegateConfiguration config;
	private List<Class<?>> classes;
	private ServiceDelegateWriterResolver writerResolver;

	//
	// Constructors
	//

	public ServiceDelegateProducer(ServiceDelegateConfiguration config, List<Class<?>> classes) {
		this.config = config;
		this.classes = classes;
		this.writerResolver = new DefaultServiceDelegateWriterResolver(config.getServiceImplDir());
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
		List<JavaService> javaServices = buildMetadata(matchingClasses);

		// do conversion
		ServiceDelegateMapper mapper = new ServiceDelegateMapper(config);
		List<As3ServiceDelegate> as3Services = mapper.performMappings(javaServices);

		// build template configuration
		Configuration fmConfig = new Configuration();
		fmConfig.setClassForTemplateLoading(ServiceDelegateProducer.class, "");

		try {

			Template serviceImplTemplate = fmConfig.getTemplate(SERVICE_IMPL_FTL);
			Map<String, Object> model = new HashMap<String, Object>();

			// generate files
			for (As3ServiceDelegate service : as3Services) {

				model.put("model", service);

				Writer writer1 = writerResolver.resolveServiceImpl(service);
				serviceImplTemplate.process(model, writer1);

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

	protected List<JavaService> buildMetadata(List<Class<?>> classes) {
		List<JavaService> delegates = new ArrayList<JavaService>();
		for (Class<?> clazz : classes)
			delegates.add(new JavaService(clazz));
		return delegates;
	}

	//
	// Getters and Setters
	//

	public void setWriterResolver(ServiceDelegateWriterResolver writerResolver) {
		this.writerResolver = writerResolver;
	}

}