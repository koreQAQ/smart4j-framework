package work.lishubin.smart4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;

/**
 * @author 李树彬
 * @date 2019/8/30  0:18
 */

public class ClassUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtils.class);

    //todo

    private static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    private static Class<?> loadClass(String className,Boolean isInitialized){

        Class<?> cls = null;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
            LOGGER.info("load {} class", className);
        } catch (ClassNotFoundException e) {
            LOGGER.error("the class not found,Pls check the package",e);
        }

        return cls;

    }

    public static Set<Class<?>> getAllClassSetByPackage(String packageName){

        // 需要把类集合向下传递
        Set<Class<?>> classSet = new HashSet<>();

        try {
            //获取项目包所在位置的URL
            Enumeration<URL> urlEnumeration = getClassLoader().getResources(packageName.replace(".","/"));
            //遍历下面的所有链接
            while (urlEnumeration.hasMoreElements()){

                // 获取其中一个连接
                URL url = urlEnumeration.nextElement();

                // 检查需要访问的协议
                String protocol = url.getProtocol();
                String packagePath = url.getPath();

                // 如果协议是普通文件类型，那么
                if ("file".equals(protocol)){
                    //去除空格转义
                    packagePath.replaceAll("%20", " ");

                    addClass(classSet,packagePath,packageName);
                }
                // 如果协议是jar包类型
                if ("jar".equals(protocol)){

                    // 建立起对Jar的连接
                    JarURLConnection jarUrlConnection = (JarURLConnection) url.openConnection();

                    if (jarUrlConnection!=null){
                        
                        // 打开jar文件，获取其中所有的jar
                        Enumeration<JarEntry> jarEntryEnumeration = jarUrlConnection.getJarFile().entries();
    
                        // 遍历jar包中所有的文件
                        while (jarEntryEnumeration.hasMoreElements()){
                            
                            JarEntry jarEntry = jarEntryEnumeration.nextElement();
                            String jarEntryName = jarEntry.getName();
    
                            //将`/`替换为`.` 并去除.class后缀
                            jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/",".");
                            doAddClass(classSet,jarEntryName);
                        }
                    }

                }

            }

        } catch (IOException e) {
            LOGGER.error("find class error Pls check getAllClassSetByPackage function",e);
        }

        return classSet;

    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        //去除测试类
        String testClassTail = "Test";


        if (!(className.endsWith(testClassTail))){
            Class<?> loadClass = loadClass(className, false);
            classSet.add(loadClass);
        }
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {

        File[] files = new File(packagePath).listFiles(file -> {

            //只选择.class文件 | 目录文件向下递归过滤
            return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();

        });
        
        //遍历过滤后的文件
        if (files!=null && files.length!=0){

            for (File file : files) {
                String fileName = file.getName();
                //如果是文件类型
                if (file.isFile()){
                    // 获取.class文件,去除.class 并加载这个class
                    if (StringUtils.isNotEmpty(packageName)){
                        String className = String.format(
                                "%s.%s",
                                packageName,
                                fileName.substring(0,fileName.lastIndexOf(".")));
                        doAddClass(classSet,className);
                    }
                }

                // 如果是目录类型
                else {

                    String subPackagePath = fileName;

                    if (StringUtils.isNotEmpty(packagePath)){
                        subPackagePath = String.format("%s/%s",packagePath,subPackagePath);
                    }

                    String subPackageName = fileName;
                    if (StringUtils.isNotEmpty(packageName)){
                        subPackageName = String.format("%s.%s",packageName,subPackageName);
                    }
                    addClass(classSet,subPackagePath,subPackageName);
                }
            }
        }

    }
}


