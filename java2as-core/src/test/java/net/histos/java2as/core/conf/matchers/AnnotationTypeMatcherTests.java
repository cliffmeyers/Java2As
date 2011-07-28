package net.histos.java2as.core.conf.matchers;

import net.histos.java2as.core.conf.TypeMatcher;
import net.histos.java2as.core.conf.matchers.test.annotation.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author cliff.meyers
 */
public class AnnotationTypeMatcherTests {

	protected static TypeMatcher MATCHER;

	@BeforeClass
	public static void setUpOnce() {
		MATCHER = new AnnotationTypeMatcher("net.histos.java2as.core.conf.matchers.test.annotation.Dto");
	}

	@Test
	public void testFalsePositiveClass() {
		Assert.assertFalse(MATCHER.match(Entity.class));
	}

	@Test
	public void testDirectAnnotationClass() {
		Assert.assertTrue(MATCHER.match(AbstractDto.class));
	}

	@Test
	public void testDirectSubclassAnnotationClass() {
		Assert.assertTrue(MATCHER.match(EntityDto.class));
	}

	@Test
	public void testDescendantSubclassAnnotationClass() {
		Assert.assertTrue(MATCHER.match(EntityPersonDto.class));
	}

	@Test
	public void testInterfaceAnnotationClass() {
		Assert.assertFalse(MATCHER.match(IDto.class));
	}

	@Test
	public void testDirectImplementorAnnotationClass() {
		Assert.assertTrue(MATCHER.match(AddressDto.class));
	}

	@Test
	public void testInheritedImplementorAnnotationClass() {
		Assert.assertTrue(MATCHER.match(AddressLocalDto.class));
	}

}
