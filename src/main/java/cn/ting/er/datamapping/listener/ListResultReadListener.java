package cn.ting.er.datamapping.listener;

import cn.ting.er.datamapping.context.MappingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class ListResultReadListener<T> extends DefaultReadListener<T> {

    private static ListResultReadListener instance = new ListResultReadListener<>();

    public static <T> ListResultReadListener<T> getInstance() {
        return instance;
    }

    private List<T> result = new ArrayList<>();

    @Override
    public void onReadRow(T t, MappingContext context) {
        result.add(t);
    }

    public List<T> getResult() {
        return result;
    }
}
