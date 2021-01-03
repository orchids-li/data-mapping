package cn.ting.er.datamapping.definition;

import cn.ting.er.datamapping.Setting;
import cn.ting.er.datamapping.exception.ReadException;
import cn.ting.er.datamapping.text.Formatter;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class PropertyHandler<T, F> {
    private Property<T, F> property;

    public PropertyHandler(Property<T, F> property) {
        this.property = property;
    }

    public boolean matched() {
        return property != null;
    }

    public String getPropertyStringValue(T t, Setting setting) {
        F value = property.get(t);
        if (value != null) {
            Formatter formatter = getFormatter(setting);
            if (formatter != null) {
                return formatter.format(value);
            }
            if (value instanceof String) {
                return (String) value;
            }
            return String.valueOf(value);
        }
        return null;
    }

    private Formatter getFormatter(Setting setting) {
        Formatter formatter = property.getFormatter();
        if (formatter == null) {
            formatter = setting.getFormats().get(property.getType());
        }
        return formatter;
    }

    public boolean parseProperty(String cell, T t, Setting setting) throws ParseException {
        if (!matched()) {
            return false;
        }
        Property property = this.property;
        if (StringUtils.isNotEmpty(cell)) {
            Formatter formatter = getFormatter(setting);
            if (formatter != null) {
                Object parse = formatter.parse(cell);
                property.set(t, parse);
                return true;
            }
            if (String.class.equals(property.getType())) {
                property.set(t, cell);
                return true;
            }
            throw new ReadException("unSupport type " + property.getType().toString());
        }
        return false;
    }

    public Property<T, F> getProperty() {
        return property;
    }
}
