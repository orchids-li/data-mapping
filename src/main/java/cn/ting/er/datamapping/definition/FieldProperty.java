package cn.ting.er.datamapping.definition;

import cn.ting.er.datamapping.annotation.Column;
import cn.ting.er.datamapping.exception.MappingException;
import cn.ting.er.datamapping.text.Formatter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class FieldProperty<T, F> extends AbstractProperty<T, F> {
    private Field field;
    private Field parent;

    public FieldProperty(Field field, Field parent, String group) {
        this.field = field;
        this.parent = parent;
        field.setAccessible(true);
        if (parent != null) {
            parent.setAccessible(true);
        }
        Column annotation = field.getAnnotation(Column.class);
        this.setName(annotation.name());
        this.setOrder(annotation.order());
        this.setGroup(group);
        this.setType((Class<F>) field.getGenericType());
        if (!Formatter.class.equals(annotation.formatter())) {
            try {
                if (StringUtils.isEmpty(annotation.pattern())) {
                    this.setFormatter(annotation.formatter().newInstance());
                } else {
                    this.setFormatter(annotation.formatter().getConstructor(String.class).newInstance(annotation.pattern()));
                }
            } catch (Exception e) {
                throw new MappingException(e);
            }
        }

    }

    @Override
    public F get(T source) {
        if (parent == null) {
            return (F) get(field, source);
        }
        return (F) Optional.ofNullable(get(parent, source)).map(v -> get(field, v)).orElse(null);
    }

    @Override
    public void set(T source, F val) {
        if (parent == null) {
            set(field, source, val);
            return;
        }
        set(field,Optional.ofNullable(get(parent, source)).orElseGet(() -> {
            try {
                Object instance = parent.getType().newInstance();
                set(parent, source, instance);
                return instance;
            } catch (Exception e) {
                throw new MappingException(e);
            }
        }), val);
    }

    private Object get(Field field, Object source) {
        try {
            return field.get(source);
        } catch (IllegalAccessException e) {
            throw new MappingException(e);
        }
    }

    private void set(Field field, Object source, Object value) {
        try {
            field.set(source, value);
        } catch (IllegalAccessException e) {
            throw new MappingException(e);
        }
    }
}
