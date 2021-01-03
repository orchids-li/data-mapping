package cn.ting.er.datamapping.provider.excel.xlsx;

import cn.ting.er.datamapping.listener.ParseListener;
import cn.ting.er.datamapping.provider.excel.AbstractExcelParser;
import cn.ting.er.datamapping.provider.excel.ExcelSetting;
import cn.ting.er.datamapping.provider.excel.xlsx.handler.SheetContentListener;
import cn.ting.er.datamapping.provider.excel.xlsx.handler.SheetHandler;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class XLSXParser extends AbstractExcelParser {
    public XLSXParser(ExcelSetting setting) {
        super(setting);
    }

    @Override
    public void parse(InputStream stream, ParseListener listener) throws IOException {
        try {
            parse(OPCPackage.open(stream), listener);
        } catch (InvalidFormatException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void parse(File file, ParseListener listener) throws IOException {
        try {
            parse(OPCPackage.open(file), listener);
        } catch (InvalidFormatException e) {
            throw new IOException(e);
        }
    }

    private void parse(OPCPackage opcPackage, ParseListener listener) throws IOException {
        try {
            ReadOnlySharedStringsTable stringsTable = new ReadOnlySharedStringsTable(opcPackage);
            XSSFReader reader = new XSSFReader(opcPackage);
            StylesTable stylesTable = reader.getStylesTable();
            XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator) reader.getSheetsData();
            SheetContentListener contentListener = new SheetContentListener(listener);
            int sheetIndex = 0;
            while (sheetIterator.hasNext()) {
                InputStream stream = sheetIterator.next();
                if (!listener.start(sheetIterator.getSheetName(), sheetIndex++)) {
                    continue;
                }
                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                try {
                    saxParserFactory.setNamespaceAware(true);
                    XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
                    ContentHandler contentHandler = new SheetHandler(getSetting(), stylesTable, stringsTable, contentListener, !getSetting().isOutputFormulaValue());
                    xmlReader.setContentHandler(contentHandler);
                    xmlReader.parse(new InputSource(stream));
                } catch (ParserConfigurationException e) {
                    IOUtils.closeQuietly(stream);
                }
                break;
            }
        } catch (SAXException | OpenXML4JException e) {
            throw new IOException(e.getMessage(), e);
        } finally {
            opcPackage.close();
        }
    }
}
