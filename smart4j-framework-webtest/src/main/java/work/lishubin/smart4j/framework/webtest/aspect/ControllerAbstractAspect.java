package work.lishubin.smart4j.framework.webtest.aspect;

import work.lishubin.smart4j.framework.bean.annotation.Controller;
import work.lishubin.srmart4j.framework.aop.annotation.Aspect;
import work.lishubin.srmart4j.framework.aop.aspect.AbstractAspectProxy;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author lishubin
 */
@SuppressWarnings("unchecked")
@Aspect(Controller.class)
public class ControllerAbstractAspect extends AbstractAspectProxy {

    @Override
    protected <T> T start(Class targetClass, Method targetMethod, Object[] methodArgs) {
        return (T) new Date(System.currentTimeMillis());
    }


    @Override
    protected <T> T after(Class targetClass, Method targetMethod, Object[] methodArgs) {

        Object startResult = getStartResult();
        Long startTime = System.currentTimeMillis();

        if (startResult instanceof Date) {
            startTime = ((Date) startResult).getTime();
        }
        Long afterTime = System.currentTimeMillis();

        String targetMethodName = targetMethod.getName();
        String timeCount = String.format("性能分析：%s方法耗时 %s毫秒", targetMethodName, String.valueOf(afterTime - startTime));

        return (T) timeCount;

    }
}
