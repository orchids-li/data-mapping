package cn.ting.er.datamapping;

import cn.ting.er.datamapping.annotation.Data;
import cn.ting.er.datamapping.annotation.Column;
import cn.ting.er.datamapping.annotation.Group;
import cn.ting.er.datamapping.definition.FieldProperty;
import cn.ting.er.datamapping.definition.Mapping;
import cn.ting.er.datamapping.definition.Property;
import cn.ting.er.datamapping.exception.MappingException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class Mappings {
    public static <T> Mapping<T> from(Class<T> clz) {
        Data data = clz.getAnnotation(Data.class);
        Objects.requireNonNull(data, String.format("cannot find annotation %s from %s", Data.class.getName(), clz.getName()));
        Mapping<T> mapping = new Mapping<>();
        mapping.setName(data.name());
        mapping.setCreator(() -> {
            try {
                return clz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new MappingException(e);
            }
        });
        List<Class> classes = new ArrayList<>();
        Class c = clz;
        classes.add(c);
        while (c.getSuperclass() != null) {
            c = c.getSuperclass();
            if (!Object.class.equals(c)) {
                classes.add(c);
            }
        }
        Collections.reverse(classes);
        List properties = classes.stream().map(Mappings::obtainProperties).flatMap(Collection::stream).collect(Collectors.toList());
        if (properties.isEmpty()) {
            throw new MappingException("field is empty");
        }
        mapping.setProperties(properties);
        return mapping;
    }

    private static <T> List<Property<T, ?>> obtainProperties(Class clz) {
        List<Property<T, ?>> properties = new ArrayList<>();
        collectProperties(properties, clz, null, null);
        return properties.stream().sorted(Comparator.comparing(Property::getOrder)).collect(Collectors.toList());
    }

    private static <T> void collectProperties(List<Property<T, ?>> properties, Class clz, Field parent, String gName) {
        for (Field field : clz.getDeclaredFields()) {
            Group group = field.getAnnotation(Group.class);
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                if (group != null && StringUtils.isEmpty(gName)) {
                    properties.add(new FieldProperty<>(field, parent, group.value()));
                } else {
                    properties.add(new FieldProperty<>(field, parent, gName));
                }
            } else if (group != null && gName == null) {
                collectProperties(properties, field.getType(), field, group.value());
            }
        }
    }
}
