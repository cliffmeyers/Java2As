package flexserverlib.java2as.core.meta;

import flexserverlib.java2as.as3.service.test.IUserService;
import flexserverlib.java2as.as3.transfer.test.User;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class JavaMethodTests {

	@Test
	public void testMethodParameters() {

		for (Method method : IUserService.class.getDeclaredMethods()) {

			JavaMethod javaMethod = new JavaMethod(method);
			String name = javaMethod.getName();
			List<JavaMethodParameter> params = javaMethod.getParameters();

			if ("listUsers".equals(name)) {
				Assert.assertEquals(0, params.size());
				Assert.assertEquals(List.class, javaMethod.getReturnType());
			} else if ("saveUser".equals(name)) {
				if (!"user".equals(params.get(0).getName()) && !"arg0".equals(params.get(0).getName()))
					Assert.fail("Parameter name must be either 'user' or 'arg0'");
				Assert.assertEquals(User.class, params.get(0).getType());
				Assert.assertEquals(0, params.get(0).getAnnotations().length);
				Assert.assertEquals("void", javaMethod.getReturnType().getName());
			} else {
				Assert.fail("No test cases written for method=" + name);
			}

		}

	}

}
