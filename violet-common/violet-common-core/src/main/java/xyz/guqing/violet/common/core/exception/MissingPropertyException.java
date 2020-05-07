package xyz.guqing.violet.common.core.exception;

/**
 * Missing property value exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class MissingPropertyException extends BadRequestException {

    public MissingPropertyException(String message) {
        super(message);
    }

    public MissingPropertyException(String message, Throwable cause) {
        super(message, cause);
    }
}
