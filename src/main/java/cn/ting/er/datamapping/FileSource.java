package cn.ting.er.datamapping;

import cn.ting.er.datamapping.Source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class FileSource implements Source {
    private File file;
    private InputStream inputStream;
    private OutputStream outputStream;

    public FileSource(String file) {
        this.file = new File(file);
    }

    public FileSource(File file) {
        this.file = file;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public InputStream openInputStream() throws IOException {
        if (inputStream == null) {
            inputStream = Files.newInputStream(file.toPath());
        }
        return inputStream;
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        if (outputStream == null) {
            outputStream = Files.newOutputStream(file.toPath());
        }
        return outputStream;
    }

    @Override
    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }
}
