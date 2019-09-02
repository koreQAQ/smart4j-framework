package work.lishubin.smart4j.framework.utils;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author 李树彬
 * @date 2019/8/30  8:39
 */
public class ClassUtilsTest {

    @Test
    public void loadClass() {
    }

    @Test
    public void getAllClassSetByPackage() {

        Set<Class<?>> allClassSetByPackage = ClassUtils.getAllClassSetByPackage("work.lishubin.smart4j.framework.utils");

        for (Class<?> aClass : allClassSetByPackage) {
            System.out.println(aClass.getName());
        }

    }
}