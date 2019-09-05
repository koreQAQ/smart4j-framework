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

## 相关模块
- AOP 模块 [smart4j-framework-aop](./smart4j-framework-aop/README.md)
- MVC 模块 [smart4j-framework-mvc](./smart4j-framework-webmvc/README.md)