package xyz.guqing.violet.common.core.exception;

/**
 * Property format exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class PropertyFormatException extends BadRequestException {

    public PropertyFormatException(String message) {
        super(message);
    }

    public PropertyFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
