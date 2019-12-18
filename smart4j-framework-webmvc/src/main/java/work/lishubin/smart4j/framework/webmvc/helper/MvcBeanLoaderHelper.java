package work.lishubin.smart4j.framework.webmvc.helper;

import work.lishubin.smart4j.framework.bean.helper.BeanHelper;
import work.lishubin.smart4j.framework.bean.helper.IocHelper;
import work.lishubin.smart4j.framework.utils.ReflectionUtils;

import java.util.Map;
import java.util.Set;

/**
 * MvcBeanLoaderHelper 用于扫描对应包下的所有带有Controller和@Service的注解
 * 并完成依赖注入
 *
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/18 17:09
 */
public class MvcBeanLoaderHelper {

    /**
     * 实现将所有的Controller，Service放在BeanMap,所有的Controller进行依赖注入
     */

    public static void init() {
        //得到目前所有的Bean
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();


        //先加载ServiceClass
        loadServiceClass(beanMap);

        //加载所有的ControllerClass
        loadControllerClass(beanMap);
    }


    /**
     * 加载所有的Controller，并把Service的实体类注入
     *
     * @param beanMap bean容器
     */
    private static void loadControllerClass(Map<Class<?>, Object> beanMap) {

        //得到所有的Controller.class
        Set<Class<?>> controllerClasses = MvcClassHelper.getControllerClass();
        putClassAndInstance(controllerClasses, beanMap);

        //将所有的Service注入
        IocHelper.injectByClassSet(controllerClasses, beanMap);


    }

    /**
     * 加载所有的Service
     *
     * @param beanMap bean容器
     */
    private static void loadServiceClass(Map<Class<?>, Object> beanMap) {

        //得到所有的Service.class
        Set<Class<?>> controllerClasses = MvcClassHelper.getServiceClass();
        putClassAndInstance(controllerClasses, beanMap);

    }

    private static void putClassAndInstance(Set<Class<?>> controllerClasses, Map<Class<?>, Object> beanMap) {
        controllerClasses.forEach(
                controllerClass -> {
                    Object newInstance = ReflectionUtils.getNewInstance(controllerClass);
                    beanMap.put(controllerClass, newInstance);
                }
        );

    }


}
