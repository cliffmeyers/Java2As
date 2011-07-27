package net.histos.java2as.test;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;

/**
 * @author cliff.meyers
 */
public class JMockTestCase {

	protected Mockery context;

	@Before
	public void setUp() {
		context = new Mockery() {{
			setImposteriser(ClassImposteriser.INSTANCE);
		}};
	}

}
