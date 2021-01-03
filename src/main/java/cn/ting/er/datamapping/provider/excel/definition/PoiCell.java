package cn.ting.er.datamapping.provider.excel.definition;


import cn.ting.er.datamapping.definition.Cell;
import cn.ting.er.datamapping.utils.Wrapper;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class PoiCell implements Cell<String>, Wrapper<org.apache.poi.ss.usermodel.Cell> {
    private org.apache.poi.ss.usermodel.Cell cell;

    public PoiCell(org.apache.poi.ss.usermodel.Cell cell) {
        this.cell = cell;
    }

    @Override
    public org.apache.poi.ss.usermodel.Cell getOriginal() {
        return cell;
    }

    @Override
    public String getValue() {
        return cell.getStringCellValue();
    }

    @Override
    public int getIndex() {
        return cell.getColumnIndex();
    }
}
