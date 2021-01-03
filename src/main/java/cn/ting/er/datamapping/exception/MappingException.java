package cn.ting.er.datamapping.exception;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class MappingException extends RuntimeException {
    public MappingException() {
        super();
    }

    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MappingException(Throwable cause) {
        super(cause);
    }

    protected MappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
