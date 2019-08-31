package work.lishubin.smart4j.framework.bean.helper;

import work.lishubin.smart4j.framework.bean.constant.ConfigConstant;
import work.lishubin.smart4j.framework.utils.PropUtils;

import java.util.Properties;

/**
 * @author 李树彬
 * @date 2019/8/29  23:59
 */
public class ConfigHelper {

    private static Properties CONFIG_PROP = PropUtils.getProperties(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver(){
        //todo
        return null;
    }

    public static String getJdbcUrl(){
        //todo
        return null;
    }

    public static String getJdbcUsername(){
        //todo
        return null;
    }

    public static String getJdbcPassword(){
        //todo
        return null;
    }


    public static Properties getConfigProp() {
        return CONFIG_PROP;
    }

    public static void setConfigProp(Properties configProp) {
        CONFIG_PROP = configProp;
    }
}
