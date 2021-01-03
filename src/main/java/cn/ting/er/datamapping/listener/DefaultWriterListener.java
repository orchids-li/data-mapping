package cn.ting.er.datamapping.listener;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class DefaultWriterListener<T> implements WriterListener<T> {
    private static DefaultWriterListener instance = new DefaultWriterListener<>();

    public static <T> DefaultWriterListener<T> getInstance() {
        return instance;
    }
}
