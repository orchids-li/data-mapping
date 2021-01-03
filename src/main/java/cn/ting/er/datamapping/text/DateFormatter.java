package cn.ting.er.datamapping.text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class DateFormatter implements Formatter<Date> {

    private String pattern;

    public DateFormatter() {
        this("yyyy-MM-dd");
    }

    public DateFormatter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Date parse(String text) throws ParseException {
        return new SimpleDateFormat(this.pattern).parse(text);
    }

    @Override
    public String format(Date data) {
        return new SimpleDateFormat(this.pattern).format(data);
    }

}
