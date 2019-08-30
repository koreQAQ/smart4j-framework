package work.lishubin.smart4j.framework.bean.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.lishubin.smart4j.framework.bean.annotation.Controller;
import work.lishubin.smart4j.framework.bean.annotation.Service;
import work.lishubin.smart4j.framework.bean.constant.ConfigConstant;
import work.lishubin.smart4j.framework.utils.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Bean 容器
 * @author 李树彬
 * @date 2019/8/30  11:53
 */
public class BeanHelper {


    private static final Set<Class<?>> CLASS_SET = new HashSet<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);

    // 加载项目基础包下所有的类到classSet中统一管理
    static {

        Set<Class<?>> allClassSetByPackage = ClassUtils.getAllClassSetByPackage(ConfigConstant.SMART_FRAMEWORK_BASE_PACKAGE);
        CLASS_SET.addAll(allClassSetByPackage);

    }

    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    public static Set<Class<?>> getServiceClass(){
        return getClassSetWithAnnotation(Service.class);
    }

    public static Set<Class<?>> getControllerClass(){
        return getClassSetWithAnnotation(Controller.class);
    }

    private static Set<Class<?>> getClassSetWithAnnotation(Class<? extends Annotation> annotation){

        Set<Class<?>> serviceClassSet = new HashSet<>();

        for (Class<?> cls : getClassSet()) {

            // 查找含有对应注解的类，并放入Set集合
            if (cls.isAnnotationPresent(annotation)) {
                serviceClassSet.add(cls);
            }
        }
        return serviceClassSet;
    }






}
