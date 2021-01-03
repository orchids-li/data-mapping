package cn.ting.er.datamapping.definition;

import cn.ting.er.datamapping.text.Formatter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public interface Property<T, F> {
    F get(T source);
    void set(T source, F val);
    String getName();
    default String getGroup() {return null;}
    default boolean hasGroup() {
        return StringUtils.isNotEmpty(getGroup());
    }
    int getOrder();
    Class<F> getType();
    Formatter<F> getFormatter();
    default boolean isTypeOf(Class type) {
        return this.getType().isAssignableFrom(type);
    }
}
