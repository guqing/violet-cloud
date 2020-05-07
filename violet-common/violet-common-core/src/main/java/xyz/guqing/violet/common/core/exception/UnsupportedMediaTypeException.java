package xyz.guqing.violet.common.core.exception;

/**
 * Unsupported media type exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class UnsupportedMediaTypeException extends BadRequestException {

    public UnsupportedMediaTypeException(String message) {
        super(message);
    }

    public UnsupportedMediaTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
