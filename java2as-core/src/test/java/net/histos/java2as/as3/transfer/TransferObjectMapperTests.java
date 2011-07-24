package net.histos.java2as.as3.transfer;

import net.histos.java2as.as3.As3SimpleType;
import net.histos.java2as.core.conf.PackageMapper;
import net.histos.java2as.core.conf.PropertyMapper;
import net.histos.java2as.core.conf.TypeMapper;
import net.histos.java2as.core.meta.JavaProperty;
import net.histos.java2as.core.meta.JavaTransferObject;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author cliff.meyers
 */
public class TransferObjectMapperTests {

	private Mockery context;
	private TransferObjectMapper mapper;

	@Before
	public void setUp() {
		context = new Mockery() {{
			setImposteriser(ClassImposteriser.INSTANCE);
		}};
	}

	/**
	 * Tests that a JavaProperty which maps to null is not added to the As3TransferObject by the TransferObjectMapper.
	 */
	@Test
	public void testMapPropertyFound() {

		final PropertyMapper propertyMapper = context.mock(PropertyMapper.class);
		final TypeMapper typeMapper = context.mock(TypeMapper.class);
		final PackageMapper packageMapper = context.mock(PackageMapper.class);
		final JavaTransferObject javaTransferObject = context.mock(JavaTransferObject.class);
		final JavaProperty javaProperty = context.mock(JavaProperty.class);

		As3TransferObject transferObject = new As3TransferObject(javaTransferObject);
		mapper = new TransferObjectMapper(propertyMapper, typeMapper, packageMapper);

		context.checking(new Expectations() {{
			oneOf(javaProperty).getName();
			will(returnValue("name"));
		}});
		context.checking(new Expectations() {{
			oneOf(propertyMapper).mapProperty(javaProperty);
			will(returnValue(new As3Property(javaProperty, As3SimpleType.Number)));
		}});

		mapper.mapProperty(transferObject, javaProperty);
		Assert.assertEquals(1, transferObject.getProperties().size());

	}

	/**
	 * Tests that a JavaProperty which maps to null is not added to the As3TransferObject by the TransferObjectMapper.
	 */
	@Test
	public void testMapPropertySuppressed() {

		final PropertyMapper propertyMapper = context.mock(PropertyMapper.class);
		final TypeMapper typeMapper = context.mock(TypeMapper.class);
		final PackageMapper packageMapper = context.mock(PackageMapper.class);
		final JavaTransferObject javaTransferObject = context.mock(JavaTransferObject.class);
		final JavaProperty javaProperty = context.mock(JavaProperty.class);

		As3TransferObject transferObject = new As3TransferObject(javaTransferObject);
		mapper = new TransferObjectMapper(propertyMapper, typeMapper, packageMapper);

		context.checking(new Expectations() {{
			oneOf(propertyMapper).mapProperty(javaProperty);
			will(returnValue(null));
		}});

		mapper.mapProperty(transferObject, javaProperty);
		Assert.assertEquals(0, transferObject.getProperties().size());

	}

}
