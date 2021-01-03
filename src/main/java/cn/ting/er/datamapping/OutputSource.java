package cn.ting.er.datamapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class OutputSource implements Source {
    private String name;
    private OutputStream stream;

    public OutputSource(OutputStream stream) {
        this.stream = stream;
    }

    public OutputSource(String name, OutputStream stream) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public InputStream openInputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return stream;
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }
}
