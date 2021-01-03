package cn.ting.er.datamapping.provider.excel.xlsx.handler;

import cn.ting.er.datamapping.listener.ParseListener;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class SheetContentListener implements SheetHandler.SheetContentsHandler {
    private ParseListener listener;
    private List<String> currentRow;
    private int currentRowIndex;

    public SheetContentListener(ParseListener listener) {
        this.listener = listener;
    }

    @Override
    public void startRow(int rowNum) {
        this.currentRow = new ArrayList<>();
        this.currentRowIndex = rowNum;
    }

    @Override
    public void endRow(int rowNum) {
        listener.row(currentRow, rowNum);
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        int index = new CellReference(cellReference).getCol();
        int expectIndex = currentRow.size();
        while (expectIndex++ < index) {
            currentRow.add(null);
        }
        currentRow.add(formattedValue);
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {

    }
}
