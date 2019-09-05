package work.lishubin.srmart4j.framework.aop.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.lishubin.smart4j.framework.utils.DataBaseUtils;
import work.lishubin.srmart4j.framework.aop.annotation.Transactional;
import work.lishubin.srmart4j.framework.aop.proxy.Proxy;
import work.lishubin.srmart4j.framework.aop.proxy.ProxyChain;

import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * @author lishubin
 */
public class TransactionAspect implements Proxy {


    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionAspect.class);


    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result = null;

        Method targetMethod = proxyChain.getTargetMethod();
        // 判断是否存在@Transaction注解
        if (targetMethod.isAnnotationPresent(Transactional.class)) {

            try {
                DataBaseUtils.beginTransaction();
                result = proxyChain.doProxyChain();
                DataBaseUtils.closeConnection();
            } catch (SQLException e) {
                DataBaseUtils.rollbackTransaction();
                LOGGER.error("doProxy failure for SQLException", e);
            }
        }

        return result;
    }
}
