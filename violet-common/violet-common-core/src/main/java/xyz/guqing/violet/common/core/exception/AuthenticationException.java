package xyz.guqing.violet.common.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Authentication exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class AuthenticationException extends AbstractVioletCloudException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
