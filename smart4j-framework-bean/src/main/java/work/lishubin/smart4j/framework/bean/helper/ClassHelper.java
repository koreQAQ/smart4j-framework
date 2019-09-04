package work.lishubin.smart4j.framework.bean.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.lishubin.smart4j.framework.bean.annotation.Controller;
import work.lishubin.smart4j.framework.bean.annotation.Service;
import work.lishubin.smart4j.framework.bean.constant.ConfigConstant;
import work.lishubin.smart4j.framework.utils.ClassUtils;
import work.lishubin.smart4j.framework.utils.PropUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 获得包下的所有类
 * @author 李树彬
 * @date 2019/8/30  0:15
 */
public class ClassHelper {


    private static final Set<Class<?>> CLASS_SET = new HashSet<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);

    // 加载项目基础包下所有的类到classSet中统一管理
    static {

        Set<Class<?>> allClassSetByPackage = ClassUtils.getAllClassSetByPackage(
            PropUtils.getStringValue(
                ConfigHelper.getConfigProp(),
                ConfigConstant.SMART_FRAMEWORK_BASE_PACKAGE
            )
        );
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

    public static Set<Class<?>> getClassSetWithAnnotation(Class<? extends Annotation> annotation){

        Set<Class<?>> serviceClassSet = new HashSet<>();

        for (Class<?> cls : getClassSet()) {

            // 查找含有对应注解的类，并放入Set集合
            if (cls.isAnnotationPresent(annotation)) {
                serviceClassSet.add(cls);
            }
        }
        return serviceClassSet;
    }

    // 获取扩展了父类的子类集合
    public static Set<Class<?>> getClassSetBySuperClass(Class superClass){

        Set<Class<?>> classSet = new HashSet<>();
        Set<Class<?>> allClassSet = getClassSet();
        for (Class<?> cls : allClassSet) {

            if (superClass.isAssignableFrom(cls) && !cls.equals(superClass)) {
                classSet.add(cls);
            }

        }

        return classSet;

    }






}
