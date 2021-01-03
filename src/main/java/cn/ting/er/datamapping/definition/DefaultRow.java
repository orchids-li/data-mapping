package cn.ting.er.datamapping.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class DefaultRow implements Row {
    private int index;
    private List<Cell> cells = new ArrayList<>();

    public DefaultRow(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public List<Cell> getCells() {
        return cells;
    }
}
