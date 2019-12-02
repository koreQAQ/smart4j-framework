package work.lishubin.smart4j.framework.helper;


import lombok.extern.slf4j.Slf4j;
import work.lishubin.smart4j.framework.constant.ConfigConstant;
import work.lishubin.smart4j.framework.utils.PropUtils;

import java.util.Properties;

/**
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/2 14:21
 */
@Slf4j
public abstract class AbstractConfigHelper {


    /**
     * 通过对应的类来进行回调
     */
    public void setConfiguration() {


    }

    private static Properties CONFIG_PROP = PropUtils.getProperties(ConfigConstant.CONFIG_FILE);

    public static Properties getConfigProp() {
        log.info("CONFIG_PROP is {}", CONFIG_PROP);
        return CONFIG_PROP;
    }


}
