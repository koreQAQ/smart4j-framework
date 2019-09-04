package work.lishubin.smart4j.framework.bean.helper;

import work.lishubin.smart4j.framework.bean.constant.ConfigConstant;
import work.lishubin.smart4j.framework.utils.CodecUtils;
import work.lishubin.smart4j.framework.utils.PropUtils;

import java.util.Properties;

/**
 * @author 李树彬
 * @date 2019/8/29  23:59
 */
public class ConfigHelper {

    private static Properties CONFIG_PROP = PropUtils.getProperties(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver(){
        return PropUtils.getStringValue(CONFIG_PROP,ConfigConstant.SMART_FRAMEWORK_JDBC_DRIVER);
    }

    public static String getJdbcUrl(){
        return PropUtils.getStringValue(CONFIG_PROP,ConfigConstant.SMART_FRAMEWORK_JDBC_URL);
    }

    public static String getJdbcUsername(){
        return PropUtils.getStringValue(CONFIG_PROP,ConfigConstant.SMART_FRAMEWORK_JDBC_USERNAME);
    }


    public static String getJdbcPassword(){
        return PropUtils.getStringValue(CONFIG_PROP,ConfigConstant.SMART_FRAMEWORK_JDBC_PASSWORD);
    }


    public static String getAppJspPath(){
        return PropUtils.getStringValueWithDefault(
                CONFIG_PROP,
                ConfigConstant.SMART_FRAMEWORK_APP_JSP_PATH,
                ConfigConstant.SMART_FRAMEWORK_APP_JSP_PATH_DEFAULT_VALUE);
    }
    public static String getAppAssetsPath(){
        return PropUtils.getStringValueWithDefault(
                CONFIG_PROP,
                ConfigConstant.SMART_FRAMEWORK_APP_ASSETS_PATH,
                ConfigConstant.SMART_FRAMEWORK_APP_ASSETS_PATH_DEFAULT_VALUE);
    }

    public static Properties getConfigProp() {
        return CONFIG_PROP;
    }


}
