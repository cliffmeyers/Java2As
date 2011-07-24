package net.histos.java2as.as3.transfer.extensions;

import net.histos.java2as.as3.As3SimpleType;
import net.histos.java2as.as3.transfer.As3Property;
import net.histos.java2as.as3.transfer.extensions.test.Person;
import net.histos.java2as.core.conf.PropertyMapper;
import net.histos.java2as.core.meta.JavaProperty;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author cliff.meyers
 */
public class JpaIdentifierAs3PropertyMapperTests {

	private PropertyMapper<As3Property> mapper;

	@Before
	public void setUp() {
		mapper = new JpaIdentifierAs3PropertyMapper();
	}

	@Test
	public void testMapperJpaIdentifierGetter() throws NoSuchMethodException {
		JavaProperty personIdProp = new JavaProperty(Person.class.getMethod("getId"));
		Assert.assertTrue(mapper.canMapProperty(personIdProp));
		Assert.assertEquals(As3SimpleType.Object, mapper.mapProperty(personIdProp).getType());
	}

	/*
	NOTE: will support this eventually; see JpaIdentifierAs3PropertyMapper source comments
	@Test
	public void testMapperAmfIgnoreField() throws NoSuchMethodException {
		JavaProperty personIdProp = new JavaProperty(Person.class.getField("id"));
		Assert.assertTrue(mapper.canMapProperty(personIdProp));
		Assert.assertEquals(As3SimpleType.Object, mapper.mapProperty(personIdProp).getType());
	}
	*/

	@Test
	public void testMapperJpaIdentifierFalse() throws NoSuchMethodException {
		JavaProperty personFirstNameProp = new JavaProperty(Person.class.getMethod("getFirstName"));
		Assert.assertFalse(mapper.canMapProperty(personFirstNameProp));
	}

}
