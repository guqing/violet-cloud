package xyz.guqing.violet.common.core.exception;

/**
 * Email exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class EmailException extends ServiceException {

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
