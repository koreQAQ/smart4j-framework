## spring-framework-utils  
spring-framework-utils有如下类：
- ConfigConstant 存放了配置文件目录
    - BeanConstant 存放需要扫描注解的相关目录
    - MvcConstant 存放MVC模块需要的视图后缀，静态资源文件目录
    - DataConstant 存放对应Data模块需要的JDBC连接相关配置

- PropUtils   
 1. 该工具类主要是实现了读取对应Properties文件
 2. 根据给定的Properties和key获取对应的value
 3. 根据给定的Properties和key，defaultValue获取对应的value
    - 根据key获取value，如果value不存在则返回对应的defaultValue，并设置
