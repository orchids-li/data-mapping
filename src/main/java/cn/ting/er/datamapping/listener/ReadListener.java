package cn.ting.er.datamapping.listener;

import cn.ting.er.datamapping.context.MappingContext;
import cn.ting.er.datamapping.exception.ReadException;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public interface ReadListener<T> {
    void onReadHeader(MappingContext context);
    void onReadRow(T t, MappingContext context);
    void onError(T t, MappingContext context, ReadException e);
}
