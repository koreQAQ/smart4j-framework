# smart4j-framework
一款轻量级JavaWeb框架

## 项目背景
在学会使用Spring之后，会搭建个人博客与小型项目。总觉得哪里好像缺少了点什么。
在看了《从零开始写Web框架》之后，决心自己也要实现一个自己的框架，有了框架的经验，再去了解Spring，SpringBoot以及
Mybatis等原理，就会得心应手。

## 项目结构
- webmvc 主要实现以下功能
```java
@Controller
public class UserController{

    @Inject
    UserService userService;

    @Action("get:/user/list")
    public View userList(){
      return new View("user-list.jsp");
    }

    @Action("post:/user/create")
    public Data userCreate(){
        return new Data("");
    }

}

```

## 主要功能模块
- Bean 模块 [smart4j-framework-bean](./smart4j-framework-bean/README.md)
- AOP 模块 [smart4j-framework-aop](./smart4j-framework-aop/README.md)
- MVC 模块 [smart4j-framework-mvc](./smart4j-framework-webmvc/README.md)
- Data 模块 [smart4j-framework-data](./smart4j-framework-data/README.md)
## 功能依赖模块
- Utils 模块

## 项目开发步骤
### 1.Utils 模块
1.1 ConfigConstant及其子类XXConfigConstant 定义了模块的配置信息
1.2 PropUtils 加载配置文件
1.3 AbstractConfigHelper及其子类XXConfigHelper 根据模块来获取对应的配置文件信息
1.4 ClassUtils 根据包名将对应包下的类加载
1.5 AbstractClassHelper 返回base_package下的所有类 


### 2.Bean模块
2.1 子类BeanClassHelper返回所有带有@Bean注解的类
2.1 定义依赖注入的注解@Inject,@Bean 
2.2 创建反射工具类 根据类名得到对象，以及根据类名创建对象与设置对象属性
2.3 BeanHelper 负责维护IoC容器Map<Class<?>,Object>
2.4 实现依赖注入
    实现步骤：
    遍历所有的包下的类，当存在@Inject注解时，从BeanHelper中根据类名或者实现类来进行注入
    但接口类型需要到MVC层去实现注入
    

### 3.MVC模块

MVC需要加载对应的@Service和@Controller注解加入bean

将带有@Service注解的类所实现的接口作为key，实现类为value， 放入Bean_Map
将ServiceClassSet中的实现类遍历，
得到每一个对应的接口列表，将每一个接口类，与这个对象形成对应的映射关系，放入Bean_Map中


关键之处在于把Mvc的Service Controller 得到的Bean_Map 调用 Bean的启动类

### 4.Data模块
### 5.AOP模块