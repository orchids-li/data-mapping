package cn.ting.er.datamapping.listener;

import cn.ting.er.datamapping.Setting;
import cn.ting.er.datamapping.context.MappingContext;
import cn.ting.er.datamapping.definition.Mapping;
import cn.ting.er.datamapping.definition.Property;
import cn.ting.er.datamapping.definition.PropertyHandler;
import cn.ting.er.datamapping.exception.ReadException;
import cn.ting.er.datamapping.exception.TooManyResultException;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class MappingParseListener<T> implements ParseListener {
    private Mapping<T> mapping;
    private ReadListener<T> readListener;
    private Map<Integer, Optional<PropertyHandler<T, ?>>> handlers = new LinkedHashMap<>();
    private List<String> headerGroup;
    private MappingContext context;

    public MappingParseListener(Mapping<T> mapping, ReadListener<T> readListener, MappingContext context) {
        this.mapping = mapping;
        this.readListener = readListener;
        this.context = context;
    }

    @Override
    public boolean start(String name, int index) {
        if (StringUtils.isNotEmpty(mapping.getName())) {
            return mapping.getName().equals(name);
        }
        return index == 0;
    }

    @Override
    public void row(List<String> data, int rowIndex) {
        int headerIndex = 0;
        boolean isHeader = rowIndex == headerIndex;
        if (isHeader) {
            boolean isMultiHeaderRow = mapping.isMultiHeader();
            if (isMultiHeaderRow) {
                this.headerGroup = data;
            } else {
                handleHeader(data);
            }
            return;
        } else if (rowIndex == headerIndex + 1){
            if (this.headerGroup != null) {
                handleMultiHeader(this.headerGroup, data);
                this.headerGroup = null;
                return;
            }
        }
        int limit = 100000;
        if (rowIndex > limit) {
            throw new TooManyResultException(String.format("excel data more then %s row", limit));
        }
        handleData(data);
    }

    private void handleMultiHeader(List<String> headerGroup, List<String> row) {
        int column = 0;
        for (String title : row) {
            int index = column++;
            String group = safeGet(headerGroup, index);
            if (StringUtils.isEmpty(title)) {
                title = safeGet(headerGroup, index);
                group = null;
            } else if (StringUtils.isEmpty(group)) {
                group = headerGroup.stream().limit(index).filter(StringUtils::isNotEmpty).reduce((a, b) -> b).orElse(null);
            }

            if (StringUtils.isNotEmpty(group)) {
                String g = group;
                handlers.put(index, findMatchProperty(mapping.getProperties().stream().filter(p -> {
                    return g.equals(p.getGroup());
                }).collect(Collectors.toList()), title));
            } else {
                handlers.put(index, findMatchProperty(mapping.getProperties().stream().filter(v -> !v.hasGroup()).collect(Collectors.toList()), title));
            }
        }
    }

    private String safeGet(List<String> row, int index) {
        if (index > row.size() - 1) {
            return null;
        }
        return row.get(index);
    }

    private void handleData(List<String> row) {
        Setting setting = context.getSetting();
        context.reset();
        context.setCurrentMapping(mapping);
        context.setCurrentRow(row);
        RuntimeException error = null;
        T t = mapping.getCreator().get();
        boolean hasData = false;
        int index = 0;
        for (String cell : row) {
            Optional<PropertyHandler<T, ?>> handlerOptional = handlers.get(index++);
            if (handlerOptional != null && handlerOptional.isPresent()) {
                PropertyHandler<T, ?> handler = handlerOptional.get();
                try {
                    hasData = handler.parseProperty(cell, t, setting) || hasData;
                } catch (Exception e) {
                    context.setCurrentProperty(handler.getProperty());
                    context.setCurrentCell(cell);
                    readListener.onError(t, context, new ReadException(row, cell, e));
                    if (!setting.isContinueOnCellError()) {
                        break;
                    }
                }
            }

        }
        if (hasData) {
            readListener.onReadRow(t, context);
        }
    }

    private void handleHeader(List<String> row) {
        int column = 0;
        for (String cell : row) {
            int index = column++;
            handlers.put(index, findMatchProperty(mapping.getProperties(), cell));
        }
    }

    private Optional<PropertyHandler<T, ?>> findMatchProperty(List<Property<T, ?>> props, String cell) {
        Optional<Property<T, ?>> optional = props.stream().filter(v -> v.getName().equals(cell)).findFirst();
        if (optional.isPresent()) {
            Property<T, ?> property = optional.get();
            PropertyHandler<T, ?> handler = new PropertyHandler<>(property);
            return Optional.of(handler);
        }
        return Optional.empty();
    }
}
