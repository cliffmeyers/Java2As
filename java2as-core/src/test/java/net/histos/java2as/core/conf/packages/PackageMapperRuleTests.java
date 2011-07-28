package net.histos.java2as.core.conf.packages;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class PackageMapperRuleTests {


	@Before
	public void setUp() {

	}

	@Test
	public void testPackageMapperRule() {

		PackageMapperRule mapperRule = new PackageMapperRule("net.histos.java2as.dto", "net.histos.java2as.vo");
		String packageName;

		packageName = "net.histos";
		Assert.assertFalse(mapperRule.canMap(packageName));

		packageName = "net.histos.java2as.dto";
		Assert.assertTrue(mapperRule.canMap(packageName));
		Assert.assertEquals("net.histos.java2as.vo", mapperRule.performMap("net.histos.java2as.dto"));

		packageName = "net.histos.java2as.dto.user";
		Assert.assertTrue(mapperRule.canMap(packageName));
		Assert.assertEquals("net.histos.java2as.vo.user", mapperRule.performMap("net.histos.java2as.dto.user"));

	}

	@Test
	public void testRuleBasedPackageMapper() {

		String packageName;
		RuleBasedPackageMapper mapper = new RuleBasedPackageMapper();
		mapper.addMapperRule(new PackageMapperRule("net.histos", "com.foo"));
		mapper.addMapperRule(new PackageMapperRule("net.histos.java2as", "com.foo.java2as"));
		mapper.addMapperRule(new PackageMapperRule("net.histos.java2as.dto", "com.foo.java2as.vo"));
		mapper.addMapperRule(new PackageMapperRule("net.histos.java2as.zzz", "com.foo.java2as.aaa"));

		packageName = "net.histos.java2as.dto.user";
		Assert.assertTrue(mapper.canMap(packageName));
		Assert.assertEquals("com.foo.java2as.vo.user", mapper.performMap("net.histos.java2as.dto.user"));

	}


}
