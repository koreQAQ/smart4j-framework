package work.lishubin.srmart4j.framework.aop.proxy;

public interface Proxy {

    /**
     * 代理类调用代理链
     * @param proxyChain 代理链
     * @return 返回调用结果
     * @throws Exception
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
