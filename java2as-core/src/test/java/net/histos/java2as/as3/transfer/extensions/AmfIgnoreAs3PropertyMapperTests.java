package net.histos.java2as.as3.transfer.extensions;

import net.histos.java2as.as3.transfer.As3Property;
import net.histos.java2as.as3.transfer.extensions.test.Person;
import net.histos.java2as.as3.transfer.extensions.test.Thing;
import net.histos.java2as.core.conf.PropertyMapper;
import net.histos.java2as.core.meta.JavaProperty;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author cliff.meyers
 */
public class AmfIgnoreAs3PropertyMapperTests {

	private PropertyMapper<As3Property> mapper;

	@Before
	public void setUp() {
		mapper = new AmfIgnoreAs3PropertyMapper();
	}

	@Test
	public void testMapperAmfIgnoreGetter() throws NoSuchMethodException {
		JavaProperty personPhotoProp = new JavaProperty(Person.class.getMethod("getPhoto"));
		Assert.assertTrue(mapper.canMapProperty(personPhotoProp));
		Assert.assertNull(mapper.mapProperty(personPhotoProp));
	}

	/*
	NOTE: will support this eventually; see AmfIgnoreAs3PropertyMapper source comments
	@Test
	public void testMapperAmfIgnoreField() throws NoSuchMethodException {
		JavaProperty placePhotoProp = new JavaProperty(Place.class.getField("photo"));
		Assert.assertTrue(mapper.canMapProperty(placePhotoProp));
		Assert.assertNull(mapper.mapProperty(placePhotoProp));
	}
	*/

	@Test
	public void testMapperAmfIgnoreFalse() throws NoSuchMethodException {
		JavaProperty thingPhotoProp = new JavaProperty(Thing.class.getMethod("getPhoto"));
		Assert.assertFalse(mapper.canMapProperty(thingPhotoProp));
	}

}
