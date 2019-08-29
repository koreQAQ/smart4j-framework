# smart4j-framework-webmvc
一款轻量级JavaWeb框架

## 项目结构
[webmvc](../smart4j-framework-webmvc/README.md) 主要实现以下功能
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
1.`@Controller`标识为控制器
2. `@Inject` 标识为依赖注入
3. `@Action` 将请求的方法与请求地址映射到`Controller`类中的具体方法

