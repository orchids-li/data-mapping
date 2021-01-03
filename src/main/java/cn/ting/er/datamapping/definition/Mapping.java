package cn.ting.er.datamapping.definition;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class Mapping<T> {
    private String name;
    private Supplier<T> creator;
    private List<Property<T, ?>> properties;
    private LinkedHashMap<Integer, PropertyHandler<T, ?>> handlers;

    public LinkedHashMap<Integer, PropertyHandler<T, ?>> getHandlers() {
        LinkedHashMap<Integer, PropertyHandler<T, ?>> handlers = new LinkedHashMap<>();
        int index = 0;
        for (Property<T, ?> property : properties) {
            handlers.put(index++, new PropertyHandler<>(property));
        }
        return handlers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Supplier<T> getCreator() {
        return creator;
    }

    public void setCreator(Supplier<T> creator) {
        this.creator = creator;
    }

    public List<Property<T, ?>> getProperties() {
        return properties;
    }

    public void setProperties(List<Property<T, ?>> properties) {
        this.properties = properties;
    }

    public boolean isMultiHeader() {
        return getProperties().stream().anyMatch(Property::hasGroup);
    }
    @Override
    public String toString() {
        return "Mapping{" +
                "name='" + name + '\'' +
                ", properties=" + properties +
                '}';
    }
}
