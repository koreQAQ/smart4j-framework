# smart4j-framework AOP Module

AOP 即面向切面编程，即在不改变既有代码的基础上动态地增加一些新功能：性能检测，日志分析等。
其主要实现方式为动态代理模式。下面将模仿书中的代码，为smart4框架新增AOP的特性

## 1. 需求分析
需要框架完成
- 切面类的注解，标识这是一个切面类，并切面类中含有对应的切入点类注解(即代理含有哪些注解的类）。
- 提供了一个可以扩展的模板切面类，其中内置了钩子函数，只需要自己的切面类继承模板切面类并重
写对应的函数即可。
- 一个类可能存在多个切面，需要一个切面代理链，来链式调用多个切面的方法，之后在调用自己的业
务方法。
- 不再是将对应类的Bean注入容器中，而是切面代理类的Bean注入到容器中。
- 切面代理类的对象采用CGLib的Enhancer创建，在方法拦截器方面，进行链式方法拦截，返回切面代理
类对象

### 1.2 实现思路
 

## 2. 编码实现
### 2.1 切面类注解
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * Aspect注解表明切面类，并切面类中含有对应的切入点类注解(即代理含有哪些注解的类）
     * @return 含有对应的切入点类注解
     */
    Class<? extends Annotation> value();

}
```
### 2.2 代理接口
所有的切入类都需要实现这一接口规范。
```java
public interface Proxy {

    /**
     * 切面类都必须要实现的切入代理规范
     * 代理类调用代理链
     * @param proxyChain 代理链
     * @return 返回调用结果
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
```
### 2.3 代理调用链
代理调用链是为了解决一个类被多个切面代理的问题，将所有代理对象放入一个ProxyList中从而按
顺序依次调用对应的代理方法。
```java
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
```
## 2.4 生产切入代理类Bean的ProxyManager类
我们需要一个ProxyManager为我们生产我们需要的切入代理类的实体对象，且该实体对象有个方法
拦截器当调用方法时会去调用ProxyChain的代理链，从而依次执行切入代理方法。
```java
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
```


## 2.5 统一的切入代理模板
实现Proxy接口，采用钩子函数的方式，使其他扩展了该类的接口重写钩子函数接口。
```java
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

```
## 2.6 AopHelper
AopHelper 负责加载整个模块的Aop框架。
在实现AopHelper之前，需要
- 在BeanHelper中添加setBean(Class<?> cls,Object bean); 为了将代理对象放入BEAN_MAP中。
- 在ClassHelper中添加
    - getClassSetBySuperClass(Class<?> superClass); 得到扩展AspectProxy类的集合
    - getClassSetWithAnnotation(Class<? extends Annotation > annotation) ;得到带有@Aspect的注解的类
    
AopHelper有三个静态方法：
- getTargetClass(Aspect aspect)  
  getTargetClass 主要完成
    - 根据@Aspect注解中指明的切入点类注解，调用ClassHelper#getClassSetWithAnnotation
      获取被切入代理类的集合
- getProxyMap
  getProxyMap 是为了得到 每一个切入代理类 横切了哪些类(集合)
    - 首先会获取扩展了AspectProxy类以及其存在@Aspect注解 才可以作为切入代理类
    - 根据获得的切入代理类集合，遍历这个集合。对于每一个切入代理类，取出其@Aspect注解中的
      切入点注解类，获取带有切入点注解类的类集合。（调用getTargetClass方法）
    - 建立起 每一个切入代理类与获取带有切入点注解类的类集合的映射关系。
- getTargetMap（Map<Class proxyClass,Set<Class> proxiedClassSet>）
  getTargetMap 是为了得到 每一个被横切代理的类与横切他们的代理类集合 之间的映射关系
  - 首先，遍历getProxy得到的切入代理类与获取带有切入点注解类的类集合的映射关系
  - 在每一个带有切入点注解类的类集合进行遍历
  - 将每一个带有切入点注解类的类 放入key,value为对应的proxyList(放入Map的key-切入代理类)
 
最后AopHelper通过static静态代码块来调用ProxyManager，将被横切代理类与代理类实体将入BEAN_MAP。

```java
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
```
 
    