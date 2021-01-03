package cn.ting.er.datamapping.provider.excel.enums;

import cn.ting.er.datamapping.exception.MappingException;
import org.apache.poi.poifs.filesystem.FileMagic;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public enum ExcelType {
    XLS("xls"), XLSX("xlsx");
    private String extension;

    ExcelType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static ExcelType valueOf(InputStream stream, String filename) {
        if (stream.markSupported()) {
            try {
                FileMagic magic = FileMagic.valueOf(stream);
                return valueOf(magic);
            } catch (IOException e) {
                throw new MappingException(e.getMessage(), e);
            }
        }
        return valueOfFilename(filename);
    }

    public static ExcelType valueOfFilename(String filename) {
        String name = filename.toLowerCase();
        if (name.endsWith(XLSX.extension)) {
            return XLSX;
        } else if (name.endsWith(XLS.extension)) {
            return XLS;
        }
        return null;
    }

    public static ExcelType valueOf(FileMagic magic) {
        if (magic == FileMagic.OOXML) {
            return XLSX;
        } else if (magic == FileMagic.OLE2) {
            return XLS;
        }
        return null;
    }
}
