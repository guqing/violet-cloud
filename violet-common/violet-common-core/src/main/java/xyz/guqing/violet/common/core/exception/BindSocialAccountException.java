package xyz.guqing.violet.common.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

/**
 * @author guqing
 * @date 2020-05-07
 */
public class BindSocialAccountException extends AbstractVioletCloudException {
    public BindSocialAccountException(String message) {
        super(message);
    }

    public BindSocialAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    @NonNull
    public HttpStatus getStatus() {
        return HttpStatus.BAD_GATEWAY;
    }
}
