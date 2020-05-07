package xyz.guqing.violet.common.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 基类异常
 * @author guqing
 * @date 2020-04-04 16:03
 */
public abstract class AbstractVioletCloudException extends RuntimeException {
    /**
     * Error errorData.
     */
    private Object errorData;

    public AbstractVioletCloudException(String message) {
        super(message);
    }

    public AbstractVioletCloudException(String message, Throwable cause) {
        super(message, cause);
    }

    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    /**
     * Sets error errorData.
     *
     * @param errorData error data
     * @return current exception.
     */
    @NonNull
    public AbstractVioletCloudException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }

}
