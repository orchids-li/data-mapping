package cn.ting.er.datamapping.text;

import java.text.ParseException;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public interface Formatter<T> {
    T parse(String text) throws ParseException;
    String format(T data);
}
