package cn.ting.er.datamapping.definition;

import cn.ting.er.datamapping.text.Formatter;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public abstract class AbstractProperty<T, F> implements Property<T, F> {
    private String name;
    private String group;
    private int order;
    private Class<F> type;
    private Formatter<F> formatter;

    @Override
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public Class<F> getType() {
        return type;
    }

    public void setType(Class<F> type) {
        this.type = type;
    }

    @Override
    public Formatter<F> getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter<F> formatter) {
        this.formatter = formatter;
    }

    public boolean isTypeOf(Class type) {
        return this.getType().isAssignableFrom(type);
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", order=" + order +
                '}';
    }
}
