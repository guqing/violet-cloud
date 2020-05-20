package xyz.guqing.violet.common.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception of entity not found.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class NotFoundException extends AbstractVioletCloudException {
	private static final long serialVersionUID = -2218664015213932183L;

	public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
