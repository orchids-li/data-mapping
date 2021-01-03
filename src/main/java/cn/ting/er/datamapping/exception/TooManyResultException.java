package cn.ting.er.datamapping.exception;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class TooManyResultException extends MappingException {
    public TooManyResultException() {
    }

    public TooManyResultException(String message) {
        super(message);
    }

    public TooManyResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyResultException(Throwable cause) {
        super(cause);
    }

    public TooManyResultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
