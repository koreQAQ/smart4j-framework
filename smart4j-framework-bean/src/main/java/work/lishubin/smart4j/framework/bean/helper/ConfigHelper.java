package work.lishubin.smart4j.framework.bean.helper;

import work.lishubin.smart4j.framework.constant.ConfigConstant;
import work.lishubin.smart4j.framework.constant.MvcConfigStant;
import work.lishubin.smart4j.framework.utils.PropUtils;

import java.util.Properties;

/**
 * @author 李树彬
 * @date 2019/8/29  23:59
 */
public class ConfigHelper {

    private static Properties CONFIG_PROP = PropUtils.getProperties(ConfigConstant.CONFIG_FILE);



    public static String getAppJspPath(){
        return PropUtils.getStringValueWithDefault(
                CONFIG_PROP,
                MvcConfigStant.SMART_FRAMEWORK_APP_JSP_PATH,
                MvcConfigStant.SMART_FRAMEWORK_APP_JSP_PATH_DEFAULT_VALUE);
    }
    public static String getAppAssetsPath(){
        return PropUtils.getStringValueWithDefault(
                CONFIG_PROP,
                MvcConfigStant.SMART_FRAMEWORK_APP_ASSETS_PATH,
                MvcConfigStant.SMART_FRAMEWORK_APP_ASSETS_PATH_DEFAULT_VALUE);
    }

    public static Properties getConfigProp() {
        return CONFIG_PROP;
    }


}
