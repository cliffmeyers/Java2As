package net.histos.java2as.core.conf.matchers;

import net.histos.java2as.core.conf.TypeMatcher;
import net.histos.java2as.core.conf.matchers.test.superclass.AbstractDto;
import net.histos.java2as.core.conf.matchers.test.superclass.Entity;
import net.histos.java2as.core.conf.matchers.test.superclass.EntityDto;
import net.histos.java2as.core.conf.matchers.test.superclass.EntityPersonDto;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author cliff.meyers
 */
public class SuperclassTypeMatcherTests {

	protected static TypeMatcher MATCHER;

	@BeforeClass
	public static void setUpOnce() {
		MATCHER = new SuperclassTypeMatcher("net.histos.java2as.core.conf.matchers.test.superclass.AbstractDto");
	}

	@Test
	public void testFalsePositiveClass() {
		Assert.assertFalse(MATCHER.match(Entity.class));
	}

	@Test
	public void testDirectDescendantClass() {
		Assert.assertTrue(MATCHER.match(EntityDto.class));
	}
	
	@Test
	public void testNestedDescendantClass() {
		Assert.assertTrue(MATCHER.match(EntityPersonDto.class));
	}

	@Test
	public void testActualSuperclass() {
		Assert.assertTrue(MATCHER.match(AbstractDto.class));
	}
	
}
