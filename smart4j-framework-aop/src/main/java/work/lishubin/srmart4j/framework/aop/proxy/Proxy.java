package work.lishubin.srmart4j.framework.aop.proxy;

/**
 * @author lishubin
 */
public interface Proxy {

    /**
     * 切面类都必须要实现的切入代理规范
     * 代理类调用代理链
     * @param proxyChain 代理链
     * @return 返回调用结果
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
