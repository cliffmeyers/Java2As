package flexserverlib.java2as.as3.service;

import flexserverlib.java2as.as3.As3Type;
import flexserverlib.java2as.as3.DefaultAs3TypeMapper;
import flexserverlib.java2as.core.AbstractProducer;
import flexserverlib.java2as.core.conf.TypeMapper;
import flexserverlib.java2as.core.meta.JavaService;
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

public class ServiceDelegateProducer extends AbstractProducer {

    //
    // Statics
    //

    private static final String SERVICE_IMPL_FTL = "service-impl.ftl";

    private ServiceDelegateConfiguration config;
	private List<Class<?>> classes;

	public ServiceDelegateProducer(ServiceDelegateConfiguration config, List<Class<?>> classes) {
		this.config = config;
		this.classes = classes;
	}

	@Override
	public void produce() {
		// filter
		List<Class<?>> matchingClasses = findMatchingClasses(config.getMatchers(), classes);

		// build metadata
		List<JavaService> javaServices = buildMetadata(matchingClasses);

		// build template configuration
		Configuration fmConfig = new Configuration();
		fmConfig.setClassForTemplateLoading(ServiceDelegateProducer.class, "");

		try {
			Template template = fmConfig.getTemplate(SERVICE_IMPL_FTL);
			Map<String, Object> model = new HashMap<String, Object>();

			// setup mappers
			TypeMapper<As3Type> typeMapper = config.getTypeMapper();
			if (typeMapper == null)
				typeMapper = new DefaultAs3TypeMapper();

            MethodMapper methodMapper = config.getMethodMapper();
            if (methodMapper == null)
                methodMapper = new DefaultMethodMapper(typeMapper);

			// do conversion
			ServiceDelegateMapper mapper = new ServiceDelegateMapper(methodMapper, typeMapper);
			List<As3ServiceDelegate> as3Services = mapper.performMappings(javaServices);

			// generate files
			for (As3ServiceDelegate service : as3Services) {
				model.put("model", service);
				Writer writer = new PrintWriter(System.out);
				template.process(model, writer);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}
	}

	private List<JavaService> buildMetadata(List<Class<?>> classes) {
		List<JavaService> delegates = new ArrayList<JavaService>();
		for (Class<?> clazz : classes)
			delegates.add(new JavaService(clazz));
		return delegates;
	}
}