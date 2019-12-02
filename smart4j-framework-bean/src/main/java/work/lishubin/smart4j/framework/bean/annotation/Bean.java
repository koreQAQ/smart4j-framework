package work.lishubin.smart4j.framework.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/2 16:45
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Bean {
}
