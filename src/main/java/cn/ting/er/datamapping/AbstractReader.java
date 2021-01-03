package cn.ting.er.datamapping;

import cn.ting.er.datamapping.context.MappingContext;
import cn.ting.er.datamapping.definition.Mapping;
import cn.ting.er.datamapping.exception.MappingException;
import cn.ting.er.datamapping.listener.ListResultReadListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public abstract class AbstractReader<T> extends SourceSetting implements Reader<T> {
    private MappingContext context;
    private InputStream stream;
    private File file;

    public AbstractReader(InputStream stream, Setting setting) {
        super(setting);
        if (!stream.markSupported()) {
            stream = new PushbackInputStream(stream, 8);
        }
        this.stream = stream;
        context = new MappingContext(setting);
    }

    public AbstractReader(File file, Setting setting) {
        super(setting);
        this.file = file;
        context = new MappingContext(setting);
    }

    public final List<T> read(Mapping<T> mapping) throws IOException {
        ListResultReadListener<T> listener = ListResultReadListener.getInstance();
        read(mapping, listener);
        return listener.getResult();
    }

    public void reset() throws IOException {
        if (isStreamMode()) {
            this.stream.reset();
        }
    }

    public boolean resetSupported() {
        if (isStreamMode()) {
            return this.stream.markSupported();
        }
        return true;
    }

    @Override
    public void close() throws IOException {
        if (isStreamMode()) {
            this.stream.close();
        }
    }

    protected boolean isStreamMode() {
        return this.stream != null;
    }

    public InputStream getStream() {
        return stream;
    }

    public File getFile() {
        return file;
    }

    public MappingContext getContext() {
        return context;
    }
}
