package work.lishubin.smart4j.framework.bean.helper;

import lombok.extern.slf4j.Slf4j;
import work.lishubin.smart4j.framework.bean.annotation.Bean;
import work.lishubin.smart4j.framework.helper.AbstractClassHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/2 17:08
 */
@Slf4j
public class BeanClassHelper extends AbstractClassHelper {


    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getClassSetWithAnnotation(Bean.class));
        log.info("loading Bean class... {}", beanClassSet);
        return beanClassSet;
    }


}
