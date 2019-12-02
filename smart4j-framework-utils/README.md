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

- ClassUtils
    1. 获取类加载器 private
    2. 根据类名加载对应的类 private
    3. 根据包名加载对应包名下的所有类  

ClassUtils的处理逻辑为：
**getAllClassSetByPackage(String packageName)**
1. targetSet  
2. 首先根据packageName和getClassLoader().getResources得到对应项目包下的所有URL（所有可以访问的对象）
3. 遍历包下所有可以访问的对象
	3.1 获取其中一个访问对象的访问方式：访问协议与访问路径(protocol,path)    
	3.2 判断是否是文件类型还是jar包类型  
		3.2.1 文件类型 packagePath由Url得到,转到addClass方式处理    
		3.2.2 jar包类型，先建立对Jar包的访问连接，url.openConnection() 强转为JarURLConnection  
		    遍历jar中的所有对象  
		    格式化对象：对jar包中的每个对象将Name中的`/`替换`.` 并取出.class后缀  
		    将格式化后的对象转到doAddClass方法  
		    
		   		      
**addClass(classSet,packagePath,fileName)**  
1. 根据packagePath得到一个File对象，通过这个File对象列出其下的所有文件File[]
其中过滤条件为 1. 文件 并且是文件名是以class结尾  2. 目录  
2. 对于过滤后得到的File[]
获取文件名，判断文件类型：  
2.1. 文件  
如果是文件，文件名FileName取出后缀.class 并将其与packageName拼接得到className
调用doAddClass方法  
2.2 目录    
2.2.1 得到下级目录名称即fileName ->subPackagePath    
2.2.2 拼接全目录路径  packagePath+ SubPackagePath 作为传递给addClass的packagePath  
2.2.3 将得到的全路径 packagePath 中的`/`替换为`.` 作为packageName  
2.2.4 将上述packagePath和packageName传递给addClass 递归调用
**doAddClass(classSet,className)**
1.判断是否为Test测试类
2.对非测试类，调用loadClass方法，加载类，并将加载的类放入classSet