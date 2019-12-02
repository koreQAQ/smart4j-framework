package work.lishubin.smart4j.framework.helper;

import lombok.extern.slf4j.Slf4j;
import work.lishubin.smart4j.framework.constant.BeanConfigStant;
import work.lishubin.smart4j.framework.utils.ClassUtils;
import work.lishubin.smart4j.framework.utils.PropUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/2 16:49
 */
@Slf4j
public class AbstractClassHelper {

    private static final Set<Class<?>> CLASS_SET = new HashSet<>();


    // 加载项目基础包下所有的类到classSet中统一管理
    static {

        Set<Class<?>> allClassSetByPackage = ClassUtils.getAllClassSetByPackage(
                PropUtils.getStringValue(
                        AbstractConfigHelper.getConfigProp(), BeanConfigStant.SMART_FRAMEWORK_BASE_PACKAGE
                )
        );
        log.info("load base-package class into CLASS_SET {}", allClassSetByPackage);
        CLASS_SET.addAll(allClassSetByPackage);

    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }


    protected static Set<Class<?>> getClassSetWithAnnotation(Class<? extends Annotation> annotation) {

        Set<Class<?>> serviceClassSet = new HashSet<>();

        for (Class<?> cls : getClassSet()) {

            // 查找含有对应注解的类，并放入Set集合
            if (cls.isAnnotationPresent(annotation)) {
                serviceClassSet.add(cls);
            }
        }
        return serviceClassSet;
    }

    /**
     * 获取扩展了父类的子类集合
     *
     * @param superClass 父类
     * @return 扩展父类的子类集合
     */
    protected static Set<Class<?>> getClassSetBySuperClass(Class superClass) {

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
