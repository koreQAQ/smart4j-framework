package work.lishubin.srmart4j.framework.aop.helper;

import work.lishubin.smart4j.framework.bean.helper.HelperLoader;
import work.lishubin.smart4j.framework.bean.helper.IocHelper;

import java.util.ArrayList;
import java.util.List;

public class HelpLoaderWithAop {

    private static final List<Class<?>> CLASS_LIST = new ArrayList<>();

    static {
        List<Class<?>> sourceClassList = HelperLoader.getClassList();

        //AOp生成代理类，在IoC 注入之前完成
        for (Class<?> cls : sourceClassList) {
            if (cls.equals(IocHelper.class)){
                CLASS_LIST.add(AopHelper.class);
            }
            CLASS_LIST.add(cls);
        }
    }

    public static void init(){

        for (Class<?> cls : CLASS_LIST) {
            try {
                Class.forName(cls.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
