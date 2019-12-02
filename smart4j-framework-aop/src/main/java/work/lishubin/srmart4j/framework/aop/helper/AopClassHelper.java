package work.lishubin.srmart4j.framework.aop.helper;

import work.lishubin.smart4j.framework.bean.annotation.Service;
import work.lishubin.smart4j.framework.helper.AbstractClassHelper;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/2 20:32
 */
public class AopClassHelper extends AbstractClassHelper {


    public static Set<Class<?>> getServiceClass() {

        return getClassSetWithAnnotation(Service.class);

    }


    public static Set<Class<?>> getClassSetWithAnnotation(Class<? extends Annotation> annotation) {
        return AbstractClassHelper.getClassSetWithAnnotation(annotation);
    }

    public static Set<Class<?>> getClassSetBySuperClass(Class superClass) {
        return AbstractClassHelper.getClassSetBySuperClass(superClass);
    }
}
