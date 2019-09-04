package work.lishubin.srmart4j.framework.aop.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * Aspect注解表明切面类，切面类需要指定切点
     * @return 切点
     */
    Class<? extends Annotation> value();

}
