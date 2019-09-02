package work.lishubin.smart4j.framework.webmvc.helper;

import work.lishubin.smart4j.framework.bean.annotation.Action;
import work.lishubin.smart4j.framework.bean.helper.ClassHelper;
import work.lishubin.smart4j.framework.utils.ReflectionUtils;
import work.lishubin.smart4j.framework.webmvc.entity.SmartHandler;
import work.lishubin.smart4j.framework.webmvc.entity.SmartRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 将所有Controller类中带有@Action注解的方法建立起一个ActionMap
 * ActionMap 维护 SmartRequest 和 SmartHandler 之间的映射关系
 * @author 李树彬
 * @date 2019/8/31  22:26
 */
public class ControllerHelper {


    private static final Map<SmartRequest,SmartHandler> ACTION_MAP = new HashMap<>();

    static {

        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClass();

        // 遍历每一个controller
        for (Class<?> controllerClass : controllerClassSet) {

            // 获得 Controller 中所有声明的方法
            Method[] declaredMethods = controllerClass.getDeclaredMethods();

            // 遍历所有方法
            for (Method declaredMethod : declaredMethods) {

                // 如果方法上存在Action注解
                if (declaredMethod.isAnnotationPresent(Action.class)){


                    // 解析Action注解内容 获取method 和 url
                    Action actionAnnotation = declaredMethod.getAnnotation(Action.class);
                    String methodWithUrl = actionAnnotation.value();

                    String[] splitString = methodWithUrl.split(":");
                    String requestMethod = splitString[0];
                    String requestUrl = splitString[1];

                    SmartRequest smartRequest = SmartRequest.getNewInstance();
                    smartRequest.setRequestMethod(requestMethod);
                    smartRequest.setRequestUrl(requestUrl);

                    SmartHandler smartHandler = SmartHandler.getNewInstance();
                    smartHandler.setController(controllerClass);
                    smartHandler.setMethod(declaredMethod);

                    ACTION_MAP.put(smartRequest, smartHandler);
                }

            }


        }

    }


    public static SmartHandler getSmartHandler(String requestMethod,String requestUrl){
        SmartRequest smartRequest = SmartRequest.getNewInstance();
        smartRequest.setRequestMethod(requestMethod);
        smartRequest.setRequestUrl(requestUrl);
        return getSmartHandler(smartRequest);
    }

    private static SmartHandler getSmartHandler(SmartRequest smartRequest){

        SmartHandler smartHandler = null;

        if (ACTION_MAP.containsKey(smartRequest)){
            smartHandler = ACTION_MAP.get(smartRequest);
        }
        return smartHandler;

    }
}
