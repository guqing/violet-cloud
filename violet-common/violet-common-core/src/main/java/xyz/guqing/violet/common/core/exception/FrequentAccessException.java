package xyz.guqing.violet.common.core.exception;

/**
 * Frequent access exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class FrequentAccessException extends BadRequestException {

    public FrequentAccessException(String message) {
        super(message);
    }

    public FrequentAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
