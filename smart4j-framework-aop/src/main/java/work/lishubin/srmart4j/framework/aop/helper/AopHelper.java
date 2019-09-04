package work.lishubin.srmart4j.framework.aop.helper;

import work.lishubin.smart4j.framework.bean.helper.BeanHelper;
import work.lishubin.smart4j.framework.bean.helper.ClassHelper;
import work.lishubin.srmart4j.framework.aop.annotation.Aspect;
import work.lishubin.srmart4j.framework.aop.aspect.AspectProxy;
import work.lishubin.srmart4j.framework.aop.proxy.Proxy;
import work.lishubin.srmart4j.framework.aop.proxy.ProxyManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author lishubin
 */
public class AopHelper {

    static {

        Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
        Map<Class<?>, List<Proxy>> targetMap = getTargetMap(proxyMap);

        // 产生真正链式执行切面的代理对象
        for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
            Class<?> proxiedClass = targetEntry.getKey();
            List<Proxy> proxyList = targetEntry.getValue();
            Object proxyWithAspect = ProxyManager.getProxyWithAspect(proxiedClass, proxyList);

            // 放入代理对象到容器中
            BeanHelper.putBean(proxiedClass,proxyWithAspect);

        }


    }


    private static Set<Class<?>> getTargetClass(Aspect aspect){

        Set<Class<?>> targetClassSet = new HashSet<>();

        // 1. 得到Aspect中的切点注解
        Class<? extends Annotation> cutPointAnnotation = aspect.value();

        // 2. 得到有切点注解的类集合
        Set<Class<?>> classSetWithAnnotation = ClassHelper.getClassSetWithAnnotation(cutPointAnnotation);

        // 3. 遍历含有切点注解的类集合
        for (Class<?> classWithAnnotation : classSetWithAnnotation) {

            // 如果这个类存在，且不含有Aspect注解
            if (!classWithAnnotation.isAnnotationPresent(aspect.getClass())){
                targetClassSet.add(classWithAnnotation);
            }

        }
        return classSetWithAnnotation;

    }

    /**
     * 建立起 代理类与被代理类集合之间的关系
     * @return  代理类与被代理类集合之间的关系
     */
    private static Map<Class<?>,Set<Class<?>>> createProxyMap(){

        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();


        // 1.找到所有扩展了AspectProxy且带有@Aspect注解的类

        Set<Class<?>> classSetByAspectProxy = ClassHelper.getClassSetBySuperClass(AspectProxy.class);
        for (Class<?> proxyClass : classSetByAspectProxy) {

            if (proxyClass!=null && proxyClass.isAnnotationPresent(Aspect.class)){

                Set<Class<?>> targetProxiedClassSet = getTargetClass(proxyClass.getAnnotation(Aspect.class));
                proxyMap.put(proxyClass, targetProxiedClassSet);

            }
        }
        return proxyMap;
    }

    private static Map<Class<?>, List<Proxy>> getTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap){

        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        try{
            for (Class<?> proxyClass : proxyMap.keySet()) {

                Proxy proxyInstance = (Proxy) proxyClass.getDeclaredConstructor().newInstance();

                Set<Class<?>> proxiedClassSet = proxyMap.get(proxyClass);

                for (Class<?> proxiedClass : proxiedClassSet) {

                    // 如果targetMap存在proxiedClass
                    if (targetMap.containsKey(proxiedClass)) {
                        targetMap.get(proxiedClass).add(proxyInstance);
                    }
                    else {
                        List<Proxy> proxyList = new ArrayList<>();
                        proxyList.add(proxyInstance);
                        targetMap.put(proxiedClass, proxyList);
                    }
                }
            }
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {

            e.printStackTrace();
        }

        return targetMap;
    }
}
