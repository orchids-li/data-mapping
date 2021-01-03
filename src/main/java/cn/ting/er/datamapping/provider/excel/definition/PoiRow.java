package cn.ting.er.datamapping.provider.excel.definition;

import cn.ting.er.datamapping.definition.Cell;
import cn.ting.er.datamapping.utils.Wrapper;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class PoiRow implements cn.ting.er.datamapping.definition.Row, Wrapper<Row> {
    private Row row;
    public PoiRow(Row row) {
        this.row = row;

    }
    @Override
    public int getIndex() {
        return row.getRowNum();
    }

    @Override
    public List<Cell> getCells() {
        List<Cell> cells = new ArrayList<>();
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            cells.add(new PoiCell(row.getCell(i)));
        }
        return cells;
    }

    @Override
    public Row getOriginal() {
        return row;
    }
}
