package net.histos.java2as;

import net.histos.java2as.as3.DefaultAs3TypeMapperTests;
import net.histos.java2as.as3.service.ServiceDelegateProducerTests;
import net.histos.java2as.as3.transfer.As3PropertyTests;
import net.histos.java2as.as3.transfer.TransferObjectProducerTests;
import net.histos.java2as.as3.transfer.extensions.AmfIgnoreAs3PropertyMapperTests;
import net.histos.java2as.as3.transfer.extensions.JpaIdentifierAs3PropertyMapperTests;
import net.histos.java2as.core.conf.matchers.AnnotationTypeMatcherTests;
import net.histos.java2as.core.conf.matchers.InterfaceTypeMatcherTests;
import net.histos.java2as.core.conf.matchers.SuperclassTypeMatcherTests;
import net.histos.java2as.core.conf.packages.PackageMapperRuleTests;
import net.histos.java2as.core.meta.JavaMethodTests;
import net.histos.java2as.core.meta.JavaTransferObjectTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		DefaultAs3TypeMapperTests.class,
		ServiceDelegateProducerTests.class,
		As3PropertyTests.class,
		TransferObjectProducerTests.class,
		AmfIgnoreAs3PropertyMapperTests.class,
		JpaIdentifierAs3PropertyMapperTests.class,
		AnnotationTypeMatcherTests.class,
		InterfaceTypeMatcherTests.class,
		SuperclassTypeMatcherTests.class,
		PackageMapperRuleTests.class,
		JavaMethodTests.class,
		JavaTransferObjectTests.class
})
public class AllTests {

}
