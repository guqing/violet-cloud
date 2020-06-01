package xyz.guqing.violet.common.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

/**
 * @author guqing
 * @date 2020-06-01
 */
public class VioletInternalException extends AbstractVioletCloudException {
    public VioletInternalException(String message) {
        super(message);
    }

    public VioletInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    @NonNull
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
