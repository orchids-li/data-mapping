package cn.ting.er.datamapping.provider.excel;

import cn.ting.er.datamapping.listener.ParseListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public interface ExcelParser {
    ExcelSetting getSetting();
    void setSetting(ExcelSetting setting);
    void parse(InputStream stream, ParseListener listener) throws IOException;
    default void parse(File file, ParseListener listener) throws IOException {
        try (InputStream stream = Files.newInputStream(file.toPath())){
            parse(stream, listener);
        }
    }
}
