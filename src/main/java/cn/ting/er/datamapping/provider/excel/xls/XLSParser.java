package cn.ting.er.datamapping.provider.excel.xls;

import cn.ting.er.datamapping.listener.ParseListener;
import cn.ting.er.datamapping.provider.excel.AbstractExcelParser;
import cn.ting.er.datamapping.provider.excel.ExcelParser;
import cn.ting.er.datamapping.provider.excel.ExcelSetting;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class XLSParser extends AbstractExcelParser {

    public XLSParser(ExcelSetting setting) {
        super(setting);
    }

    @Override
    public void parse(InputStream stream, ParseListener listener) throws IOException {
        HSSFContentListener contentListener = new HSSFContentListener(listener, getSetting());
        contentListener.setOutputFormulaValues(getSetting().isOutputFormulaValue());
        MissingRecordAwareHSSFListener missingRecordAwareHSSFListener = new MissingRecordAwareHSSFListener(contentListener);
        FormatTrackingHSSFListener formatTrackingHSSFListener = new FormatTrackingHSSFListener(missingRecordAwareHSSFListener);

        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();
        contentListener.setFormatListener(formatTrackingHSSFListener);
        if (contentListener.isOutputFormulaValues()) {
            request.addListenerForAllRecords(formatTrackingHSSFListener);
        } else {
            EventWorkbookBuilder.SheetRecordCollectingListener collectingListener = new EventWorkbookBuilder.SheetRecordCollectingListener(formatTrackingHSSFListener);
            request.addListenerForAllRecords(collectingListener);
            contentListener.setWorkbookBuildingListener(collectingListener);
        }
        factory.processWorkbookEvents(request, new POIFSFileSystem(stream));
    }
}
