package work.lishubin.srmart4j.framework.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * 生产我们需要的切入代理类的实体对象，且该实体对象有个方法
 * 拦截器当调用方法时会去调用ProxyChain的代理链，从而依次执行切入代理方法。
 * @author lishubin
 */
public class ProxyManager {

    @SuppressWarnings("unchecked")
    public static <T> T getProxyWithAspect(
            final Class<?> proxiedClass,
            final List<Proxy> proxyList){
        return (T) Enhancer.create(proxiedClass,
                (MethodInterceptor) (targetClassObject, targetMethod, methodArgs, methodProxy)
                        ->
                        ProxyChain.getInstance(
                                proxiedClass,
                                targetClassObject,
                                targetMethod,
                                methodProxy,
                                methodArgs,
                                proxyList).doProxyChain());

    }

}
