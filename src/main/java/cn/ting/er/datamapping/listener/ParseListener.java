package cn.ting.er.datamapping.listener;

import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public interface ParseListener {
    boolean start(String name, int index);
    void row(List<String> data, int rowIndex);
}
