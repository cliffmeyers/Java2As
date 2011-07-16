package flexserverlib.java2as.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class PackageMapperTests {



    @Before
    public void setUp() {

    }
    
    @Test
    public void testSimplePackageMapper() {

        SimplePackageMapper mapper = new SimplePackageMapper("net.histos.java2as.dto", "net.histos.java2as.vo");
        String packageName;

        packageName = "net.histos";
        Assert.assertFalse(mapper.canMap(packageName));

        packageName = "net.histos.java2as.dto";
        Assert.assertTrue(mapper.canMap(packageName));
        Assert.assertEquals("net.histos.java2as.vo", mapper.performMap("net.histos.java2as.dto"));

        packageName = "net.histos.java2as.dto.user";
        Assert.assertTrue(mapper.canMap(packageName));
        Assert.assertEquals("net.histos.java2as.vo.user", mapper.performMap("net.histos.java2as.dto.user"));
        
    }

    @Test
    public void testCompositePackageMapper() {

        String packageName;
        CompositePackageMapper mapper = new CompositePackageMapper();
        mapper.addMapper(new SimplePackageMapper("net.histos", "com.foo"));
        mapper.addMapper(new SimplePackageMapper("net.histos.java2as", "com.foo.java2as"));
        mapper.addMapper(new SimplePackageMapper("net.histos.java2as.dto", "com.foo.java2as.vo"));
        mapper.addMapper(new SimplePackageMapper("net.histos.java2as.zzz", "com.foo.java2as.aaa"));

        packageName = "net.histos.java2as.dto.user";
        Assert.assertTrue(mapper.canMap(packageName));
        Assert.assertEquals("com.foo.java2as.vo.user", mapper.performMap("net.histos.java2as.dto.user"));

    }


}
