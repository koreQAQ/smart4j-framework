package work.lishubin.smart4j.framework.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记注解，横切当有这个注解的类，在控制台输出这个类方法每个方法的执行时间
 *
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/24 10:39
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodTime {
}
