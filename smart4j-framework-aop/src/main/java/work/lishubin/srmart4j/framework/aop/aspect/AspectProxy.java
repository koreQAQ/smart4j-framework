package work.lishubin.srmart4j.framework.aop.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.lishubin.srmart4j.framework.aop.proxy.Proxy;
import work.lishubin.srmart4j.framework.aop.proxy.ProxyChain;

import java.lang.reflect.Method;

/**
 * @author lishubin
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);


    private Object beginResult = null;
    private Object interceptorResult = null;
    private Object startResult = null;
    private Object methodResult = null;
    private Object afterResult = null;
    private Object errorResult = null;
    private Object endResult = null;

    @Override
    public Object doProxy(ProxyChain proxyChain) {

        beginResult = begin();

        Class targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodArgs = proxyChain.getMethodArgs();
        try{
            if (intercept(targetClass,targetMethod,methodArgs)){

                startResult = start(targetClass,targetMethod,methodArgs);
                methodResult = proxyChain.doProxyChain();
                afterResult = after(targetClass,targetMethod,methodArgs);
            }
            else {
                methodResult = proxyChain.doProxyChain();
            }

        }
        catch (Throwable e){

            LOGGER.error("aspect meet some error",e);
            errorResult = error(targetClass,targetMethod,methodArgs,e);

        }
        finally {
            endResult = end();
        }

        return methodResult;

    }


    // 定义钩子函数


    protected boolean intercept(Class targetClass, Method targetMethod, Object[] methodArgs) {
        return true;
    }

    protected <T> T start(Class targetClass, Method targetMethod, Object[] methodArgs) {
        return null;
    }


    protected <T> T after(Class targetClass, Method targetMethod, Object[] methodArgs) {
        return null;
    }

    private <T> T error(Class targetClass, Method targetMethod, Object[] methodArgs, Throwable e) {
        return null;
    }

    protected <T> T begin(){
        return null;
    }

    protected <T> T end(){
        return null;
    }


    public Object getBeginResult() {
        return beginResult;
    }

    public Object getInterceptorResult() {
        return interceptorResult;
    }

    public Object getStartResult() {
        return startResult;
    }

    public Object getMethodResult() {
        return methodResult;
    }

    public Object getAfterResult() {
        return afterResult;
    }

    public Object getErrorResult() {
        return errorResult;
    }

    public Object getEndResult() {
        return endResult;
    }
}
