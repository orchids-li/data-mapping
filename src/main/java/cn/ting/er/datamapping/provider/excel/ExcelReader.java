package cn.ting.er.datamapping.provider.excel;

import cn.ting.er.datamapping.AbstractReader;
import cn.ting.er.datamapping.Source;
import cn.ting.er.datamapping.definition.Mapping;
import cn.ting.er.datamapping.exception.MappingException;
import cn.ting.er.datamapping.listener.MappingParseListener;
import cn.ting.er.datamapping.listener.ReadListener;
import cn.ting.er.datamapping.provider.excel.enums.ExcelType;
import cn.ting.er.datamapping.provider.excel.xls.XLSParser;
import cn.ting.er.datamapping.provider.excel.xlsx.XLSXParser;
import org.apache.poi.poifs.filesystem.FileMagic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class ExcelReader<T> extends AbstractReader<T> {
    private ExcelType type;

    public ExcelReader(InputStream stream) {
        super(stream, new ExcelSetting());
        try {
            this.type = ExcelType.valueOf(FileMagic.valueOf(getStream()));
            if (this.type == null) {
                throw new IOException();
            }
        } catch (IOException e) {
            throw new MappingException("Your InputStream is not excel");
        }
    }

    public ExcelReader(File file) {
        super(file, new ExcelSetting());
        this.type = ExcelType.valueOfFilename(file.getName());
        if (type == null) {
            throw new MappingException("Your File is not excel");
        }
    }

    @Override
    public void read(Mapping<T> mapping, ReadListener<T> listener) throws IOException {
        ExcelParser parser;
        ExcelSetting setting = (ExcelSetting) getSetting();
        if (ExcelType.XLSX == type) {
            parser = new XLSXParser(setting);
        } else {
            parser = new XLSParser(setting);
        }
        MappingParseListener<T> parseListener = new MappingParseListener<>(mapping, listener, getContext());
        if (isStreamMode()){
            parser.parse(getStream(), parseListener);
        } else {
            parser.parse(getFile(), parseListener);
        }

    }
}
