package cn.ting.er.datamapping;

import cn.ting.er.datamapping.definition.Mapping;
import cn.ting.er.datamapping.definition.Row;
import cn.ting.er.datamapping.listener.WriterListener;

import java.io.Closeable;
import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public interface Writer<T> extends Closeable {
    void write(Iterable<T> data, Mapping<T> mapping, WriterListener<T> listener);
    void write(T data, Mapping<T> mapping, WriterListener<T> listener);
    void write(List<String> row, WriterListener<T> listener);
}
