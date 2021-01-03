package cn.ting.er.datamapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public interface Source {
    String getName();
    InputStream openInputStream() throws IOException;
    OutputStream openOutputStream() throws IOException;
    void close() throws IOException;
}
