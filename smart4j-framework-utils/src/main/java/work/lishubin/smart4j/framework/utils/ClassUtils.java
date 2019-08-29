package work.lishubin.smart4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author 李树彬
 * @date 2019/8/30  0:18
 */

public class ClassUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtils.class);

    //todo

    private static ClassLoader getClassLoader(){
        return null;
    }

    public static void loadClass(String className,Boolean isInitialized){

        try {
            Class.forName(className,isInitialized,getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("the class not found,Pls check the package",e);
        }

    }

    public static Set<Class<?>> getAllClassSetByPackage(String packageName){
        return null;
    }
}


