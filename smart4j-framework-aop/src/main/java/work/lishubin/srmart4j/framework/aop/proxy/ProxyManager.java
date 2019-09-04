package work.lishubin.srmart4j.framework.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class ProxyManager {

@SuppressWarnings("unchecked")
    public static <T> T getProxyWithAspect(
            final Class<?> proxiedClass,
            final List<Proxy> proxyList){
        return (T) Enhancer.create(proxiedClass, (MethodInterceptor) (targetClassObject, targetMethod, methodArgs, methodProxy) -> ProxyChain.getInstance(proxiedClass, targetClassObject, targetMethod, methodProxy, methodArgs, proxyList).doProxyChain());

    }

}
