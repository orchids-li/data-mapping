package cn.ting.er.datamapping;

import cn.ting.er.datamapping.text.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class Setting {
    private boolean continueOnCellError = true;
    private boolean ignoreEmptyRow = true;
    private boolean ignoreInvalidCell = true;
    private int readRowLimit = Integer.MAX_VALUE;
    private int headIndex;
    private Map<Class, Formatter> formats;
    public Setting() {
        formats = new HashMap<>();
        initFormats();
    }

    private void initFormats() {
        formats.put(Date.class, new DateFormatter());
        IntegerFormatter integerFormat = new IntegerFormatter();
        formats.put(Integer.class, integerFormat);
        formats.put(int.class, integerFormat);
        LongFormatter longFormat = new LongFormatter();
        formats.put(Long.class, longFormat);
        formats.put(long.class, longFormat);
        DoubleFormatter doubleFormat = new DoubleFormatter();
        formats.put(Double.class, doubleFormat);
        formats.put(double.class, doubleFormat);
    }

    public boolean isContinueOnCellError() {
        return continueOnCellError;
    }

    public void setContinueOnCellError(boolean continueOnCellError) {
        this.continueOnCellError = continueOnCellError;
    }

    public boolean isIgnoreEmptyRow() {
        return ignoreEmptyRow;
    }

    public void setIgnoreEmptyRow(boolean ignoreEmptyRow) {
        this.ignoreEmptyRow = ignoreEmptyRow;
    }

    public boolean isIgnoreInvalidCell() {
        return ignoreInvalidCell;
    }

    public void setIgnoreInvalidCell(boolean ignoreInvalidCell) {
        this.ignoreInvalidCell = ignoreInvalidCell;
    }

    public int getHeadIndex() {
        return headIndex;
    }

    public void setHeadIndex(int headIndex) {
        this.headIndex = headIndex;
    }
    public Map<Class, Formatter> getFormats() {
        return formats;
    }

    public int getReadRowLimit() {
        return readRowLimit;
    }

    public void setReadRowLimit(int readRowLimit) {
        this.readRowLimit = readRowLimit;
    }

    public void setFormats(Map<Class, Formatter> formats) {
        this.formats = formats;
    }
}
