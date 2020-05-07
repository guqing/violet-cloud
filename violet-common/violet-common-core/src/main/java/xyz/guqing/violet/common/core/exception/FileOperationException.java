package xyz.guqing.violet.common.core.exception;

/**
 * File operation exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class FileOperationException extends ServiceException {
    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
