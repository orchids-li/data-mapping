package cn.ting.er.datamapping.definition;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public interface Cell<T> {
    T getValue();
    int getIndex();
    default String getStringValue() {
        T value = getValue();
        if (value instanceof String) {
            return (String) value;
        } else if (value != null) {
            return value.toString();
        }
        return null;
    }
    default Integer getIntegerValue() {
        T value = getValue();
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value != null) {
            return Integer.valueOf(getStringValue());
        }
        return null;
    }
    default Long getLongValue() {
        T value = getValue();
        if (value instanceof Long) {
            return (Long) value;
        } else if (value != null) {
            return Long.valueOf(getStringValue());
        }
        return null;
    }
    default Double getDoubleValue() {
        T value = getValue();
        if (value instanceof Double) {
            return (Double) value;
        } else if (value != null) {
            return Double.valueOf(getStringValue());
        }
        return null;
    }
}
