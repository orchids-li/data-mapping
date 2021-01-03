package cn.ting.er.datamapping.listener;

import cn.ting.er.datamapping.context.MappingContext;
import cn.ting.er.datamapping.exception.ReadException;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class DefaultReadListener<T> implements ReadListener<T> {
    private static DefaultReadListener instance = new DefaultReadListener<>();

    public static <T> DefaultReadListener<T> getInstance() {
        return instance;
    }

    @Override
    public void onReadHeader(MappingContext context) {

    }

    @Override
    public void onReadRow(T t, MappingContext context) {

    }

    @Override
    public void onError(T t, MappingContext context, ReadException e) {
        throw e;
    }
}
