package cn.ting.er.datamapping.text;

import java.text.ParseException;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class LongFormatter implements Formatter<Long> {
    @Override
    public Long parse(String text) throws ParseException {
        return Long.valueOf(text);
    }

    @Override
    public String format(Long data) {
        if (data == null) {
            return null;
        }
        return data.toString();
    }
}
