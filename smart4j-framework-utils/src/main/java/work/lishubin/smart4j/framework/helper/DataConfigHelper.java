package work.lishubin.smart4j.framework.helper;

import work.lishubin.smart4j.framework.constant.DataConfigStant;
import work.lishubin.smart4j.framework.utils.PropUtils;

/**
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/2 14:26
 */
public class DataConfigHelper extends AbstractConfigHelper {

    public static String getJdbcDriver() {
        return PropUtils.getStringValue(getConfigProp(), DataConfigStant.SMART_FRAMEWORK_JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropUtils.getStringValue(getConfigProp(), DataConfigStant.SMART_FRAMEWORK_JDBC_URL);
    }

    public static String getJdbcUsername() {
        return PropUtils.getStringValue(getConfigProp(), DataConfigStant.SMART_FRAMEWORK_JDBC_USERNAME);
    }


    public static String getJdbcPassword() {
        return PropUtils.getStringValue(getConfigProp(), DataConfigStant.SMART_FRAMEWORK_JDBC_PASSWORD);
    }

}
