package flexserverlib.java2as.as3.service;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.core.AbstractProducer;
import flexserverlib.java2as.core.conf.PackageMapper;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.meta.JavaService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceDelegateProducer extends AbstractProducer {

	//
	// Statics
	//

	private static final String SERVICE_IMPL_FTL = "service-impl.ftl";
	private static final String SERVICE_INTERFACE_FTL = "service-interface.ftl";

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
		this.writerResolver = new DefaultServiceDelegateWriterResolver(config.getServiceImplDir(), config.getServiceInterfaceDir());
	}

	//
	// Public Methods
	//

	@Override
	public void produce() {
		// filter
		List<Class<?>> matchingClasses = findMatchingClasses(config.getTypeMatchers(), classes);

		// build metadata
		List<JavaService> javaServices = buildMetadata(matchingClasses);

		// get mappers
		TypeMapper<As3Type> typeMapper = config.getTypeMapper();
		MethodMapper methodMapper = config.getMethodMapper();
		PackageMapper packageMapper = config.getPackageMapper();

		// do conversion
		ServiceDelegateMapper mapper = new ServiceDelegateMapper(methodMapper, typeMapper, packageMapper);
		List<As3ServiceDelegate> as3Services = mapper.performMappings(javaServices);

		// build template configuration
		Configuration fmConfig = new Configuration();
		fmConfig.setClassForTemplateLoading(ServiceDelegateProducer.class, "");

		try {

			Template serviceImplTemplate = fmConfig.getTemplate(SERVICE_IMPL_FTL);
			Template serviceInterfaceTemplate = fmConfig.getTemplate(SERVICE_INTERFACE_FTL);
			Map<String, Object> model = new HashMap<String, Object>();

			// generate files
			for (As3ServiceDelegate service : as3Services) {

				model.put("model", service);

				Writer writer1 = writerResolver.resolveServiceImpl(service);
				serviceImplTemplate.process(model, writer1);

				/*
								if (config.isGenerateInterfaces()) {
									Writer writer2 = writerResolver.resolveServiceInterface(service);
									serviceInterfaceTemplate.process(model, writer2);
								}
								*/

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