package work.lishubin.smart4j.framework.aop.helper;

import work.lishubin.smart4j.framework.bean.helper.HelperLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lishubin
 */
public class HelpLoaderWithAop {

    private static final List<Class<?>> CLASS_LIST = new ArrayList<>();

    static {
        List<Class<?>> sourceClassList = HelperLoader.getClassList();

        //AOp生成代理类，在IoC 注入之前完成
        // 静态代码块会在调用静态方式时执行
        CLASS_LIST.add(AopHelper.class);
        for (Class<?> cls : sourceClassList) {
            // 这时AOP会执行静态方法，生成对应的代理类对象重新注入BeanMap
            CLASS_LIST.add(cls);

        }
    }

    public static void init() {
        HelperLoader.init();
        //出发AopHelper的static代码块
        AopHelper.init();
    }

}
