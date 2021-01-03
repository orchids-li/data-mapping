package cn.ting.er.datamapping.text;

import java.text.ParseException;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class IntegerFormatter implements Formatter<Integer> {
    @Override
    public Integer parse(String text) throws ParseException {
        return Integer.valueOf(text);
    }

    @Override
    public String format(Integer data) {
        if (data == null) {
            return null;
        }
        return data.toString();
    }
}
