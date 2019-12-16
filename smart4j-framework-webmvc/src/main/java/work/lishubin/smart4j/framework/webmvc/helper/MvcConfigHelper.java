package work.lishubin.smart4j.framework.webmvc.helper;

import work.lishubin.smart4j.framework.constant.MvcConfigStant;
import work.lishubin.smart4j.framework.helper.AbstractConfigHelper;
import work.lishubin.smart4j.framework.utils.PropUtils;


/**
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/2 14:26
 */
public class MvcConfigHelper extends AbstractConfigHelper {
    public static String getAppJspPath() {
        return PropUtils.getStringValueWithDefault(
                getConfigProp(),
                MvcConfigStant.SMART_FRAMEWORK_APP_JSP_PATH,
                MvcConfigStant.SMART_FRAMEWORK_APP_JSP_PATH_DEFAULT_VALUE);
    }

    public static String getAppAssetsPath() {
        return PropUtils.getStringValueWithDefault(
                getConfigProp(),
                MvcConfigStant.SMART_FRAMEWORK_APP_ASSETS_PATH,
                MvcConfigStant.SMART_FRAMEWORK_APP_ASSETS_PATH_DEFAULT_VALUE
        );
    }

}
