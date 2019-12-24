package work.lishubin.smart4j.framework.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import work.lishubin.smart4j.framework.aop.annotation.Aspect;
import work.lishubin.smart4j.framework.aop.annotation.MethodTime;

import java.lang.reflect.Method;

/**
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/24 10:34
 */
@Slf4j
@Aspect(value = MethodTime.class)
public class MethodTimeAspect extends AbstractAspectProxy {

    private long startTime = 0;


    @Override
    protected <T> T start(Class<?> targetClass, Method targetMethod, Object[] methodArgs) {
        // 记录初始时间
        startTime = System.currentTimeMillis();
        return super.start(targetClass, targetMethod, methodArgs);
    }

    @Override
    protected <T> T after(Class<?> targetClass, Method targetMethod, Object[] methodArgs) {

        //日志打印
        log.info("{} -  {} execution time :{}", targetClass.getName(), targetMethod.getName(), System.currentTimeMillis() - startTime);
        System.out.println(System.currentTimeMillis() - startTime);
        return super.after(targetClass, targetMethod, methodArgs);
    }
}
