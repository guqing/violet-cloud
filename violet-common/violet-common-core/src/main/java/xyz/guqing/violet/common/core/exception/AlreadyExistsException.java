package xyz.guqing.violet.common.core.exception;

/**
 * Exception caused by entity existence already.
 *
 * @author guqing
 * @date 2020-4-4 16:29
 */
public class AlreadyExistsException extends BadRequestException {

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
