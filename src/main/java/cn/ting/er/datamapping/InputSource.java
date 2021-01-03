package cn.ting.er.datamapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class InputSource implements Source {
    private String name;
    private InputStream stream;

    public InputSource(InputStream stream) {
        this.stream = stream;
    }

    public InputSource(String name, InputStream stream) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return stream;
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }
}
