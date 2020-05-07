package xyz.guqing.violet.common.core.exception;

/**
 * @author guqing
 * @date 2020-04-15 11:17
 */
public class UnsupportedDataTypeException extends BadRequestException {
    public UnsupportedDataTypeException(String message) {
        super(message);
    }

    public UnsupportedDataTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
