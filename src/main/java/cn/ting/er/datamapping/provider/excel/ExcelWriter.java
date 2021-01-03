package cn.ting.er.datamapping.provider.excel;

import cn.ting.er.datamapping.AbstractWriter;
import cn.ting.er.datamapping.Setting;
import cn.ting.er.datamapping.Source;
import cn.ting.er.datamapping.definition.*;
import cn.ting.er.datamapping.listener.WriterListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class ExcelWriter<T> extends AbstractWriter<T> {
    private Workbook workbook;
    private Sheet sheet;
    public ExcelWriter(File file) throws IOException {
        super(file, new ExcelSetting());
    }

    private Stream<Property<T, ?>> toPropsStream(LinkedHashMap<Integer, PropertyHandler<T, ?>> handlers) {
        return handlers.entrySet().stream().map(v -> v.getValue().getProperty());
    }

    private void init(Mapping<T> mapping, WriterListener<T> listener) {
        if (workbook == null) {
            Workbook workbook = new SXSSFWorkbook(1000);
            Sheet sheet = createSheet(mapping, workbook);
            this.workbook = workbook;
            this.sheet = sheet;
            LinkedHashMap<Integer, PropertyHandler<T, ?>> handlers = mapping.getHandlers();

            if (mapping.isMultiHeader()) {
                List<String> groups = new ArrayList<>(handlers.size());
                List<String> headers = new ArrayList<>(handlers.size());
                for (int i = 0; i < handlers.size(); i++) {
                    headers.add(null);
                    groups.add(null);
                }
                int headerIndex = 0;
                int colIndex = 0;
                for (Property<T, ?> property : mapping.getProperties()) {
                    if (StringUtils.isEmpty(property.getGroup())) {
                        groups.set(colIndex, property.getName());
                        sheet.addMergedRegion(new CellRangeAddress(headerIndex, headerIndex + 1, colIndex,colIndex));
                    } else {
                        headers.set(colIndex, property.getName());
                        if (colIndex == 0 || !property.getGroup().equals(mapping.getProperties().get(colIndex - 1).getGroup())) {
                            int len = 0,j = colIndex + 1;
                            while (j < mapping.getProperties().size()) {
                                if (property.getGroup().equals(mapping.getProperties().get(j).getGroup())) {
                                    len++;
                                }
                                j++;
                            }
                            groups.set(colIndex, property.getGroup());
                            sheet.addMergedRegion(new CellRangeAddress(headerIndex, headerIndex, colIndex, colIndex + len));
                        }
                    }
                    colIndex ++;
                }
                write(groups, listener);
                writeInternal(headers, listener);
            } else {
                List<String> header = new ArrayList<>();
                handlers.forEach((i, prop) -> {
                    header.add(prop.getProperty().getName());
                });
                writeInternal(header, listener);
            }
        }
    }

    private Sheet createSheet(Mapping<?> mapping, Workbook workbook) {
        if (StringUtils.isNotEmpty(mapping.getName())) {
            return workbook.createSheet(mapping.getName());
        } else {
            return workbook.createSheet();
        }
    }

    @Override
    protected void writeInternal(List<String> row, WriterListener<T> listener) {
        Row poiRow = sheet.createRow(getOffset());
        int index = 0;
        for (String cellValue : row) {
            Cell cell = poiRow.createCell(index++);
            cell.setCellValue(cellValue);
        }
    }

    @Override
    protected void writeInternal(T data, Mapping<T> mapping, WriterListener<T> listener) {
        init(mapping, listener);
        Row row = sheet.createRow(getOffset() + 1);
        mapping.getHandlers().forEach((index, handler) -> {
            Cell cell = row.createCell(index);
            String value = handler.getPropertyStringValue(data, getSetting());
            cell.setCellValue(value);
        });
    }

    @Override
    public void close() throws IOException {
        try {
            if (workbook != null) {
                OutputStream stream = getStream();
                if (stream == null) {
                    stream = Files.newOutputStream(getFile().toPath());
                }
                workbook.write(stream);
                workbook.close();
            }
        } finally {
            super.close();
        }
    }
}
