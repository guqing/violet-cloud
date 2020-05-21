package xyz.guqing.violet.common.core.exception;

import org.springframework.http.HttpStatus;

/**
 * BeanUtils exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class BeanUtilsException extends AbstractVioletCloudException {
	private static final long serialVersionUID = -185461254705656671L;

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
