package cn.ting.er.datamapping.provider.excel.xlsx.handler;

import cn.ting.er.datamapping.definition.*;
import cn.ting.er.datamapping.exception.MappingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class DefaultSheetContentsHandler<T> implements XSSFSheetXMLHandler.SheetContentsHandler {
    private Mapping<T> mapping;
    private Row row;
    private boolean rowHasData;
    private int rowIndex;
    private int cellIndex;

    public DefaultSheetContentsHandler(Mapping<T> mapping) {
        this.mapping = mapping;
    }

    @Override
    public void startRow(int i) {
        int limit = 100000;
        if (i >= limit) {
            throw new MappingException(String.format("excel数据最多%s", limit));
        }
        this.rowIndex = i;
        this.rowHasData = false;
        this.cellIndex = 0;
        row = new DefaultRow(rowIndex);
    }

    @Override
    public void endRow(int i) {
        /*if (this.isDataRow()) {
            if (this.rowHasData) {
                //listener
            }
        }*/
    }

    @Override
    public void cell(String cellReference, String value, XSSFComment xssfComment) {
        int index = cellIndex++;
        this.rowHasData = this.rowHasData || StringUtils.isNotEmpty(value);
//        String key = obtainKey(cellReference);
        Cell cell = new StringCell(value, index, this.row);
        row.getCells().add(cell);
    }

    @Override
    public void headerFooter(String s, boolean b, String s1) {

    }

    /*private boolean isHeadRow() {
        return this.headIndex == this.rowIndex;
    }

    private boolean isDataRow() {
        return this.rowIndex > this.headIndex;
    }*/

    /**
     * 列表转列序号 A1 -> 0 , C1 -> 2
     * @param cellReference 列名，如A1，C1
     * @return index
     */
    private int obtainIndex(String cellReference) {
        int index = -1;
        int start = 'A';
        int length = cellReference.length();
        for (int i = 0; i < length; i++) {
            char c = cellReference.charAt(i);
            if (Character.isDigit(c)) {
                break;// 数字则跳出
            }
            index = (index + 1) * 26 + (int)c - start;
        }
        return index;
    }

}
