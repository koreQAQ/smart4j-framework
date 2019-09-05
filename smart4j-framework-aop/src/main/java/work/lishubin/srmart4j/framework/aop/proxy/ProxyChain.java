package work.lishubin.srmart4j.framework.aop.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lishubin
 */
public class ProxyChain {


    private Class targetClass;
    private Object targetClassObject;
    private Method targetMethod;
    private MethodProxy methodProxy;
    private Object[] methodArgs;
    private List<Proxy> proxyList;

    private Integer proxyListIndex = 0;

    private ProxyChain(Class targetClass, Object targetClassObject, Method targetMethod, MethodProxy methodProxy, Object[] methodArgs,List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetClassObject = targetClassObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodArgs = methodArgs;
        this.proxyList = proxyList;
    }

    public static ProxyChain getInstance(
            Class targetClass,
            Object targetClassObject,
            Method targetMethod,
            MethodProxy methodProxy,
            Object[] methodArgs,
            List<Proxy> proxyList
            ) {
        return new ProxyChain(targetClass,targetClassObject,targetMethod,methodProxy,methodArgs,proxyList);
    }


    public <T> T doProxyChain() throws Throwable {
        Object methodResult;

        // 如果proxyList中还有Proxy对象
        if (proxyListIndex < proxyList.size()){
            // 调用代理类的切入代理方法
            methodResult = proxyList.get(proxyListIndex++).doProxy(this);
        } else {
            // 不存在则调用本身的业务方法
            methodResult =  methodProxy.invokeSuper(targetClassObject, methodArgs);
        }
        return (T) methodResult;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodArgs() {
        return methodArgs;
    }
}
