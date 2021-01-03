package cn.ting.er.datamapping.exception;

import cn.ting.er.datamapping.definition.Cell;
import cn.ting.er.datamapping.definition.Row;

import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class ReadException extends MappingException {
    private List<String> row;
    private String cell;

    public ReadException(List<String> row, String message) {
        this(row, null, message);
    }

    public ReadException(List<String> row, String cell, String message) {
        super(message);
        this.row = row;
        this.cell = cell;
    }

    public ReadException(List<String> row, String cell, Exception e) {
        super(e.getMessage(), e);
        this.row = row;
        this.cell = cell;
    }

    public ReadException() {
        super();
    }

    public ReadException(String message) {
        super(message);
    }

    public ReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadException(Throwable cause) {
        super(cause);
    }

    protected ReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public List<String> getRow() {
        return row;
    }

    public void setRow(List<String> row) {
        this.row = row;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }
}
