package work.lishubin.smart4j.framework.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;


/**
 * @author Lishubin
 * @date 2019/8/29  23:27
 */
public class PropUtilsTest {

    @Test
    public void getPropertiesTest() {
        Properties properties = PropUtils.getProperties("smart4j.properties");
        Assert.assertNotNull(properties);
        Assert.assertEquals("com.mysql.jdbc.Driver",properties.getProperty("smart4j.framework.jdbc.driver"));
    }
}