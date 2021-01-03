package cn.ting.er.datamapping.provider.excel;

import cn.ting.er.datamapping.Setting;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class ExcelSetting extends Setting {
    private boolean outputFormulaValue = true;

    public boolean isOutputFormulaValue() {
        return outputFormulaValue;
    }

    public void setOutputFormulaValue(boolean outputFormulaValue) {
        this.outputFormulaValue = outputFormulaValue;
    }
}
