package cn.ting.er.datamapping.annotation;

import cn.ting.er.datamapping.text.Formatter;

import java.lang.annotation.*;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Column {
    String name();
    String pattern() default "";
    Class<?extends Formatter> formatter() default Formatter.class;
    int order() default 0;
}
