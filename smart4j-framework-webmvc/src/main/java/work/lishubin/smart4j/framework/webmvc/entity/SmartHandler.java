package work.lishubin.smart4j.framework.webmvc.entity;

import java.lang.reflect.Method;

/**
 * @author 李树彬
 * @date 2019/8/31  23:24
 */
public class SmartHandler {


    private Class<?> controller;
    private Method method;

    private SmartHandler() {

    }

    public static SmartHandler getNewInstance(){
        return new SmartHandler();
    }

    public Class<?> getController() {
        return controller;
    }

    public void setController(Class<?> controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
