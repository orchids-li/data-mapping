package cn.ting.er.datamapping;

import cn.ting.er.datamapping.definition.Mapping;
import cn.ting.er.datamapping.listener.ReadListener;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public interface Reader<T> extends Closeable {
   void read(Mapping<T> mapping, ReadListener<T> listener) throws IOException;
}
