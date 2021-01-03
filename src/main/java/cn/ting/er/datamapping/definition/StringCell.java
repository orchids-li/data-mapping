package cn.ting.er.datamapping.definition;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class StringCell implements Cell {
    private String value;
    private int index;
    private Row row;

    public StringCell() {
    }

    public StringCell(String value, int index, Row row) {
        this.value = value;
        this.index = index;
        this.row = row;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }
}
