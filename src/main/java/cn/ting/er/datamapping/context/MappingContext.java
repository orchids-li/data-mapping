package cn.ting.er.datamapping.context;

import cn.ting.er.datamapping.Setting;
import cn.ting.er.datamapping.definition.Mapping;
import cn.ting.er.datamapping.definition.Property;
import cn.ting.er.datamapping.exception.MappingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class MappingContext extends HashMap<String, Object> {
    private Setting setting;
    private Mapping currentMapping;
    private Property currentProperty;
    private List<String> currentRow;
    private String currentCell;
    private List<MappingException> errors = new ArrayList<>();

    public MappingContext(Setting setting) {
        this.setting = setting;
    }

    public void reset() {
        this.currentMapping = null;
        this.currentProperty = null;
        this.currentRow = null;
        this.currentCell = null;
        this.errors = new ArrayList<>();
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public Mapping getCurrentMapping() {
        return currentMapping;
    }

    public void setCurrentMapping(Mapping currentMapping) {
        this.currentMapping = currentMapping;
    }

    public Property getCurrentProperty() {
        return currentProperty;
    }

    public void setCurrentProperty(Property currentProperty) {
        this.currentProperty = currentProperty;
    }

    public List<String> getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(List<String> currentRow) {
        this.currentRow = currentRow;
    }

    public String getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(String currentCell) {
        this.currentCell = currentCell;
    }

    public List<MappingException> getErrors() {
        return errors;
    }

    public void setErrors(List<MappingException> errors) {
        this.errors = errors;
    }
}
