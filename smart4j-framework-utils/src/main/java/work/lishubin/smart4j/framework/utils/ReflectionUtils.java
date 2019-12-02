package work.lishubin.smart4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 反射工具类
 * @author 李树彬
 * @date 2019/8/30  10:49
 */
public class ReflectionUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * 通过clazz类来返回对应的实体对象
     * @param cls clazz
     * @param <T> 未知类型
     * @return 实体对象
     */
    public static <T> T getNewInstance(Class<T> cls){
        T instance = null;
        try {
            instance = cls.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            LOGGER.error(" Instancing failure",e);
        }
        return instance;
    }

    public static Object  invokeMethod(Object target, Method method,Object... params){

        Object result = null;

        try {
            Parameter[] parameters = method.getParameters();
            if (parameters!=null && parameters.length!= 0){
                for (Parameter parameter : parameters) {
                    if ("params".equals(parameter.getName())){
                        result = method.invoke(target, params);
                    }
                }
            }
            result = method.invoke(target);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 给一个实体对象根据属性名称来设置属性值
     * @param target 实体对象
     * @param field 属性名称
     * @param fieldValue 属性值
     */
    public static void setField(Object target,Field field,Object fieldValue){

        try {
            field.setAccessible(true);
            field.set(target,fieldValue);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field failure pls check input parameter",e);
            e.printStackTrace();
        }

    }

}
