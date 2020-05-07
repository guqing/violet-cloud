package xyz.guqing.violet.common.core.exception;

import org.springframework.http.HttpStatus;

/**
 * BeanUtils exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class BeanUtilsException extends AbstractVioletCloudException {

    public BeanUtilsException(String message) {
        super(message);
    }

    public BeanUtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
