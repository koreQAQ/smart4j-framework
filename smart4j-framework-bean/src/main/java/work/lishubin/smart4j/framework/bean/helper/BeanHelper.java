package work.lishubin.smart4j.framework.bean.helper;

import work.lishubin.smart4j.framework.utils.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean 容器 , 使class与bean实例一一对应
 * @author 李树彬
 * @date 2019/8/30  11:53
 */
public class BeanHelper {

    /**
     * 维护Class与instance之间的映射关系
     */
    private static Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        // 建立起映射关系
        Set<Class<?>> beanClassSet = BeanClassHelper.getBeanClassSet();
        for (Class<?> cls : beanClassSet) {
            Object instance = ReflectionUtils.getNewInstance(cls);
            BEAN_MAP.put(cls, instance);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<?> cls){
        T instance = null;
        if (!BEAN_MAP.isEmpty() && BEAN_MAP.containsKey(cls)){

            Object o = BEAN_MAP.get(cls);
            if (o != null){

                instance = (T) o;
            }
        }
        return instance;
    }

    /**
     * 放入一组 类和对象之间的映射关系 为了方便AOP模块
     * 把原先的对象（不是代理对象）去除
     *
     * @param cls  类
     * @param bean 对象bean
     */
    public static void putBean(Class<?> cls, Object bean) {
        BEAN_MAP.remove(cls);
        BEAN_MAP.put(cls, bean);
    }


}
