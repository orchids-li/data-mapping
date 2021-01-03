package cn.ting.er.datamapping.definition;

import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public interface Row {
    int getIndex();
    List<Cell> getCells();
}
