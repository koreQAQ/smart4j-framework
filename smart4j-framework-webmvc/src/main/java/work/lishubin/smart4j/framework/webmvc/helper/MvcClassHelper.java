package work.lishubin.smart4j.framework.webmvc.helper;

import work.lishubin.smart4j.framework.bean.annotation.Controller;
import work.lishubin.smart4j.framework.bean.annotation.Service;
import work.lishubin.smart4j.framework.helper.AbstractClassHelper;

import java.util.Set;

/**
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/2 16:48
 */
public class MvcClassHelper extends AbstractClassHelper {

    public static Set<Class<?>> getServiceClass() {
        return getClassSetWithAnnotation(Service.class);
    }

    public static Set<Class<?>> getControllerClass() {
        return getClassSetWithAnnotation(Controller.class);
    }

}
