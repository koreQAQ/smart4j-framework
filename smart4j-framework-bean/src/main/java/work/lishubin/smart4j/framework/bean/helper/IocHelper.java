package work.lishubin.smart4j.framework.bean.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.lishubin.smart4j.framework.bean.annotation.Inject;
import work.lishubin.smart4j.framework.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * 负责将Bean注入到对应的@Inject位置
 *
 * @author 李树彬
 * @date 2019/8/31  8:43
 */
public class IocHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);


    static {


        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

        //扫描包下的所有类
        Set<Class<?>> classSet = BeanClassHelper.getClassSet();

        injectByClassSet(classSet, beanMap);

    }

    public static void injectByClassSet(Set<Class<?>> classSet, Map<Class<?>, Object> beanMap) {
        for (Class<?> cls : classSet) {

            // 取得对应类的实体
            Object classInstance = beanMap.get(cls);

            // 取得每个类的属性
            Field[] declaredFields = cls.getDeclaredFields();

            // 对于每个属性
            for (Field declaredField : declaredFields) {

                // 1. 判断是否含有@Inject注解
                if (declaredField.isAnnotationPresent(Inject.class)) {

                    // 2. 存在的Inject
                    // 查看是否已经有对应的对象
                    Class<?> fieldClass = declaredField.getClass();

                    //2.1 根据类名注入
                    if (beanMap.containsKey(fieldClass)) {
                        // 取得对应的bean,并将bean通过反射注入到属性中
                        Object fieldInstance = beanMap.get(declaredField.getClass());
                        ReflectionUtils.setField(classInstance, declaredField, fieldInstance);
                    }
                }

            }

        }
    }

}
