package net.histos.java2as.core.conf.matchers;

import net.histos.java2as.core.conf.TypeMatcher;
import net.histos.java2as.core.conf.matchers.test.interfaces.Entity;
import net.histos.java2as.core.conf.matchers.test.interfaces.EntityDto;
import net.histos.java2as.core.conf.matchers.test.interfaces.IDto;
import net.histos.java2as.core.conf.matchers.test.interfaces.EntityPersonDto;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author cliff.meyers
 */
public class InterfaceTypeMatcherTests {

	protected static TypeMatcher MATCHER;

	@BeforeClass
	public static void setUpOnce() {
		MATCHER = new InterfaceTypeMatcher("net.histos.java2as.core.conf.matchers.test.interfaces.IDto");
	}

	@Test
	public void testFalsePositiveClass() {
		Assert.assertFalse(MATCHER.match(Entity.class));
	}

	@Test
	public void testDirectImplementingClass() {
		Assert.assertTrue(MATCHER.match(EntityDto.class));
	}

	@Test
	public void testNestedImplementingClass() {
		Assert.assertTrue(MATCHER.match(EntityPersonDto.class));
	}

	@Test
	public void testActualInterface() {
		Assert.assertFalse(MATCHER.match(IDto.class));
	}

}
