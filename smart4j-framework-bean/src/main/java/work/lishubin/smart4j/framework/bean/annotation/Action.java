package work.lishubin.smart4j.framework.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 李树彬
 * @date 2019/8/30  12:12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    /**
     * 请求方法:请求路径
     * @return  请求方法:请求路径
     */
    String value();

}
