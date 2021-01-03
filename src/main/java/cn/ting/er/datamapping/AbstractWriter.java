package cn.ting.er.datamapping;

import cn.ting.er.datamapping.context.MappingContext;
import cn.ting.er.datamapping.definition.Mapping;
import cn.ting.er.datamapping.listener.DefaultWriterListener;
import cn.ting.er.datamapping.listener.WriterListener;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public abstract class AbstractWriter<T> extends SourceSetting implements Writer<T> {
    private MappingContext context;
    private OutputStream stream;
    private File file;
    private int offset = 0;

    public AbstractWriter(OutputStream stream, Setting setting) throws IOException {
        super(setting);
        this.stream = stream;
    }

    public AbstractWriter(File file, Setting setting) {
        super(setting);
        this.file = file;
    }

    public final void write(Iterable<T> data, Mapping<T> mapping) {
        write(data, mapping, DefaultWriterListener.getInstance());
    }
    @Override
    public final void write(Iterable<T> data, Mapping<T> mapping, WriterListener<T> listener) {
        for (T t : data) {
            write(t, mapping, listener);
        }
    }
    public final void write(T data, Mapping<T> mapping) {
        write(data, mapping, DefaultWriterListener.getInstance());
    }
    @Override
    public void write(T data, Mapping<T> mapping, WriterListener<T> listener) {
        writeInternal(data, mapping, listener);
        this.offset ++;
    }

    protected abstract void writeInternal(T data, Mapping<T> mapping, WriterListener<T> listener);

    @Override
    public final void write(List<String> row, WriterListener<T> listener) {
        writeInternal(row, listener);
        this.offset ++;
    }

    protected abstract void writeInternal(List<String> row, WriterListener<T> listener);

    @Override
    public void close() throws IOException {
        if (stream != null) {
            stream.close();
        }
    }

    protected OutputStream getStream() {
        return stream;
    }

    public File getFile() {
        return file;
    }

    public MappingContext getContext() {
        return context;
    }

    protected int getOffset() {
        return offset;
    }
}
