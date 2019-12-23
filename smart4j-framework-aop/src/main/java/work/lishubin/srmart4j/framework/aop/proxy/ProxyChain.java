package work.lishubin.srmart4j.framework.aop.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * ProxyChain维护一个ProxyList来完成每一个Proxy的调用
 *
 * @author lishubin
 */
public class ProxyChain {


    private final Class<?> targetClass;
    private final Object targetClassObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodArgs;

    private Integer proxyListIndex = 0;
    private List<Proxy> proxyList;

    private ProxyChain(Class<?> targetClass, Object targetClassObject, Method targetMethod, MethodProxy methodProxy, Object[] methodArgs, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetClassObject = targetClassObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodArgs = methodArgs;
        this.proxyList = proxyList;
    }

    public static ProxyChain getInstance(
            Class<?> targetClass,
            Object targetClassObject,
            Method targetMethod,
            MethodProxy methodProxy,
            Object[] methodArgs,
            List<Proxy> proxyList
    ) {
        return new ProxyChain(targetClass, targetClassObject, targetMethod, methodProxy, methodArgs, proxyList);
    }

    /**
     * 依次调用在ProxyList中的Proxy对象的方法，如果proxyList遍历完成之后，则再次调用其本身的方法
     *
     * @param <T> 方法返回值
     * @return 方法返回值
     * @throws Throwable
     */
    public <T> T doProxyChain() throws Throwable {

        Object methodResult;

        // 如果proxyList中还有Proxy对象
        if (proxyListIndex < proxyList.size()) {
            // 调用代理类的切入代理方法
            methodResult = proxyList.get(proxyListIndex++).doProxy(this);
        } else {
            // 不存在则调用本身的业务方法
            methodResult = methodProxy.invokeSuper(targetClassObject, methodArgs);
        }
        return (T) methodResult;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodArgs() {
        return methodArgs;
    }
}
