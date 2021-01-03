package cn.ting.er.datamapping.annotation;

import java.lang.annotation.*;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Group {
    String value() default "";
}
