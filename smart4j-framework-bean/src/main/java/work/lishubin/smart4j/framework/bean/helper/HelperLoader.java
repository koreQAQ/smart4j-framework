package work.lishubin.smart4j.framework.bean.helper;



import java.util.ArrayList;
import java.util.List;

/**
 * @author 李树彬
 * @date 2019/8/31  22:32
 */
public class HelperLoader {

    private static final List<Class<?>> CLASS_LIST = new ArrayList<>();

    static {

        CLASS_LIST.add(ConfigHelper.class);
        CLASS_LIST.add(ClassHelper.class);
        CLASS_LIST.add(BeanHelper.class);
        CLASS_LIST.add(IocHelper.class);

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
