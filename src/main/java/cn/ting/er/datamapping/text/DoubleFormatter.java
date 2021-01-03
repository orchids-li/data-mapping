package cn.ting.er.datamapping.text;

import java.text.ParseException;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class DoubleFormatter implements Formatter<Double> {
    @Override
    public Double parse(String text) throws ParseException {
        return Double.valueOf(text);
    }

    @Override
    public String format(Double data) {
        if (data == null) {
            return null;
        }
        return data.toString();
    }
}
