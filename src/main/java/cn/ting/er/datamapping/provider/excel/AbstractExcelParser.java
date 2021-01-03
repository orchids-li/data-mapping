package cn.ting.er.datamapping.provider.excel;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public abstract class AbstractExcelParser implements ExcelParser {
    private ExcelSetting setting;

    public AbstractExcelParser(ExcelSetting setting) {
        this.setting = setting;
    }

    @Override
    public ExcelSetting getSetting() {
        return setting;
    }

    @Override
    public void setSetting(ExcelSetting setting) {
        this.setting = setting;
    }
}
